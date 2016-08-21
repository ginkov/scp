package com.jiayun.scp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrWeek {
	
	private double bal;
	private List<FinanceRecord> in;
	private List<FinanceRecord> out;
	private double inAmount;
	private double outAmount;
	private double changeAmt;
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(double changeAmt) {
		this.changeAmt = changeAmt;
	}

	public FrWeek() {
		bal = 0.0;
		inAmount = 0.0;
		outAmount = 0.0;
		in = new ArrayList<FinanceRecord>();
		out = new ArrayList<FinanceRecord>();
	}
	
	public double getBal() {
		return bal;
	}
	public void setBal(double bal) {
		this.bal = bal;
	}
	public List<FinanceRecord> getIn() {
		return in;
	}
	public void setIn(List<FinanceRecord> in) {
		this.in = in;
	}
	public List<FinanceRecord> getOut() {
		return out;
	}
	public void setOut(List<FinanceRecord> out) {
		this.out = out;
	}
	public double getInAmount() {
		return inAmount;
	}
	public void setInAmount(double inAmount) {
		this.inAmount = inAmount;
	}
	public double getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(double outAmount) {
		this.outAmount = outAmount;
	}
	
	

}
