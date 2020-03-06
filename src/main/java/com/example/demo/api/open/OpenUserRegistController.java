package com.example.demo.api.open;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.api.open.entity.CheckResult;
import com.example.demo.config.common.BaseContant;
import com.example.demo.config.common.BaseController;
import com.example.demo.config.common.WebResult;
import com.example.demo.config.enums.BaseErrorEnum;
import com.example.demo.config.redis.RedisUtils;
import com.example.demo.config.utils.BigDecimalUtil;
import com.example.demo.config.utils.JWTUtils;
import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.mapper.TUserMapper;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.mail.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value=BaseController.OPEN_URL+"/user")
@Api(description = "用户注册登录发送验证码接口 ",tags=" 用户注册登录发送验证码接口 ")
public class OpenUserRegistController extends BaseController{

	private static final String error_code="error_code:";
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private TUserMapper tUserMapper;
	
	@PostMapping("/sendEmail")
	@ApiOperation(value = "发送邮箱验证码", notes = "")
	public WebResult sendEmail(String email,String message) {
		Integer code=(int)((Math.random()*9+1)*1000);
		//十分钟验证码过期
		redisUtils.set(email, code.toString(),BaseContant.CODE_EXIST_TIME);
		MailUtil.send(email, "验证码", "你的验证码是"+code+",十分钟内有效", false);
		return insertSuccess("");
	}
	
	@ApiOperation(value = "登录", notes = "")
	@PostMapping("/login")
	public WebResult login(@RequestBody TUser user) throws UnsupportedEncodingException {
		QueryWrapper<TUser> queryWrapper= new QueryWrapper<>();
		queryWrapper.eq("username", user.getEmail()).or()
		.eq("email", user.getEmail());
		TUser users=tUserMapper.selectOne(queryWrapper);
		if(users!=null) {
			String password=user.getPassword()+users.getEmail();
			String s2 = HexUtil.encodeHexStr(password);
			queryWrapper.eq("password", s2);
			TUser userLogin=tUserMapper.selectOne(queryWrapper);
			if(userLogin!=null) {
				//设置jwt过期时间为十个小时36000000
				String token=JWTUtils.createToken(users,BaseContant.LOGIN_TOKEN_EXPRISE_TIME);
				WebResult result=WebResult.success("登录成功",user);
				result.setAttribute("token", token);
				return result;
			}
		}
		return selectNotFound();
	}
	
	@ApiOperation(value = "邮箱验证码登录", notes = "")
	@PostMapping("/codeLogin")
	public WebResult codeLogin(@RequestBody @Validated TUser user) throws UnsupportedEncodingException {
		CheckResult check=validateCode(user.getEmail(), user.getCode());
		if(check.getCheckPass()) {
			redisUtils.del(user.getEmail());
			QueryWrapper<TUser> queryWrapper= new QueryWrapper<>();
			queryWrapper.eq("username", user.getUsername()).or()
			.eq("email", user.getEmail());
			TUser users=tUserMapper.selectOne(queryWrapper);
			if(users!=null) {
				//设置jwt过期时间为十个小时36000000
				String token=JWTUtils.createToken(users,BaseContant.LOGIN_TOKEN_EXPRISE_TIME);
				WebResult result=WebResult.success("登录成功",user);
				result.setAttribute("token", token);
				return result;
			}
		}
		return error(BaseErrorEnum.ERR_CODE_NOT_EXIST,check.getErrorNum());
	}
	
	@ApiOperation(value = "注册", notes = "")
	@PostMapping("/regist")
	public WebResult regist(@RequestBody @Validated TUser user) {
		QueryWrapper<TUser> query=new QueryWrapper<TUser>();
		query.eq("email", user.getEmail());
		TUser users=tUserMapper.selectOne(query);
		if(users!=null) {
			return insertDataRepeat();
		}
		CheckResult check=validateCode(user.getEmail(), user.getCode());
		if(check.getCheckPass()) {//验证通过，删除缓存
			redisUtils.del(user.getEmail());
			user.setScore(DEFAULT_SCORE);
			String password=user.getPassword()+user.getEmail();
			String s2 = HexUtil.encodeHexStr(password);
			user.setPassword(s2);
			user.setSex("2");
			tUserMapper.insert(user);
			return insertSuccess(user);
		}
		return error(BaseErrorEnum.ERR_CODE_NOT_EXIST,check.getErrorNum());
	}
	public static void main(String[] args) {
		String password="12345615574996662@163.com";
		String s2 = HexUtil.encodeHexStr(password);
		System.out.println(s2);
	}
	
	/***
	 * 验证邮箱和验证码
	 * @param email
	 * @param code
	 * @return
	 */
//	@PostMapping("/validateCode")
//	@ApiOperation(value = "验证邮箱和验证码", notes = "")
	public CheckResult validateCode(String email,String code) {
		CheckResult result=new CheckResult();
		//先查询是否有错误次数
		BigDecimal errorNum=(BigDecimal) redisUtils.get(error_code+email);
		BigDecimal maxError=new BigDecimal(5);
		//如果有错误次数，并且错误次数大于5，那么不验证，直接返回错误
		if(BigDecimalUtil.notEqualZERO(errorNum) && (errorNum.compareTo(maxError)==1 ||errorNum.compareTo(maxError)==0 )) {
			result.setCheckPass(false);
			result.setErrorNum(errorNum);
			return result;
		}
		String factCode=(String) redisUtils.get(email);
		if(StrUtil.isNotBlank(factCode)) {
			if(factCode.equals(code)) {
				result.setCheckPass(true);
				result.setErrorNum(BigDecimal.ZERO);
				return result;
			}
		}
		//如果错误了，则添加错误次数，十分钟过期
		if(BigDecimalUtil.notEqualZERO(errorNum)) {
			redisUtils.set(error_code+email, errorNum.add(BaseContant.UPDATE_ADD_NUM),BaseContant.CODE_EXIST_TIME);
			result.setErrorNum(errorNum.add(BaseContant.UPDATE_ADD_NUM));
		}else {
			result.setErrorNum(BaseContant.UPDATE_ADD_NUM);
			redisUtils.set(error_code+email, BaseContant.UPDATE_ADD_NUM,BaseContant.CODE_EXIST_TIME);
		}
		result.setCheckPass(false);
		return result;
	}
}
