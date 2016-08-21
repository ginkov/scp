package com.jiayun.scp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="year_order")
/**
 * 生成每年的定单号。
 * @author xinyin
 *
 */
public class YearOrder {
	
	@Id
	private Integer year;
	
	@Column(name="order_number")
	private Integer ordersNumber;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getOrdersNumber() {
		return ordersNumber;
	}

	public void setOrdersNumber(Integer ordersNumber) {
		this.ordersNumber = ordersNumber;
	}

}
