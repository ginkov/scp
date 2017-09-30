package com.jiayun.scp.model;

import java.util.Date;

/**
 * 用于表示一条财务记录.
 * 包括 销售收入, 支出
 * @author xinyin
 *
 */
public class FinanceRecord {
	
	private Date date;
	private double amount;
	private String description;
	private FinanceAction action;
	private String sn;
	private String type;
	private String url;
	
	public FinanceRecord() {
		amount = 0.0;
	}

	public void fromExpRecord(ExpRecord er) {
		date = er.getDate();
		sn = er.getSn();
		amount = er.getAmount();
		action = FinanceAction.EXPENSE;
		description = er.getExpName();
		type = er.getT2().getName();
		url = "/finance/expense/detail/"+er.getId();
	}
	
	public void fromSalesOrder(SalesOrder so) {
		date = so.getOrderDate();
		sn = so.getSn();
		amount = so.getDiscountPrice();
		if(so.getPayStatus() == PayStatus.PAID) {
			action = FinanceAction.SALE;
		} else {
			action = FinanceAction.AR;
		}
		description = so.getProdSummary();
		type = so.getCustomer().getName();
		url = "/sale/order/detail/"+ so.getId();
	}
	
	public void fromNonSalesIncome(NonSalesIncomeRecord r) {
		date = r.getDate();
		sn = r.getSn();
		amount = r.getAmount();
		action = FinanceAction.INVEST_GOT;
		description = r.getSummary();
		type = r.getT2().getName();
		url = "/finance/nonsalesincome/detail/"+r.getId();
	}
	
	// getters and setters.

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FinanceAction getAction() {
		return action;
	}
	public void setAction(FinanceAction action) {
		this.action = action;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
}
