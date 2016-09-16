package com.jiayun.scp.util.pairs;

import java.util.HashSet;
import java.util.Set;

public class Item implements Comparable<Item>{

	private Integer amount;
	private Set<Integer> indices;

	
	public Item() {
		amount = 0;
		indices = new HashSet<Integer>();
	}
	@Override
	public int compareTo(Item o) {
		return amount.compareTo(o.getAmount()); 
	}
	
	public Item add(Item i) {
		if(i.getIndices().size() >0) {
			this.amount += i.amount;
			this.indices.addAll(i.indices);
		}
		return this;
	}

	public Integer getAmount() {
		return amount;
	}

	public Set<Integer> getIndices() {
		return indices;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public void initIndex(int i) {
		indices.add(i);
	}
}
