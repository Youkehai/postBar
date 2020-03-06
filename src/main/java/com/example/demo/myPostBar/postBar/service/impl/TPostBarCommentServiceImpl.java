package com.example.demo.myPostBar.postBar.service.impl;

import com.example.demo.config.common.BaseContant;
import com.example.demo.config.common.BaseController;
import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.postBar.entity.TPostBarComment;
import com.example.demo.myPostBar.postBar.entity.TPostBarCommentReply;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentReplyMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarMapper;
import com.example.demo.myPostBar.postBar.service.ITPostBarCommentService;
import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.mapper.TUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 帖子——评论表 服务实现类
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@Service
public class TPostBarCommentServiceImpl extends ServiceImpl<TPostBarCommentMapper, TPostBarComment> implements ITPostBarCommentService {

	@Autowired
	private TPostBarMapper tPostBarMapper;
	@Autowired
	private TUserMapper tUserMapper;
	@Autowired
	private TPostBarCommentReplyMapper tPostBarCommentReplyMapper;
	/***
	 * 添加评论和增加帖子评论数
	 * @param barComment
	 * @param postBar2 
	 * @param user 
	 * @return
	 */
	@Transactional
	public int insertAndUpdate(TPostBarComment barComment, TPostBar postBar2, TUser user) {
		//查询今天是否已经发过帖子，已发过则不加分
		boolean insertGrade=true;
		QueryWrapper<TPostBarComment> query=new QueryWrapper<TPostBarComment>();
		query.likeRight("create_date", LocalDate.now()).eq("create_id",postBar2.getCreateId());
		List<TPostBarComment> barList=list(query);
		if(barList!=null && !barList.isEmpty()) {
			insertGrade=false;
		}
		save(barComment);
		if(insertGrade) {
			TUser user2=new TUser();
			user2.setId(postBar2.getId());
			Integer nowGrade=Integer.parseInt(user.getScore())+BaseContant.POST_ADD_USER_GRADE;
			user2.setScore(nowGrade.toString());
			tUserMapper.updateById(user2);
		}
		TPostBar postBar=new TPostBar();
		postBar.setId(postBar2.getId());
		postBar.setCommentNum(postBar2.getCommentNum().add(BaseContant.UPDATE_ADD_NUM));
		return tPostBarMapper.updateById(postBar);
	}
	
	/***
	 * 删除评论和评论下的回复
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean deleteComment(String id) {
		boolean delete=removeById(id);
		QueryWrapper<TPostBarCommentReply> query=new QueryWrapper<TPostBarCommentReply>();
		query.eq("comment_id", id);
		tPostBarCommentReplyMapper.delete(query);
		return delete;
	}

}
