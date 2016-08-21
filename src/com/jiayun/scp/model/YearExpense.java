package com.jiayun.scp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="year_expense")
public class YearExpense {
	
	@Id
	private Integer year;
	
	@Column(name="exp_num")
	private Integer expNum = 0;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getExpNum() {
		return expNum;
	}

	public void setExpNum(Integer expNum) {
		this.expNum = expNum;
	}

}
