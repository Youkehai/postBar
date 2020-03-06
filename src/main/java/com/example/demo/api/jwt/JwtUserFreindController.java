package com.example.demo.api.jwt;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.common.BaseContant;
import com.example.demo.config.common.BaseController;
import com.example.demo.config.common.WebResult;
import com.example.demo.config.enums.BaseErrorEnum;
import com.example.demo.config.utils.JWTUtils;
import com.example.demo.config.websocket.Websocket;
import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.user.entity.TScoreGrade;
import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.entity.TUserFreind;
import com.example.demo.myPostBar.user.entity.TUserNews;
import com.example.demo.myPostBar.user.mapper.TUserFreindMapper;
import com.example.demo.myPostBar.user.mapper.TUserMapper;
import com.example.demo.myPostBar.user.mapper.TUserNewsMapper;
import com.example.demo.myPostBar.user.service.impl.TUserFreindServiceImpl;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(BaseController.JWT_URL+"/friend")
@Api(description = "用户好友相关接口 ",tags="用户查询好友列表接口，申请，发消息  ")
public class JwtUserFreindController extends BaseController{

	@Autowired
	private TUserFreindMapper tUserFreindMapper;
	@Autowired
	private TUserMapper tUserMapper;
	@Autowired
	private Websocket websocket;
	@Autowired
	private TUserNewsMapper tUserNewsMapper;
	@Autowired
	private TUserFreindServiceImpl tUserFreindServiceImpl;
	

