package com.jiayun.scp.util.pairs;

import java.util.Comparator;

public class ComparatorInRange implements Comparator<Item>{
	
	private int range;
	
	public ComparatorInRange(int range) {
		this.range = range;
	}

	@Override
	/**
	 * o1 相当于发票, o2 相当于支出. 发票要大于支出.
	 */
	public int compare(Item o1, Item o2) {
		int d = o1.getAmount() - o2.getAmount();
		if(d>=0 && d<=range) {
			return 0;
		}
		else if(d<0) {
			return -1;
		}
		else {
			return 1;
		}
	}

}
