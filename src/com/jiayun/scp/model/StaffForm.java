package com.jiayun.scp.model;

import java.util.HashSet;
import java.util.Set;

public class StaffForm {
	
	private String name;
	private String description;
	private String pass;
	private Set<String> roles;


	public StaffForm() {
		pass = "123456";
		roles = new HashSet<String>();
	}
	
	public StaffForm(Staff s) {
		name = s.getName();
		description = s.getDescription();
		pass = s.getPass_md5();
		roles = new HashSet<String>();
		for(Role r: s.getRoles()) {
			roles.add(r.getRole());
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

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
