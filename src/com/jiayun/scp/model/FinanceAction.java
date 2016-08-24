package com.jiayun.scp.model;

public enum FinanceAction {
	EXPENSE("支出"), 
	SALE("销售收入"), 
	INVEST_GOT("获得投资"),
	AR("应收账款");
	
	private final String description;
	
	FinanceAction(String desc){
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}
	
	
}
