package com.example.demo.api.jwt;


import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.config.enums.BaseErrorEnum;
import com.example.demo.config.utils.JWTUtils;

import cn.hutool.core.util.StrUtil;


/***
 * 判断请求有没有带jwt
 * @author youkehai
 *
 */
@Aspect
@Component
public class JwtAspect {
	//声明该方法是一个前置通知：在目标方法开始之前执行
	    @Before(value="execution(* com.example.demo.api.jwt..*.*(..))")
	    public void beforeMethod(JoinPoint joinpoint) throws Throwable  {
	    	
	    	 RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	         ServletRequestAttributes sra = (ServletRequestAttributes) ra;
	         HttpServletRequest request = sra.getRequest();
	         // 获取请求头
	         String JWT = request.getHeader("Authorization");
	        
	         if(StrUtil.isNotBlank(JWT)) {
	        	 JWTUtils.verifyToken(JWT);
	         }else { //如果没带jwt
	        	 throw  new ExceptionInInitializerError(BaseErrorEnum.ERR_HEARPARAMS_AUTHORIZATION.getErrorMessage());
	         }
	     }
}
