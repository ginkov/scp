package com.jiayun.scp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="exp_record")
public class ExpRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String sn;

	@Column(name="exp_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	
	@Column(name="supplier")
	private String supplierName;
	
	@Column(name="invoice_num")
	private String invoiceNum;
	
	@ManyToOne
	@JoinColumn(name="t2_id")
	private ExpT2 t2;
	
	@Column
	private String summary;
	
	@Column(name="exp_name")
	private String expName;
	
	@Column
	private double amount;
	
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private Staff owner;
	
	public ExpRecord() {
		date = new Date();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public ExpT2 getT2() {
		return t2;
	}
	public void setT2(ExpT2 t2) {
		this.t2 = t2;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Staff getOwner() {
		return owner;
	}

	public void setOwner(Staff owner) {
		this.owner = owner;
	}

	
}
