package com.jiayun.scp.model;

public enum PayStatus {
	
	UNPAID("未付款"), DOWNPAID("已付定金"), PAID("已付全款");
	
	private final String description;
	
	PayStatus(String desc){
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}
}
