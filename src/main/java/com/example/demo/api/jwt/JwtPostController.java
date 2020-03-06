package com.example.demo.api.jwt;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.config.common.BaseContant;
import com.example.demo.config.common.BaseController;
import com.example.demo.config.common.WebResult;
import com.example.demo.config.utils.CusAccessObjectUtil;
import com.example.demo.config.utils.JWTUtils;
import com.example.demo.config.websocket.Websocket;
import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.postBar.entity.TPostBarComment;
import com.example.demo.myPostBar.postBar.entity.TPostBarCommentReply;
import com.example.demo.myPostBar.postBar.entity.TPostBarLike;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentReplyMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarLikeMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarMapper;
import com.example.demo.myPostBar.postBar.service.impl.TPostBarCommentServiceImpl;
import com.example.demo.myPostBar.postBar.service.impl.TPostBarLikeServiceImpl;
import com.example.demo.myPostBar.postBar.service.impl.TPostBarServiceImpl;
import com.example.demo.myPostBar.user.entity.TScoreGrade;
import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.entity.TUserNews;
import com.example.demo.myPostBar.user.mapper.TUserMapper;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(BaseController.JWT_URL+"/post")
@Api(description = "发帖评论回复相关接口 ",tags="帖子接口  ")
public class JwtPostController extends BaseController{

	@Autowired
	private TPostBarMapper tPostBarMapper;
	@Autowired
	private TPostBarCommentMapper tPostBarCommentMapper;
	@Autowired
	private TPostBarCommentReplyMapper tPostBarCommentReplyMapper;
	@Autowired
	private Websocket websocket;
	@Autowired
	private TPostBarCommentServiceImpl tPostBarCommentServiceImpl;
	@Autowired
	private TPostBarLikeMapper tPostBarLikeMapper;
	@Autowired
	private TPostBarLikeServiceImpl tPostBarLikeServiceImpl;
	@Autowired
	private TPostBarServiceImpl tPostBarServiceImpl;
	@Autowired
	private TUserMapper tUserMapper;
	
