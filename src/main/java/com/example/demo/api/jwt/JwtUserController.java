package com.example.demo.api.jwt;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.config.common.BaseContant;
import com.example.demo.config.common.BaseController;
import com.example.demo.config.common.WebResult;
import com.example.demo.config.utils.JWTUtils;
import com.example.demo.config.utils.SpringUtil;
import com.example.demo.config.utils.StringUtil;
import com.example.demo.myPostBar.postBar.entity.TPostBarLike;
import com.example.demo.myPostBar.postBar.mapper.TPostBarLikeMapper;
import com.example.demo.myPostBar.user.entity.TScoreGrade;
import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.entity.TUserLook;
import com.example.demo.myPostBar.user.entity.TUserNews;
import com.example.demo.myPostBar.user.mapper.TScoreGradeMapper;
import com.example.demo.myPostBar.user.mapper.TUserLookMapper;
import com.example.demo.myPostBar.user.mapper.TUserMapper;
import com.example.demo.myPostBar.user.mapper.TUserNewsMapper;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(BaseController.JWT_URL+"/user")
@Api(description = "用户(查询好友等)相关接口 ",tags="用户接口  ")
public class JwtUserController extends BaseController{
	
	@Autowired
	private TUserMapper tUserMapper;
	@Autowired
	private TScoreGradeMapper tScoreGradeMapper;
	@Autowired
	private TUserLookMapper tUserLookMapper;
	@Autowired
	private TPostBarLikeMapper tPostBarLikeMapper;
	@Autowired
	private TUserNewsMapper tUserNewsMapper;
	
	@ApiOperation(value = "获取自己登录的信息", notes = "")
	@GetMapping("/userInfo")
	public WebResult getUserInfo(@RequestHeader(Authorization)String token) throws UnsupportedEncodingException {
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 TUser user=tUserMapper.selectById(userId);
		 if(user!=null) {
			 QueryWrapper<TScoreGrade> queryWrapper=new QueryWrapper<TScoreGrade>();
			 queryWrapper.between("score", "0", user.getScore()).orderByDesc("score");
			 List<TScoreGrade> scoreList=tScoreGradeMapper.selectList(queryWrapper);
			 List<TScoreGrade> allScoreList=tScoreGradeMapper.selectList(null);
			 if(allScoreList.size()>scoreList.size()) {//如果还没到最大等级
				 //计算出下一等级需要的积分
				 Integer scoreFar=Integer.parseInt(allScoreList.get(scoreList.size()).getScore())-Integer.parseInt(user.getScore());
				 user.setScoreFar(scoreFar.toString());
			 }else {
				 user.setScoreFar("99999");
			 }
			 user.setGrade(scoreList.get(scoreList.size()-1).getName());
			 user.setPassword("");
			 return selectSuccess(user);
		 }
		 return selectNotFound();
	}
	
	@ApiOperation(value = "根据账号或邮箱搜索好友", notes = "")
	@GetMapping("/friendSearch")
	public WebResult friendSearch(@RequestHeader(Authorization)String token,String account) throws UnsupportedEncodingException {
		QueryWrapper<TUser> queryWrapper= new QueryWrapper<>();
		queryWrapper.eq("username",account).or()
		.eq("email", account);
		TUser users=tUserMapper.selectOne(queryWrapper);
		if(users!=null) {
			users.setPassword("");
			return selectSuccess(users);
		}
		return selectNotFound();
	}
	
	@ApiOperation(value = "获取未读消息列表", notes = "")
	@GetMapping("/newsIsNotRead")
	public WebResult newsIsNotRead(@RequestHeader(Authorization)String token,String type) throws UnsupportedEncodingException {
		 //查询未读消息并发送给他
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
	    QueryWrapper<TUserNews> query=new QueryWrapper<TUserNews>();
	    if(StrUtil.isNotBlank(type)) {
	    	query.in("type",StringUtil.convertList(type));
	    }
	    query.eq("status",BaseContant.NEWS_STATUS_NOT_READED).eq("user_id",userId);
	    //注入不了，改用Spring获取bean
	    List<TUserNews> notReadList=SpringUtil.getBean(TUserNewsMapper.class).selectList(query);
	    if(notReadList!=null && !notReadList.isEmpty()) {
	    	return selectSuccess(notReadList);
	    }
	    return selectNotFound();
	}
	
	@ApiOperation(value = "获取消息列表", notes = "")
	@GetMapping("/newsList")
	public WebResult newsList(@RequestHeader(Authorization)String token,Page<TUserNews> page,String type,String status) throws UnsupportedEncodingException {
		 //查询未读消息并发送给他
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
	    QueryWrapper<TUserNews> query=new QueryWrapper<TUserNews>();
	    if(StrUtil.isNotBlank(type)) {
	    	query.in("type",StringUtil.convertList(type));
	    }
	    if(StrUtil.isNotBlank(status)) {
	    	query.eq("status",status);
	    }
	    query.eq("user_id",userId);
	    //注入不了，改用Spring获取bean
	    Page<TUserNews> notReadList=tUserNewsMapper.selectPage(page, query);
	    if(notReadList.getRecords()!=null && !notReadList.getRecords().isEmpty()) {
	    	return selectSuccess(notReadList);
	    }
	    return selectNotFound();
	}
	
	@ApiOperation(value = "获取浏览记录", notes = "")
	@GetMapping("/userLook")
	public WebResult userLook(@RequestHeader(Authorization)String token,Page<TUserLook> page) throws UnsupportedEncodingException {
		 //查询未读消息并发送给他
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
	    QueryWrapper<TUserLook> query=new QueryWrapper<TUserLook>();
	    query.eq("user_id",userId);
	    Page<TUserLook> notReadList=tUserLookMapper.selectPage(page, query);
	    if(notReadList.getRecords()!=null && !notReadList.getRecords().isEmpty()) {
	    	return selectSuccess(notReadList);
	    }
	    return selectNotFound();
	}
	
	@ApiOperation(value = "获点赞记录", notes = "")
	@GetMapping("/userLike")
	public WebResult userLike(@RequestHeader(Authorization)String token,Page<TPostBarLike> page) throws UnsupportedEncodingException {
		 //查询未读消息并发送给他
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
	    QueryWrapper<TPostBarLike> query=new QueryWrapper<TPostBarLike>();
	    query.eq("create_id",userId);
	    Page<TPostBarLike> notReadList=tPostBarLikeMapper.selectPage(page, query);
	    if(notReadList.getRecords()!=null && !notReadList.getRecords().isEmpty()) {
	    	return selectSuccess(notReadList);
	    }
	    return selectNotFound();
	}
	
	@ApiOperation(value = "修改用户信息", notes = "")
	@PutMapping("/userInfo")
	public WebResult updateUser(@RequestHeader(Authorization)String token,@RequestBody TUser user) throws UnsupportedEncodingException {
		 //查询未读消息并发送给他
		 Claims userMap=JWTUtils.verifyToken(token);
		 String userId=userMap.get("uid",String.class);
		 user.setId(userId);
		 int i=tUserMapper.updateById(user);
	    if(i>0) {
	    	return updateSuccess(user);
	    }
	    return updateNotFound();
	}
	
	@ApiOperation(value = "删除消息", notes = "")
	@DeleteMapping("/news")
	public WebResult deleteNews(@RequestHeader(Authorization)String token,String id) throws UnsupportedEncodingException {
		 //查询未读消息并发送给他
		int i=tUserNewsMapper.deleteById(id);
	    if(i>0) {
	    	return deleteSuccess(i);
	    }
	    return deleteNotFound();
	}
}
