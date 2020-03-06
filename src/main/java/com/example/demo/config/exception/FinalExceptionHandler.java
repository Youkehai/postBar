package com.example.demo.config.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.example.demo.config.common.WebResult;
import com.example.demo.config.enums.BaseErrorEnum;

import cn.hutool.core.util.StrUtil;



@RestController
public class FinalExceptionHandler implements ErrorController {

Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	/**
	 * 主要拦截所有错误请求返回页面    变成Json
	 * 改变service异常的拦截
	 * @param resp
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/error")
	public WebResult error(HttpServletResponse resp, HttpServletRequest request) throws IOException {
		Map<String, Object> errorAttributes = getErrorAttributes(request, false);
		Integer status = (Integer) errorAttributes.get("status");
		
		String path = (String) errorAttributes.get("path");
		String messageFound = (String) errorAttributes.get("message");
		log.info(status +"------------" + path+"------------" + messageFound);
		if(status==401) {//缺少头部权限信息
			
			messageFound = "缺少头部权限信息";
			return WebResult.error(messageFound, BaseErrorEnum.valueOf(messageFound).getErrorCode());
		}
		if(status==500 && StrUtil.isNotBlank(messageFound)&& !messageFound.equals((messageFound))) {
			//500 同时错误信息存在于国际化中，即已登记的错误
			return WebResult.error(messageFound, BaseErrorEnum.valueOf(messageFound).getErrorCode());
		}
		// 错误处理逻辑
		return WebResult.error(messageFound, status, path);
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		  ServletWebRequest servletWebRequest = new ServletWebRequest(request);
		    return this.errorAttributes.getErrorAttributes(servletWebRequest,
		            includeStackTrace);
	}
}
