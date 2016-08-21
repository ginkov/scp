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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="org_type_id")
	private OrgType orgType;
	
	@Column(name="name")
	private String name;
	
	@Column(name="contact_name")
	private String contactName;
	
	@Column(name="reg_date")
	private Date registerTime;
	
	@Column(name="phone_1")
	private String phone1;
	
	@Column(name="phone_2")
	private String phone2;
	
	@Column(name="phone_3")
	private String phone3;
	
	@Column(name="phone_4")
	private String phone4;
	
	@Column(name="description")
	private String description;
	
	@Column(name="name_and_phone")
	private String nameAndPhone;
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinTable(name = "customer_address_join", joinColumns=@JoinColumn(name="customer_id"), inverseJoinColumns=@JoinColumn(name="address_id"))
	private List<CustomerAddress> addresses;
	
    public Customer() {
    	addresses = new ArrayList<CustomerAddress>();
    	registerTime = new Date();
    }
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OrgType getOrgType() {
		return orgType;
	}
	public void setOrgType(OrgType orgType) {
		this.orgType = orgType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getPhone4() {
		return phone4;
	}
	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNameAndPhone() {
		return nameAndPhone;
	}

	public void setNameAndPhone(String nameAndPhone) {
		this.nameAndPhone = nameAndPhone;
	}

	public List<CustomerAddress> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
	}
	
	public synchronized List<CustomerAddress> removeEmptyAddresses() {
		List<CustomerAddress> toBeDeleted = new ArrayList<>();
		for(CustomerAddress ca: addresses) {
			if(ca.isEmpty()) toBeDeleted.add(ca);
		}
		addresses.removeAll(toBeDeleted);
		return toBeDeleted;
	}

	public void addAddress(CustomerAddress address) {
		addresses.add(address);
	}

	public void delAddressById(int id) {
		CustomerAddress toBeDel = null;
		for(CustomerAddress a : addresses) {
			if(a.getId() == id) {
				toBeDel = a;
			}
		}
		if(toBeDel != null) {
			addresses.remove(toBeDel);
		}
	}
	
	public void genNameAndPhone() {
		if(phone1 == null || phone1.isEmpty()) {
			this.nameAndPhone = name;
		}
		else {
			this.nameAndPhone = name + "_" + phone1;
		}
	}
	
	
//	@Override
//	public String toString() {
//		if(addresses.size() > 0) {
//			return String.format(""
//				+ "name         = %s\n"
//				+ "contact      = %s\n"
//				+ "phone1       = %s\n"
//				+ "address.city = %s\n\n", name, contactName, phone1, addresses.get(0).getCity());
//		}
//		else {
//			return String.format(""
//				+ "name         = %s\n"
//				+ "contact      = %s\n"
//				+ "phone1       = %s\n"
//				+ "address.city = 没有地址\n\n", name, contactName, phone1, addresses.get(0).getCity());
//		}
//	}
	
	
}
