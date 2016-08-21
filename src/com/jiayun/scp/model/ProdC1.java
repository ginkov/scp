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
@Table(name="prod_cat1")
public class ProdC1 {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="c1")
	private Set<ProdC2> c2list;
	
	public ProdC1() {
		c2list = new HashSet<ProdC2>();
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
	public Set<ProdC2> getC2list() {
		return c2list;
	}
	public void setC2list(Set<ProdC2> c2list) {
		this.c2list = c2list;
	}

}
