package com.example.demo.config.enums;

public enum BaseErrorEnum {

	ERR_STOCK_STATUS(10001, "目前有库存小于0的，不能开启验证"),
	ERR_DELETE_DATA_USE(10002, "该基础数据正在使用，不能删除"),
	ERR_CODE_NOT_EXIST(10003,"验证码不存在"),
	ERR_FRIEND_IS_EXIST(10004,"已是你的好友"),
	ERR_FRIEND_IS_APPLYING(10005,"正在申请中"),
	ERR_JWT_TOKEN_EXPRISE(9999, "令牌过期"),
/******************************************************start 异常：11001-11999***************************************************************/
	/** 查询成功   */
	SUCCESS_SELECT(0, "查询成功"),
	/** 新增成功   */
	SUCCESS_INSERT(0, "新增成功"),
	/** 修改成功   */
	SUCCESS_UPDATE(0, "修改成功 "),
	/** 删除成功   */
	SUCCESS_DELETE(0, "删除成功"),
	
	ERR_HEARPARAMS_AUTHORIZATION(10005, "缺少头部权限参数"),
	/** 请求缺少部分(检查文件名称、内容) */
	ERR_MISSING_SERVLET_REQUEST_PART_EXCEPTION(10006, "请求缺少部分(检查文件名称、内容)"),
	/** 缺少请求参数(检查参数名称、内容)   */
	ERR_MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(10007, "缺少请求参数(检查参数名称、内容)"),
	/** 参数解析失败(检查参数内容格式) */
	ERR_HTTP_MESSAGE_NOT_READABLE_EXCEPTION(10008, "参数解析失败(检查参数内容格式)"),
	/** 参数验证失败(@Valid对应实体类验证异常) */		
	ERR_METHOD_ARGUMENT_NOT_VALID_EXCEPTION(10009, "参数验证失败(@Valid对应实体类验证异常)"),
	/** 参数验证失败(@Validated验证实体的异常) */
	ERR_CONSTRAINT_VIOLATIONEXCEPTION(10010, "参数验证失败(@Validated验证实体的异常)"),
	/** 参数验证失败(javax基础验证异常)  */
	ERR_VALIDATION_EXCEPTION(10011, "参数验证失败(javax基础验证异常)"),
	/** 参数绑定失败(检查参数名称)  */
	ERR_BIND_EXCEPTION(10012, "参数绑定失败(检查参数名称)"),
	/** API地址不存在(检查请求地址)*/
	ERR_NO_HANDLER_FOUND_EXCEPTION(10013, " API地址不存在(检查请求地址)"),
	/** 不支持当前请求方法(检查请求方法) */
	ERR_HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(10014, "不支持当前请求方法(检查请求方法)"),
	/** 不支持当前媒体类型(检查下请求头contentType) */
	ERR_HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(10015, "不支持当前媒体类型(检查下请求头contentType)"),
	
	
	
/**********************************************101xx 表示查询的异常 ***************************************************/	
	/** 查询失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误)  */
	ERR_SELECT_ATTRIBUTE_NOT_REQUIREMENTS(10101, "查询失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误)"),
	/** 查询失败：找不到数据（查询返回0条） */
	ERR_SELECT_NOT_FOUND(10102, "找不到数据"),
	
	
/**********************************************102xx 表示新增的异常 ***************************************************/	
	/** 新增失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误) */
	ERR_INSERT_ATTRIBUTE_NOT_REQUIREMENTS(10201, " 新增失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误)"),
	/** 新增失败：数据重复 */
	ERR_INSERT_DATA_REPEAT(10202, "数据重复"),
	
	
	
/**********************************************103xx 表示修改的异常 ***************************************************/		
	/** 更新失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误) */
	ERR_UPDATE_ATTRIBUTE_NOT_REQUIREMENTS(10301, " 更新失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误)"),
	/** 更新失败：数据不存在 */
	ERR_UPDATE_DATA_NOT_FOUND(10302, " 更新失败：数据不存在"),
	
	
	
/**********************************************104xx 表示删除的异常 ***************************************************/		
	/** 删除失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误) */
	ERR_DELETE_ATTRIBUTE_NOT_REQUIREMENTS(10401, "删除失败：属性不满足要求(业务的判断：不存在，不符合，不在之内等错误)"),
	/** 删除失败：数据不存在 */
	ERR_DELETE_DATA_NOT_FOUND(10402, "删除失败：数据不存在 "),
	
	
	
/**********************************************10000 表示 未定义的异常  需要统一拦截  ***************************************************/		
	/**  系统内部错误 */
	ERR_INTERNAL_SERVER_ERROR(10000, " 出了点其他错误");
/*******************************************************end 平台通用异常：11001-11999**************************************************************/

	/** 错误编码 */
	private int errorCode;
	/** 错误信息 */
	private String errorMessage;

	private BaseErrorEnum(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
