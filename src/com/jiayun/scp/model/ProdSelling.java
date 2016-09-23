package com.jiayun.scp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="prod_selling")
public class ProdSelling {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private double listprice;
	
	@Column(name="online_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date onlineDate;
	
	@Column(name="offline_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date offlineDate;
	
	@ManyToOne
	@JoinColumn(name="cat2_id")
	private ProdC2 c2;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="selling", orphanRemoval=true)
	private List<ProdPartItem> partslist;
	
	public ProdSelling() {
		onlineDate = new Date();
		c2 = new ProdC2();
		partslist = new ArrayList<ProdPartItem>();
		listprice = 0.0;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getListprice() {
		return listprice;
	}
	
	public void genListprice() {
		double lp = 0.0;
		for(ProdPartItem ppi: partslist) {
			lp += ppi.getPart().getListprice() * ppi.getQuantity();
		}
		this.listprice = lp;
	}

	public void setListprice(double listprice) {
		this.listprice = listprice;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public ProdC2 getC2() {
		return c2;
	}

	public void setC2(ProdC2 c2) {
		this.c2 = c2;
	}

	public List<ProdPartItem> getPartslist() {
		return partslist;
	}

	public void setPartslist(List<ProdPartItem> partslist) {
		this.partslist = partslist;
	}
	
	public List<ProdPartItem> removeEmptyItems() {
		List<ProdPartItem> toBeDel = new ArrayList<>();
		for(ProdPartItem i : partslist) {
			if(i.isEmpty()) toBeDel.add(i);
		}
		partslist.removeAll(toBeDel);
		return toBeDel;
	}
	
	
	
}
