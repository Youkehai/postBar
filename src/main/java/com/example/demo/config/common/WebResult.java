package com.example.demo.config.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WebResult  implements Serializable{

	/**
	 * 项目统一返回对象
	 * @author xiong
	 *
	 */
		
		private static final long serialVersionUID = 1L;
		
		public static final int SUCCESS = 0;
		
		public static final String SUCCESS_CODE = "success";
		
		public static final String MESSAGE_CODE = "message";
		
		public static final String STATUS_CODE = "status";
		
		public static final String DATA_CODE = "data";
		
		/** 是否成功 默认：true */
		private Boolean success;
		
		/** 状态码 */
		private int status;
		
		/** 提示信息  */
		private String message; 
		
		/** 返回对象(公用规则验证对象,如不为空,则提醒) */
		private Object data = null;
		
		/** 返回其他参数(导入数据重复或在数据中未查询到提醒对象，如不为空，则提醒) */
		private Map<String, Object> attributes;
		
		
		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		public Map<String, Object> setAttribute(String key, Object value) {
			if (attributes == null) {
				attributes = new HashMap<String, Object>();
			}
			attributes.put(key, value);
			return attributes;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Boolean getSuccess() {
			return success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}
		
		
		
		/**
		 *  请求错误
		 * @param msg
		 * @return
		 */
		public static WebResult error(String msg,int code) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.FALSE);
			result.setStatus(code);
			result.setMessage(msg);
			return result;
		}
		
		/**
		 *  请求错误
		 * @param msg
		 * @return
		 */
		public static WebResult error(String msg) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(msg);
			return result;
		}
		
		/**
		 *  请求错误
		 * @param msg
		 * @return
		 */
		public static WebResult error(String msg,Object data) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(msg);
			result.setData(data);
			return result;
		}
		
		
		/**
		 *  请求错误 带数据
		 * @param msg
		 * @param data
		 * @return
		 */
		public static WebResult error(String msg, int code, Object data) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(msg);
			result.setStatus(code);
			result.setData(data);
			return result;
		}
		
		/**
		 * 请求错误
		 * @param msg
		 * @param attributes 键值对数据
		 * @return
		 */
		public static WebResult error(String msg,int code, Map<String, Object> attributes) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(msg);
			result.setStatus(code);
			result.setAttributes(attributes);
			return result;
		}
		
		
		
		
		/**
		 * 请求成功
		 * @return
		 */
		public static WebResult success() {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.TRUE);
			result.setStatus(WebResult.SUCCESS);
			return result;
		}
		
		/**
		 * 请求成功  带提示信息
		 * @param msg 提示信息
		 * @return
		 */
		public static WebResult success(String msg) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.TRUE);
			result.setStatus(WebResult.SUCCESS);
			result.setMessage(msg);
			return result;
		}

		/**
		 * 请求成功
		 * @param msg 提示信息
		 * @param data 返回的数据    表格或者实体 或者list等
		 * @return
		 */
		public static WebResult success(String msg, Object data) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.TRUE);
			result.setStatus(WebResult.SUCCESS);
			result.setMessage(msg);
			result.setData(data);
			return result;
		}
		
		/**
		 * 请求成功
		 * @param msg 提示信息
		 * @param attributes 附加键值对参数
		 * @return
		 */
		public static WebResult success(String msg, Map<String, Object> attributes) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.TRUE);
			result.setStatus(WebResult.SUCCESS);
			result.setMessage(msg);
			result.setAttributes(attributes);
			return result;
		}
		
		
		/**
		 * 请求成功
		 * @param msg 提示信息
		 * @param attributes 附加键值对参数
		 * @return
		 */
		public static WebResult success(String msg, Object data, Map<String, Object> attributes) {
			WebResult result = new WebResult();
			result.setSuccess(Boolean.TRUE);
			result.setStatus(WebResult.SUCCESS);
			result.setMessage(msg);
			result.setData(data);
			result.setAttributes(attributes);
			return result;
		}

}
