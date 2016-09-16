package com.jiayun.scp.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 记录发票与支出的多对多匹配关系.
 * @author xinyin
 *
 */
public class Invoice2Expense {
	
	private Set<Invoice> il;
	private Set<ExpRecord> el;
	
	private double isum, esum;
	private double bal;
	
	
	public Invoice2Expense() {
		il = new HashSet<Invoice>();
		el = new HashSet<ExpRecord>();
	}
	
	public void sum() {
		isum = 0.0;
		esum = 0.0;
		bal  = 0.0;
		
		for(Invoice i: il) {
			isum += i.getAmount();
		}
		for(ExpRecord e: el) {
			esum += e.getAmount();
		}
		bal = isum - esum;
		
	}
	public Set<Invoice> getIl() {
		return il;
	}
	public void setIl(Set<Invoice> il) {
		this.il = il;
	}
	public Set<ExpRecord> getEl() {
		return el;
	}
	public void setEl(Set<ExpRecord> el) {
		this.el = el;
	}
	public double getIsum() {
		return isum;
	}

	public double getEsum() {
		return esum;
	}


	public double getBal() {
		return bal;
	}
	

}
