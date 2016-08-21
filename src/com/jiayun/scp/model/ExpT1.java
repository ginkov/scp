package com.jiayun.scp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="exp_type1")
public class ExpT1 {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="t1")
	private Set<ExpT2> t2list;
	
	public ExpT1() {
		t2list = new HashSet<ExpT2>();
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
	public Set<ExpT2> getT2list() {
		return t2list;
	}
	public void setT2list(Set<ExpT2> t2list) {
		this.t2list = t2list;
	}
	
	
}
