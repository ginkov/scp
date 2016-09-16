package com.jiayun.scp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table
public class Invoice {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String sn;

	@Column
	private String description;
	
	@Column(name="issue_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	
	@Column
	private String issuer;
	
	@Column
	private double amount;
	
	@Column(name="expense_type")
    @Enumerated(EnumType.STRING)
	private InvoiceType type;
	
	@Column(name = "is_original")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean original;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="expense_invoice_join", joinColumns=@JoinColumn(name="invoice_id"), 
			inverseJoinColumns=@JoinColumn(name="expense_id"))
	private Set<ExpRecord> erSet;
	
	@Transient
	private boolean used;
	
	
	public Invoice() {
		date = new Date();
		amount = 0.0;
		type = InvoiceType.EXPENSE;
		erSet = new HashSet<ExpRecord>();
		original = false;
		used = false;
	}
	
	public final void clearErSet() {
		this.erSet = new HashSet<ExpRecord>();
	}

	public void copyFrom(ExpRecord er) {
		this.sn = er.getInvoiceNum();
		this.description = er.getSummary();
		this.date = er.getDate();
		this.issuer = er.getSupplierName();
		this.amount = er.getAmount();
		this.original = true;
		this.erSet.add(er);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public InvoiceType getType() {
		return type;
	}

	public void setType(InvoiceType type) {
		this.type = type;
	}

	public Set<ExpRecord> getErSet() {
		return erSet;
	}

	public void setErSet(Set<ExpRecord> erSet) {
		this.erSet = erSet;
	}

	public boolean getOriginal() {
		return original;
	}

	public void setOriginal(boolean original) {
		this.original = original;
	}

	public boolean isUsed() {
		if(erSet.size()>0) {
			this.used = true;
		}
		return used;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}
	
	
	
	
}