	@ApiOperation(value = "获取好友列表", notes = "")
	@GetMapping("/friends")
	public WebResult friends(@RequestHeader(Authorization)String token,String status) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 QueryWrapper<TUserFreind> query=new QueryWrapper<TUserFreind>();
		 query.eq("uf.my_id", userId);
		 if(StrUtil.isNotBlank(status)) {
			 query.eq("uf.status",status);
		 }
		 List<TUserFreind> friendList=tUserFreindMapper.selectFriendInfo(query);
		 if(friendList!=null && !friendList.isEmpty()) {
//			 List<String> idList=Lists.newArrayList();
//			 for (TUserFreind tUserFreind : friendList) {
//				 if(BaseContant.FRIEND_STATUS_NORMAL.equals(tUserFreind.getStatus())) {//如果状态已经是好友了
//					 idList.add(tUserFreind.getFreindId());
//				 }
//			}
//			 QueryWrapper<TUser> queryWrapper=new QueryWrapper<TUser>();
//			 queryWrapper.in("id",idList);
//			 List<TUser> userList=tUserMapper.selectList(queryWrapper);
			 return selectSuccess(friendList);
		 }
		 return selectNotFound();
	}
	
	@ApiOperation(value = "查询和某个好友的聊天记录", notes = "")
	@GetMapping("/friendChatList")
	public WebResult friendChat(@RequestHeader(Authorization)String token,String friendId) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 QueryWrapper<TUserNews> query=new QueryWrapper<TUserNews>();
		 List<TUserNews> allNewList=Lists.newArrayList();
		 query.eq("un.send_id", userId).eq("un.user_id", friendId).eq("un.type","chat");
		 List<TUserNews> newsList=tUserNewsMapper.selectNewsUserInfo(query);
		 if(newsList!=null && !newsList.isEmpty()) {
			 allNewList.addAll(newsList);
		 }
		 QueryWrapper<TUserNews> query1=new QueryWrapper<TUserNews>();
		 query1.eq("un.send_id", friendId).eq("un.user_id", userId).eq("un.type","chat");
		 newsList=tUserNewsMapper.selectNewsUserInfo(query1);
		 if(newsList!=null && !newsList.isEmpty()) {
			 allNewList.addAll(newsList);
		 }
		 if(allNewList!=null && !allNewList.isEmpty()) {
			 List<TUserNews> sortList=allNewList.stream().
			 sorted(Comparator.comparing(TUserNews :: getCreateDate))
			.collect(Collectors.toList());
			 return selectSuccess(sortList);
		 }
		 return selectNotFound();
	}
	
	@ApiOperation(value = "发送好友申请", notes = "")
	@PostMapping("/friends")
	public WebResult addFriend(@RequestHeader(Authorization)String token,String friendId) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 QueryWrapper<TUserFreind> query=new QueryWrapper<TUserFreind>();
		 query.eq("my_id",userId).eq("freind_id",friendId);
		 TUserFreind isFriend=tUserFreindMapper.selectOne(query);
		 if(isFriend!=null) {
			 if(BaseContant.FRIEND_STATUS_APPLYING.equals(isFriend.getStatus())) {
				 return error(BaseErrorEnum.ERR_FRIEND_IS_APPLYING);
			 }else {
				 return error(BaseErrorEnum.ERR_FRIEND_IS_EXIST);
			 }
		 }
		 TUserFreind friend=new TUserFreind();
		 friend.setStatus(BaseContant.FRIEND_STATUS_APPLYING);
		 friend.setFreindId(friendId);
		 friend.setMyId(userId);
		 friend.setCreateDate(LocalDateTime.now());
		 int i=tUserFreindMapper.insert(friend);
		 if(i>0) {
			 TUserNews news=new TUserNews();
			 TUser user=tUserMapper.selectById(userId);
			 news.setType("apply");
			 TUser userInfo=new TUser();
			 userInfo.setId(userId);
			 news.setSendId("system");
			 userInfo.setName(user.getName());
			 userInfo.setPostId(friend.getId());
			 userInfo.setAvatar(user.getAvatar());
			 news.setBody(JSONUtil.toJsonStr(userInfo));//存放发送者的头像和ID
			 news.setContent("申请成为你的好友");
			 news.setUserId(friendId);
			 websocket.AppointSending(friendId, news);
			 return insertSuccess(friend);
		 }
		 return insertDataRepeat();
	}
	
	@ApiOperation(value = "给好友发送消息", notes = "")
	@PostMapping("/friendnews")
	public WebResult MessageToFriend(@RequestHeader(Authorization)String token,@RequestBody @Validated TUserNews news) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		news.setType("chat");
		news.setCreateDate(LocalDateTime.now());
		news.setSendId(userId);
		news.setStatus(BaseContant.NEWS_STATUS_NOT_READED);
		websocket.AppointSending(news.getUserId(), news);
		return insertSuccess(news);
	}
	
	@ApiOperation(value = "设置消息已读（未读）[点击消息即可变为已读状态]", notes = "传入ID和status(0已读，1未读)")
	@PostMapping("/newsIsRead")
	public WebResult NewsIsRead(@RequestHeader(Authorization)String token,@RequestBody TUserNews news) throws UnsupportedEncodingException {
		if(StrUtil.isBlank(news.getId())) {//如果消息ID没找到
			return updateAttributeNotRequirements("id");
		}
		tUserNewsMapper.updateById(news);
		return updateSuccess(news);
	}
	
	@ApiOperation(value = "删除好友", notes = "")
	@DeleteMapping("/friends")
	public WebResult deleteFriend(@RequestHeader(Authorization)String token,String friendId) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 QueryWrapper<TUserFreind> query=new QueryWrapper<TUserFreind>();
		 query.eq("my_id", userId).eq("freind_id", friendId);
		 TUserFreind friend=tUserFreindMapper.selectOne(query);
		 if(friend!=null) {
			 int i=tUserFreindMapper.deleteById(friend.getId());
			 if(i>0) {
				 return deleteSuccess(friend);
			 }
		 }
		 return deleteNotFound();
	}
	
	@ApiOperation(value = "同意(0)/拒绝(1)好友申请", notes = "")
	@PutMapping("/friends")
	public WebResult Friend(@RequestHeader(Authorization)String token,String id,String status,String newsId) throws UnsupportedEncodingException {
		TUserFreind friend =tUserFreindMapper.selectById(id);
		if(friend!=null) {
			TUserFreind updateEntity=new TUserFreind();
			updateEntity.setId(id);
			updateEntity.setStatus(status);
			updateEntity.setUpdateDate(LocalDateTime.now());
			//修改朋友关系和修改消息信息
			boolean i=tUserFreindServiceImpl.updateNews(updateEntity,newsId,friend);
			if(i) {
				 TUserNews news=new TUserNews();
				 //查询到朋友的资料
				 TUser user=tUserMapper.selectById(friend.getFreindId());
				 news.setType("message");
				 TUser userInfo=new TUser();
				 userInfo.setId(friend.getFreindId());
				 userInfo.setAvatar(user.getAvatar());
				 userInfo.setName(user.getName());
				 news.setSendId("system");
				 news.setUserId(friend.getMyId());
				 news.setBody(JSONUtil.toJsonStr(userInfo));//存放发送者的头像和ID
				 if(BaseContant.FRIEND_STATUS_NORMAL.equals(status)) {//如果是同意
					 news.setContent("同意成为你的好友");
				 }else {
					 news.setContent("拒绝了你的好友申请");
				 }
				 websocket.AppointSending(friend.getMyId(), news);
				 return updateSuccess(friend);
			 }
			 return updateNotFound();
		}
		return updateNotFound();
	}
}
