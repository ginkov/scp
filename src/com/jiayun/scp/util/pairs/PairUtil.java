package com.jiayun.scp.util.pairs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 对两组金额量进行匹配.
 * <br>
 * 比如发票和支出.
 * @author xinyin
 *
 */
public class PairUtil {
	
	private static final int SLICE_RANGE = 8;
	
	private Map<Set<Integer>,Set<Integer>> matches;
	private List<Item> a,b;
	
	public PairUtil(List<Item> a, List<Item> b) {
		this.a = a;
		this.b = b;
		matches = new HashMap<Set<Integer>, Set<Integer>>();
	}
	
	public void go() {
		// 先一对一精确匹配
		Comparator<Item> c0   = new ComparatorInRange(0);
		pair(a,b,c0);
		
		// 再一对一模糊匹配
		Comparator<Item> c200 = new ComparatorInRange(200);
		pair(a,b,c200);
		
		// 剩下的是无法一对一匹配的. 进行 8 个一组的切片
		if(a.size() > 0 && b.size() >0) {
			List<List<Item>> sliceA = slice(a, SLICE_RANGE);
			List<List<Item>> sliceB = slice(b, SLICE_RANGE);
			
			int s = Math.min(sliceA.size(), sliceB.size());
			for(int i = 0; i<s; i++) {
				List<Item> pa = powerSet(sliceA.get(i));
				List<Item> pb = powerSet(sliceB.get(i));
				pair(pa,pb, c0);
				pair(pa,pb, c200);
			}
		}
	}

	
	private void pair(List<Item> a, List<Item> b, Comparator<Item> c) {
		List<Item> matchedA = new ArrayList<>();  
		List<Item> matchedB = new ArrayList<>();
		
		int i = 0;
		int j = 0;
		Collections.sort(a);
		Collections.sort(b);
		Collections.reverse(a);
		Collections.reverse(b);
		while(i<a.size() && j<b.size()) {
//			System.out.println("a="+a.get(i).getAmount()+"\tb="+b.get(j).getAmount());
			int d = c.compare(a.get(i),b.get(j)); 
			if(d==0) {
				matches.put(a.get(i).getIndices(), b.get(j).getIndices());
				matchedA.add(a.get(i));
				matchedB.add(b.get(j));
				++i;
				++j;
			}
			else if(d>0) { ++i; }
			else { ++j; }
		}
		a.removeAll(matchedA);
		b.removeAll(matchedB);
	}
	
	private List<Item> powerSet(List<Item> l) {
		List<Item> ps = new ArrayList<>();
		int n = l.size();
		
		for( long i = 0; i < (1 << n); i++) {
			Item item = new Item();
		    for( int j = 0; j < n; j++ )
		        if( (i >> j) % 2 == 1 ) item.add(l.get(j));
		    if (item.getAmount() > 0) ps.add(item); 
		}

		return ps;
	}
	
	/**
	 * 把列表 l 按 n 为一组, 均匀切片.
	 * @param l
	 * @param range
	 * @return
	 */
	private List<List<Item>> slice(List<Item> list, int n){
		int len = list.size();
		int nGrp = len / n;
		if(len%n >0) ++nGrp;
		
		List<List<Item>> result = new ArrayList<List<Item>>();
		for(int i=0; i< nGrp; i++) {
			result.add(new ArrayList<Item>());
		}
		for(int i=0, j=0; i<len; i++, j++) {
			if(j==nGrp) {
				j=0;
			}
			result.get(j).add(list.get(i));
		}
		return result;
	}

	public Map<Set<Integer>, Set<Integer>> getMatch() {
		return matches;
	}

	public void setMatch(Map<Set<Integer>, Set<Integer>> matches) {
		this.matches = matches;
	}
	

}
