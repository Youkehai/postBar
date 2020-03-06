package com.example.demo.config.websocket;
 
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.example.demo.myPostBar.user.entity.TUserNews;

import cn.hutool.json.JSONUtil;
 
 
 
/**
 * definition for our encoder
 * 
 * @编写人: 夏小雪 日期:2015年6月14日 时间:上午11:58:23
 */
public class ServerEncoder implements Encoder.Text<TUserNews> {
 
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public String encode(TUserNews messagepojo) throws EncodeException {
		return JSONUtil.toJsonStr(messagepojo);
	}
 
}