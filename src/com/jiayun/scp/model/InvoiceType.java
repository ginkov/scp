package com.jiayun.scp.model;

public enum InvoiceType {
	EXPENSE("费用"),
	LABOR("人工"), 
	MATERIAL("原材料");
	
	private String description;
	
	InvoiceType(String desc){
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}
	
}
