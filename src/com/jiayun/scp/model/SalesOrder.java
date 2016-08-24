package com.jiayun.scp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="sales_order")
public class SalesOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String sn;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="user_sale_type_id")
	private UserSaleType userSaleType;
	
	@Column(name="prod_summary")
	private String prodSummary;
	
	@Column(name="order_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	
	@Column(name="deliver_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deliverDate;
	
	@Column(name="list_price")
	private Double listPrice;
	
	@Column(name="discount_price")
	private Double discountPrice;
	
	@Column
	private Double discount;
	
	@Column(name="channel_name")
	private String channelName;
	
	@Column
	private String description;
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinTable(name = "order_item_join", joinColumns=@JoinColumn(name="order_id"), inverseJoinColumns=@JoinColumn(name="sales_item_id"))
	private List<OrderItem> items;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinTable(name = "order_service_join", joinColumns=@JoinColumn(name="order_id"), inverseJoinColumns=@JoinColumn(name="service_record_id"))
	private List<ServiceRecord> serviceRecords;
	
    @Enumerated(EnumType.STRING)
    @Column(name="paystatus")
    private PayStatus payStatus;
    
    @Column(name="paydate")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date payDate;
    
	public SalesOrder() {
		items = new ArrayList<OrderItem>();
		serviceRecords = new ArrayList<ServiceRecord>();
		orderDate = new Date();
		deliverDate = null;
		listPrice = 0.0;
		discount = 0.0;
		discountPrice = 0.0;
		payStatus = PayStatus.UNPAID;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public UserSaleType getUserSaleType() {
		return userSaleType;
	}

	public void setUserSaleType(UserSaleType userSaleType) {
		this.userSaleType = userSaleType;
	}

	public String getProdSummary() {
		return prodSummary;
	}

	public void setProdSummary(String prodSummary) {
		this.prodSummary = prodSummary;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> orderItems) {
		this.items = orderItems;
	}

	public List<ServiceRecord> getServiceRecords() {
		return serviceRecords;
	}

	public void setServiceRecords(List<ServiceRecord> serviceRecords) {
		this.serviceRecords = serviceRecords;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public void updatePriceAndDiscount() {
		updateListPrice();
		updateDiscountPrice();
		updateDiscount();
	}
	
	private void updateListPrice() {
		listPrice = 0.0;
		for(OrderItem i : items) {
			listPrice += i.getListPrice() * i.getQuantity();
		}
	}
	private void updateDiscount() {
		if (listPrice < 0.0001) {
			discount = 1.0;
		}
		else {
			discount = discountPrice / listPrice;
		}
	}
	private void updateDiscountPrice() {
		discountPrice = 0.0;
		for(OrderItem i: items) {
			discountPrice += i.getDiscountPrice() * i.getQuantity();
		}
	}
	@Override
	public String toString() {
		return String.format(""
				+ "deliverDate = %d", deliverDate);
	}
	
	public List<OrderItem> removeEmptyItems() {
		List<OrderItem> toBeDel = new ArrayList<>();
		for(OrderItem i : items) {
			if(i.isEmpty()) toBeDel.add(i);
		}
		items.removeAll(toBeDel);
		return toBeDel;
	}
	
	public List<ServiceRecord> removeEmptyServiceRecords() {
		List<ServiceRecord> toBeDel = new ArrayList<>();
		for(ServiceRecord sr: serviceRecords) {
			if(sr.isEmpty()) toBeDel.add(sr);
		}
		serviceRecords.removeAll(toBeDel);
		return toBeDel;
	}
	
	public void updateProductSummary() {
		Set<String> p = new HashSet<>();
		for(OrderItem o : items) {
			p.add(o.getProdSelling().getName());
		}
		String [] l = new String[] {};
		l = p.toArray(l);
		
		StringBuffer sb = new StringBuffer();
		if(l.length>0) {
			sb.append(l[0]);
		}
		for(int i=1; i<l.length; i++) {
			sb.append("; ");
			sb.append(l[i]);
		}
		prodSummary = sb.toString();
	}
	
	public boolean hasProdSelling(ProdSelling pm) {
		if(pm==null) return false;
		for(OrderItem o: items) {
			if(o!=null && o.getProdSelling()!=null &&
					o.getProdSelling().getId()!=null && o.getProdSelling().getId().equals(pm.getId())) return true;
		}
		return false;
	}
	
	public boolean hasUserSaleType(UserSaleType ut) {
		if(ut==null || userSaleType == null) return false;
		if(ut.getId()!=null && userSaleType.getId()!=null && ut.getId().equals(userSaleType.getId())) return true;
		return false;
	}
	
	

}
