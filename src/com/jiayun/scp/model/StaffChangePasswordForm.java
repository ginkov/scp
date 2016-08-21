package com.jiayun.scp.model;

public class StaffChangePasswordForm {
	
	private String oldPass;
	private String description;
	private String newPass1;
	private String newPass2;
	private String newPassCheckResult;
	
	public String getOldPass() {
		return oldPass;
	}
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNewPass1() {
		return newPass1;
	}
	public void setNewPass1(String newPass1) {
		this.newPass1 = newPass1;
	}
	public String getNewPass2() {
		return newPass2;
	}
	public void setNewPass2(String newPass2) {
		this.newPass2 = newPass2;
	}
	public String getNewPassCheckResult() {
		return newPassCheckResult;
	}
	public void setNewPassCheckResult(String newPassCheckResult) {
		this.newPassCheckResult = newPassCheckResult;
	}
	
	
	
	public boolean isNewPassOK() {
		if(newPass1 != null && !newPass1.isEmpty() && newPass2 !=null && !newPass2.isEmpty()) {
			if(newPass1.equals(newPass2)) {
				newPassCheckResult = "";
				return true;
			}
			else {
				newPassCheckResult = "两次密码输入不同，请重新输入";
			}
		}
		else {
			newPassCheckResult = "密码不能为空";
		}
		return false;
	}
	
	
}
