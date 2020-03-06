package com.example.demo.myPostBar.postBar.service.impl;

import com.example.demo.config.common.BaseContant;
import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentReplyMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarMapper;
import com.example.demo.myPostBar.postBar.service.ITPostBarService;
import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.entity.TUserLook;
import com.example.demo.myPostBar.user.mapper.TUserLookMapper;
import com.example.demo.myPostBar.user.mapper.TUserMapper;
import com.google.common.collect.Lists;
import com.mysql.cj.ParseInfo;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@Service
public class TPostBarServiceImpl extends ServiceImpl<TPostBarMapper, TPostBar> implements ITPostBarService {

	@Autowired
	private TUserLookMapper tUserLookMapper;
	@Autowired
	private TUserMapper userMapper;
	@Autowired
	private TPostBarCommentMapper tPostBarCommentMapper;
	@Autowired
	private TPostBarCommentReplyMapper tPostBarCommentReplyMapper;
	/***
	 * 更新浏览数和增加浏览记录
	 * @param postBar2
	 * @param userId
	 */
	@Transactional
	public void updateNum(TPostBar postBar, String userId) {
		TPostBar update=new TPostBar();
		if(StrUtil.isNotBlank(userId)) {
			if(!userId.equals(postBar.getCreateId())) {//如果不是自己浏览增加浏览数
				update.setId(postBar.getId());
				update.setLookNum(postBar.getLookNum().add(BaseContant.UPDATE_ADD_NUM));
				updateById(update);
			}
		}
		if(StrUtil.isNotBlank(userId)) {//如果用户不为空吗，增加浏览记录
			QueryWrapper<TUserLook> query=new QueryWrapper<TUserLook>();
			query.eq("user_id", userId).eq("post_id", postBar.getId());
			List<TUserLook> lookExit=tUserLookMapper.selectList(query);
			if(lookExit!=null && !lookExit.isEmpty()) {//已经浏览过，更新时间
				if(lookExit.size()>1) {//如果多条一样的记录
					List<String> ids=Lists.newArrayList();
					int last=0;
					for (TUserLook tUserLook : lookExit) {
						last++;
						if(last<lookExit.size()) {//如果不是最后一个则加进去删除
							ids.add(tUserLook.getId());
						}
					}
					tUserLookMapper.deleteBatchIds(ids);
				}
				TUserLook entity=new TUserLook();
				entity.setId(lookExit.get(lookExit.size()-1).getId());
				entity.setCreateDate(LocalDateTime.now());
				tUserLookMapper.updateById(entity);
			}else {
				TUserLook look=new TUserLook();
				look.setCreateDate(LocalDateTime.now());
				look.setPostId(postBar.getId());
				look.setPostTitle(postBar.getTitle());
				look.setUserId(userId);
				tUserLookMapper.insert(look);
			}
		}
	}
	
	@Transactional
	public boolean insertAndUpdate(TPostBar postBar, TUser user2) {
		//查询今天是否已经发过帖子，已发过则不加分
		boolean insertGrade=true;
		QueryWrapper<TPostBar> query=new QueryWrapper<TPostBar>();
		query.likeRight("create_date", LocalDate.now()).eq("create_id",postBar.getCreateId());
		List<TPostBar> barList=list(query);
		if(barList!=null && !barList.isEmpty()) {
			insertGrade=false;
		}
		if(insertGrade) {
			TUser user=new TUser();
			user.setId(postBar.getId());
			Integer nowGrade=Integer.parseInt(user2.getScore())+BaseContant.POST_ADD_USER_GRADE;
			user.setScore(nowGrade.toString());
			userMapper.updateById(user);
		}
		return save(postBar);//新增帖子
	}

	/***
	 * 删除帖子 评论 回复
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public boolean deletePost(String id) {
		boolean delete=removeById(id);//先删除帖子
		@SuppressWarnings("rawtypes")
		QueryWrapper query=new QueryWrapper();
		query.eq("post_id", id);
		tPostBarCommentMapper.delete(query);
		tPostBarCommentReplyMapper.delete(query);
		return delete;
	}
}
