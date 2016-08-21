package com.jiayun.scp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sales_item")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="prod_selling_id")
	private ProdSelling prodSelling;
	
	@Column
	private Integer quantity;
	
	@Column(name="list_price")
	private Double listPrice;
	
	@Column(name="discount_price")
	private Double discountPrice;
	
	@Column
	private Double discount;
	
	@Column(name="total_price")
	private Double totalPrice;
	
	@Column
	private String description;
	
	public OrderItem() {
		prodSelling = new ProdSelling();
		quantity = 0;
		listPrice = 0.0;
		discount = 1.0;
		discountPrice = 0.0;
		totalPrice = 0.0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProdSelling getProdSelling() {
		return prodSelling;
	}

	public void setProdSelling(ProdSelling prodSelling) {
		this.prodSelling = prodSelling;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
//	@Override
//	public String toString() {
//		return String.format(""
//				+ "product name =%s\n"
//				+ "product id   =%d\n"
//				+ "list price   =%.2f\n"
//				+ "discount     =%.1f\n", prodSelling.getName(), prodSelling.getId(), listPrice, discount);
//	}
//	
	public boolean isEmpty() {
		return (quantity == null || quantity ==0);
	}
	
	public void genDiscount() {
		if(listPrice < 0.01) {
			discount = 1.0;
		}
		else {
			discount = discountPrice / listPrice;
		}
	}
	
}