	@ApiOperation(value = "发帖", notes = "")
	@PostMapping("/postBar")
	public WebResult postBar(@RequestHeader(Authorization)String token,@RequestBody @Validated TPostBar postBar) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 TUser user=tUserMapper.selectById(userId);
		 postBar.setCreateAvatar(user.getAvatar());
		 postBar.setCreateDate(LocalDateTime.now());
		 postBar.setCreateId(userId);
		 postBar.setLikeNum(DEFAULT_NUM);
		 postBar.setLookNum(DEFAULT_NUM);
		 postBar.setCommentNum(DEFAULT_NUM);
		 postBar.setCreateName(user.getName());
		 //新增帖子和个人积分,一天就加一次
		 boolean i=tPostBarServiceImpl.insertAndUpdate(postBar,user);
//		 tPostBarMapper.insert(postBar);
		if(i) {
			return insertSuccess(postBar);
		}else {
			return insertDataRepeat();
		}
	}
	
	@ApiOperation(value = "评论", notes = "")
	@PostMapping("/comment")
	public WebResult comment(HttpServletRequest request,@RequestHeader(Authorization)String token,@RequestBody @Validated TPostBarComment barComment) throws UnsupportedEncodingException {
		//判断评论的帖子存不存在
		TPostBar postBar=tPostBarMapper.selectById(barComment.getPostId());
		if(postBar!=null) {
			 Claims userMap=JWTUtils.verifyToken(token);
			 String userId=userMap.get("uid",String.class);
			 TUser user=tUserMapper.selectById(userId);
			 String ip=CusAccessObjectUtil.getIpAddress(request);
			 barComment.setCreateAvatar(user.getAvatar());
			 barComment.setCreateDate(LocalDateTime.now());
			 barComment.setCreateName(user.getName());
			 barComment.setIpAddress(ip);
			 barComment.setCreateId(userId);
			//新增帖子和个人积分,一天就加一次
			int i= tPostBarCommentServiceImpl.insertAndUpdate(barComment,postBar,user);
			if(i>0) {
				if(!postBar.getCreateId().equals(userId)) {//如果不是自己的帖子
					TUserNews news=new TUserNews();
					news.setSendId("system");
					news.setUserId(postBar.getCreateId());//发送通知给发帖者
					news.setContent(barComment.getContent());
					TUser userInfo=new TUser();
					userInfo.setId(userId);
					userInfo.setName(user.getName());
					userInfo.setAvatar(user.getAvatar());
					userInfo.setPostId(postBar.getId());
					news.setType("comment");
					news.setBody(JSONUtil.toJsonStr(userInfo));
					websocket.AppointSending(postBar.getCreateId(),news);
				}
				return insertSuccess(barComment);
			}else {
				return insertDataRepeat();
			}
		}
		return insertAttributeNotRequirements("该帖子不存在");
	}
	
	@ApiOperation(value = "回复", notes = "")
	@PostMapping("/reply")
	public WebResult reply(HttpServletRequest request,@RequestHeader(Authorization)String token,@RequestBody @Validated TPostBarCommentReply barCommentReply) throws UnsupportedEncodingException {
		//判断回复的评论存不存在
		TPostBarComment postBar=tPostBarCommentMapper.selectById(barCommentReply.getCommentId());
		if(postBar!=null) {
			 Claims userMap=JWTUtils.verifyToken(token);
			 String userId=userMap.get("uid",String.class);
			 TUser user=tUserMapper.selectById(userId);
			 barCommentReply.setCreateAvatar(user.getAvatar());
			 barCommentReply.setCreateDate(LocalDateTime.now());
			 barCommentReply.setCreateName(user.getName());
			 barCommentReply.setCreateId(userId);
			int i= tPostBarCommentReplyMapper.insert(barCommentReply);
			if(i>0) {
				if(!postBar.getCreateId().equals(userId)) {//如果不是自己的评论
				TUserNews news=new TUserNews();
				news.setSendId("system");
				news.setUserId(postBar.getCreateId());//发送通知给评论者
				news.setContent(barCommentReply.getContent());
				TUser userInfo=new TUser();
				userInfo.setId(userId);
				userInfo.setName(user.getName());
				userInfo.setAvatar(user.getAvatar());
				userInfo.setPostId(postBar.getId());
				news.setType("reply");
				news.setBody(JSONUtil.toJsonStr(userInfo));
				websocket.AppointSending(postBar.getCreateId(),news);
				}
				return insertSuccess(barCommentReply);
			}else {
				return insertDataRepeat();
			}
		}
		return insertAttributeNotRequirements("该帖子不存在");
	}
	
	@ApiOperation(value = "给帖子点赞", notes = "postId帖子ID和点赞状态status0(确认点赞),1(取消点赞)")
	@PostMapping("/like")
	public WebResult likePost(HttpServletRequest request,@RequestHeader(Authorization)String token,@RequestBody @Validated TPostBarLike postLike) throws UnsupportedEncodingException {
		//判断回复的评论存不存在
		TPostBar postBar=tPostBarMapper.selectById(postLike.getPostId());
		if(postBar!=null ) {
			Claims userMap=JWTUtils.verifyToken(token);
			String userId=userMap.get("uid",String.class);
			TUser user=tUserMapper.selectById(userId);
			if("0".equals(postLike.getStatus())){
				postLike.setCreateAvatar(user.getAvatar());
				postLike.setCreateDate(LocalDateTime.now());
				postLike.setCreateId(userId);
				postLike.setPostTitle(postBar.getTitle());
				postLike.setCreateName(user.getName());
				int i= tPostBarLikeServiceImpl.insertAndUpdate(postLike,postBar);
				if(i>0) {
					if(!postBar.getCreateId().equals(userId)) {//如果不是自己的帖子
					TUserNews news=new TUserNews();
					news.setSendId("system");
					news.setUserId(postBar.getCreateId());//发送通知给发帖者
					news.setContent(postBar.getTitle());
					TUser userInfo=new TUser();
					userInfo.setId(userId);
					userInfo.setName(user.getName());
					userInfo.setAvatar(user.getAvatar());
					userInfo.setPostId(postBar.getId());
					news.setType("like");
					news.setBody(JSONUtil.toJsonStr(userInfo));
					websocket.AppointSending(postBar.getCreateId(),news);
					}
					return insertSuccess(postLike);
				}else {
					return insertDataRepeat();
				}
			}else {
				QueryWrapper<TPostBarLike> query=new QueryWrapper<TPostBarLike>();
				query.eq("post_id",postBar.getId()).eq("create_id",userId);
				tPostBarLikeServiceImpl.deleteAndUpdate(query,postBar);
				return deleteSuccess("取消点赞成功");
			}
		}
		return insertAttributeNotRequirements("该帖子不存在");
	}
	
	@ApiOperation(value = "查询我发的帖子", notes = "")
	@GetMapping("/myPost")
	public WebResult getMyPost(HttpServletRequest request,@RequestHeader(Authorization)String token,Page<TPostBar> page) throws UnsupportedEncodingException {
		Claims userMap=JWTUtils.verifyToken(token);
		String userId=userMap.get("uid",String.class);
		QueryWrapper<TPostBar> query=new QueryWrapper<TPostBar>();
		query.eq("create_id",userId);
		Page<TPostBar> pageList=tPostBarMapper.selectPage(page, query);
		if(pageList.getRecords()!=null && !pageList.getRecords().isEmpty()) {
			return selectSuccess(pageList);
		}
		return selectNotFound();
	}
	
	@ApiOperation(value = "删除帖子", notes = "")
	@DeleteMapping("/postBar")
	public WebResult deletePostBar(@RequestHeader(Authorization)String token,String id) throws UnsupportedEncodingException {
		Claims userMap=JWTUtils.verifyToken(token);
		String userId=userMap.get("uid",String.class);
		TPostBar postBar=tPostBarMapper.selectById(id);
		if(postBar!=null) {
			if(userId.equals(postBar.getCreateId())) {//如果是我发的
				boolean i=tPostBarServiceImpl.deletePost(id);
				if(i) {
					return deleteSuccess(i);
				}
			}
		}
		return deleteNotFound();
	}
	
	@ApiOperation(value = "删除评论", notes = "")
	@DeleteMapping("/comment")
	public WebResult deleteComment(@RequestHeader(Authorization)String token,String id) throws UnsupportedEncodingException {
		Claims userMap=JWTUtils.verifyToken(token);
		String userId=userMap.get("uid",String.class);
		TPostBarComment postBarComment=tPostBarCommentMapper.selectById(id);
		if(postBarComment!=null) {
			TPostBar postBar=tPostBarMapper.selectById(postBarComment.getPostId());
			if(userId.equals(postBarComment.getCreateId()) || postBar.getCreateId().contentEquals(userId)) {//如果是我发的或者是帖子主人的操作
				boolean i=tPostBarCommentServiceImpl.deleteComment(id);
				if(i) {
					return deleteSuccess(i);
				}
			}
		}
		return deleteNotFound();
	}
	
	@ApiOperation(value = "删除回复", notes = "")
	@DeleteMapping("/reply")
	public WebResult deleteReply(@RequestHeader(Authorization)String token,String id) throws UnsupportedEncodingException {
		Claims userMap=JWTUtils.verifyToken(token);
		String userId=userMap.get("uid",String.class);
		TPostBarCommentReply postBarComment=tPostBarCommentReplyMapper.selectById(id);
		if(postBarComment!=null) {
			TPostBar postBar=tPostBarMapper.selectById(postBarComment.getPostId());
			if(userId.equals(postBarComment.getCreateId()) || postBar.getCreateId().contentEquals(userId)) {//如果是我发的或者是帖子主人的操作
				int i=tPostBarCommentReplyMapper.deleteById(id);
				if(i>0) {
					return deleteSuccess(i);
				}
			}
		}
		return deleteNotFound();
	}
}
