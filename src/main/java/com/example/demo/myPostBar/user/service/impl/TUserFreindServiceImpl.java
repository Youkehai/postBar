package com.example.demo.myPostBar.user.service.impl;

import com.example.demo.config.common.BaseContant;
import com.example.demo.myPostBar.user.entity.TUserFreind;
import com.example.demo.myPostBar.user.entity.TUserNews;
import com.example.demo.myPostBar.user.mapper.TUserFreindMapper;
import com.example.demo.myPostBar.user.mapper.TUserNewsMapper;
import com.example.demo.myPostBar.user.service.ITUserFreindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户-好友表 服务实现类
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@Service
public class TUserFreindServiceImpl extends ServiceImpl<TUserFreindMapper, TUserFreind> implements ITUserFreindService {

	@Autowired
	private TUserNewsMapper tUserNewsMapper;
	/***
	 * 修改好友状态和信息表信息
	 * @param updateEntity  修改需要的实体
	 * @param newsId  消息ID
	 * @param friend 	需要再反向添加一条你是我的好友的记录
	 * @return
	 */
	@Transactional
	public boolean updateNews(TUserFreind updateEntity, String newsId, TUserFreind friend) {
		boolean i=updateById(updateEntity);
		TUserNews news=tUserNewsMapper.selectById(newsId);
		if(news!=null) {
			TUserFreind myFriend=new TUserFreind();
			myFriend.setStatus(BaseContant.FRIEND_STATUS_NORMAL);
			myFriend.setMyId(friend.getFreindId());
			myFriend.setFreindId(friend.getMyId());
			myFriend.setCreateDate(LocalDateTime.now());
			myFriend.setUpdateDate(LocalDateTime.now());
			save(myFriend);//添加一条反向记录
			TUserNews update=new TUserNews();
			update.setId(news.getId());
			 if(BaseContant.FRIEND_STATUS_NORMAL.equals(updateEntity.getStatus())) {//如果是同意
				 update.setContent(news.getContent()+"(已同意)");
			 }else {
				 update.setContent(news.getContent()+"(已拒绝)");
			 }
			 tUserNewsMapper.updateById(update);
		}
		return i;
	}

}
