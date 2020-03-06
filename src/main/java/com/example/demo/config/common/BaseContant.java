package com.example.demo.config.common;

import java.math.BigDecimal;

/***
 * 基础常量类
 * @author Administrator
 *
 */
public class BaseContant {

	/***
	 * 好友状态 是好友
	 */
	public static final String FRIEND_STATUS_NORMAL="0";
	/***
	 * 好友状态 申请中
	 */
	public static final String FRIEND_STATUS_APPLYING="1";
	
	/***
	 * 好友状态 拒绝
	 */
	public static final String FRIEND_STATUS_REFUSE="2";
	
	/***
	 * 消息状态  未读
	 */
	public static final String NEWS_STATUS_NOT_READED="1";
	
	/***
	 * 消息状态  已读
	 */
	public static final String NEWS_STATUS_READED="0";
	
	/***
	 * 每次点赞或浏览评论增加一次
	 */
	public static final BigDecimal UPDATE_ADD_NUM=new BigDecimal(1);
	
	/**
	 * 验证码过期时间(错误到达五次后的重试时间),十分钟
	 * */
	public static final long CODE_EXIST_TIME=600;
	
	/**
	 * 登录令牌过期时间 10小时
	 * */
	public static final long LOGIN_TOKEN_EXPRISE_TIME=36000000;
	
	/**
	 * 发帖加积分
	 * */
	public static final int POST_ADD_USER_GRADE=10;
	
	/**
	 * 评论加积分
	 * */
	public static  int COMMENT_ADD_USER_GRADE=10;
}
