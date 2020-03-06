package com.example.demo.api.open.entity;

import java.math.BigDecimal;

public class CheckResult {

	/**验证是否通过*/
	private boolean checkPass;
	/**错误次数*/
	private BigDecimal errorNum;
	public boolean getCheckPass() {
		return checkPass;
	}
	public void setCheckPass(boolean checkPass) {
		this.checkPass = checkPass;
	}
	public BigDecimal getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(BigDecimal errorNum) {
		this.errorNum = errorNum;
	}
	
	
}
