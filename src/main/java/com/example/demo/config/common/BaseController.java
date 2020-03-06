package com.example.demo.config.common;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.demo.config.enums.BaseErrorEnum;


public class BaseController {
	
	/**头部参数*/
	public static final String Authorization = "Authorization";
	public static final String JWT_URL="/api/jwt";
	public static final String OPEN_URL="/api/open";
	
	/**注册的初始分值*/
	public static final String DEFAULT_SCORE="0";
	/**发帖的初始值*/
	public static final BigDecimal DEFAULT_NUM=new BigDecimal(0);
	
	
	/****************************************查询  ************************************************/
	/**查询成功:单个String，实体，分页对象*/
	protected WebResult selectSuccess(Object object) {
		return success(BaseErrorEnum.SUCCESS_SELECT.getErrorMessage(),object);
	}
	/**查询成功：多个实体，list等 */
	protected WebResult selectSuccess(Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_SELECT.getErrorMessage(),attributes);
	}
	/**查询成功：单个主实体对象，附加的多个list */
	protected WebResult selectSuccess(Object object,Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_SELECT.getErrorMessage(),object,attributes);
	}
	/**查询失败：属性不满足要求(不存在，不符合，不在之内等) */
	protected WebResult selectAttributeNotRequirements(Object object) {
		return error(BaseErrorEnum.ERR_SELECT_ATTRIBUTE_NOT_REQUIREMENTS,object);
	}
	/** 查询失败：找不到数据*/
	protected WebResult selectNotFound(Object object) {
		return error(BaseErrorEnum.ERR_SELECT_NOT_FOUND,object);
	}
	/** 查询失败：找不到数据*/
	protected WebResult selectNotFound() {
		return error(BaseErrorEnum.ERR_SELECT_NOT_FOUND);
	}
	
	/****************************************新增  ************************************************/
	/**新增成功:单个String，实体，分页对象*/
	protected WebResult insertSuccess(Object object) {
		return success(BaseErrorEnum.SUCCESS_INSERT.getErrorMessage(),object);
	}
	/**新增成功：多个实体，list等 */
	protected WebResult insertSuccess(Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_INSERT.getErrorMessage(),attributes);
	}
	/**新增成功：单个主实体对象，附加的多个list */
	protected WebResult insertSuccess(Object object,Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_INSERT.getErrorMessage(),object,attributes);
	}
	
	/** 新增失败：属性不满足要求(不存在，不符合，不在之内等) */
	protected WebResult insertAttributeNotRequirements(Object object) {
		return error(BaseErrorEnum.ERR_INSERT_ATTRIBUTE_NOT_REQUIREMENTS,object);
	}
	/** 新增失败：数据已存在*/
	protected WebResult insertDataRepeat(Object object) {
		return error(BaseErrorEnum.ERR_INSERT_DATA_REPEAT,object);
	}
	/** 新增失败：数据已存在*/
	protected WebResult insertDataRepeat() {
		return error(BaseErrorEnum.ERR_INSERT_DATA_REPEAT);
	}
	
	/****************************************权限不足 ************************************************/
	/****************************************修改 ************************************************/
	/**修改成功:单个String，实体，分页对象*/
	protected WebResult updateSuccess(Object object) {
		return success(BaseErrorEnum.SUCCESS_UPDATE.getErrorMessage(),object);
	}
	/**修改成功：多个实体，list等 */
	protected WebResult updateSuccess(Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_UPDATE.getErrorMessage(),attributes);
	}
	/**修改成功：单个主实体对象，附加的多个list */
	protected WebResult updateSuccess(Object object,Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_UPDATE.getErrorMessage(),object,attributes);
	}
	
	/**修改失败：属性不满足要求(不存在，不符合，不在之内等) */
	protected WebResult updateAttributeNotRequirements(Object object) {
		return error(BaseErrorEnum.ERR_UPDATE_ATTRIBUTE_NOT_REQUIREMENTS,object);
	}
	/**修改失败：找不到数据*/
	protected WebResult updateNotFound(Object object) {
		return error(BaseErrorEnum.ERR_UPDATE_DATA_NOT_FOUND,object);
	}
	/**修改失败：找不到数据*/
	protected WebResult updateNotFound() {
		return error(BaseErrorEnum.ERR_UPDATE_DATA_NOT_FOUND);
	}
	
	/****************************************删除************************************************/
	/**删除成功:单个String，实体，分页对象*/
	protected WebResult deleteSuccess(Object object) {
		return success(BaseErrorEnum.SUCCESS_DELETE.getErrorMessage(),object);
	}
	/**删除成功：多个实体，list等 */
	protected WebResult deleteSuccess(Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_DELETE.getErrorMessage(),attributes);
	}
	/**删除成功：单个主实体对象，附加的多个list */
	protected WebResult deleteSuccess(Object object,Map<String, Object> attributes) {
		return success(BaseErrorEnum.SUCCESS_DELETE.getErrorMessage(),object,attributes);
	}
	/** 删除失败：属性不满足要求(不存在，不符合，不在之内等) */
	protected WebResult deleteAttributeNotRequirements(Object object) {
		return error(BaseErrorEnum.ERR_DELETE_ATTRIBUTE_NOT_REQUIREMENTS,object);
	}
	/** 删除失败：找不到数据*/
	protected WebResult deleteNotFound(Object object) {
		return error(BaseErrorEnum.ERR_DELETE_DATA_NOT_FOUND,object);
	}
	/** 删除失败：找不到数据*/
	protected WebResult deleteNotFound() {
		return error(BaseErrorEnum.ERR_DELETE_DATA_NOT_FOUND);
	}
	
	/****************************************公用************************************************/
	
	protected WebResult success(String baseErrorEnumMessage,Object object) {
		return WebResult.success(baseErrorEnumMessage,object);
	}
	protected WebResult success(String baseErrorEnumMessage,Map<String, Object> attributes) {
		return WebResult.success(baseErrorEnumMessage,attributes);
	}
	protected WebResult success(String baseErrorEnumMessage,Object object,Map<String, Object> attributes) {
		return WebResult.success(baseErrorEnumMessage,object,attributes);
	}
	
	protected WebResult error(BaseErrorEnum baseErrorEnumMessage,Object object) {
		return WebResult.error(baseErrorEnumMessage.getErrorMessage(),baseErrorEnumMessage.getErrorCode(),object);
	}
	protected WebResult error(BaseErrorEnum baseErrorEnumMessage) {
		return WebResult.error(baseErrorEnumMessage.getErrorMessage(),baseErrorEnumMessage.getErrorCode(),null);
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				try {
					setValue(DateUtils.parseDate(text));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date) value) : "";
//			}
		});
	}
}
