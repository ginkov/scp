package com.jiayun.scp.model;

public class StaffForm {
	
	private String name;
	private String description;
	private String pass;
	private boolean isUser;
	private boolean isAdmin;

	public StaffForm() {
		pass = "123456";
		isUser = true;
		isAdmin = false;
	}
	
	public StaffForm(Staff s) {
		name = s.getName();
		description = s.getDescription();
		pass = s.getPass_md5();
		for(Role l : s.getRoles()) {
			if("ROLE_USER".equals(l.getRole())) {
				isUser = true;
			}
			if("ROLE_ADMIN".equals(l.getRole())) {
				isAdmin = true;
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description= description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean getIsUser() {
		return isUser;
	}

	public void setIsUser(boolean isUser) {
		this.isUser = isUser;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
