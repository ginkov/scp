package com.jiayun.scp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jiayun.scp.model.ExpRecord;
import com.jiayun.scp.model.Invoice;
import com.jiayun.scp.util.pairs.Item;
import com.jiayun.scp.util.pairs.PairUtil;

/**
 * 提供 Invoice 的各种操作，如匹配等
 * @author xinyin
 *
 */
public class InvoiceUtil {
	
	private Map<Set<Invoice>, Set<ExpRecord>> matches;
	
	private List<Invoice> il;
	private List<ExpRecord> el;
	
	public InvoiceUtil() {
		matches = new HashMap<Set<Invoice>, Set<ExpRecord>>();
	}
	
	public void go() {
		List<Item> a = genFromInvoice(il);
		List<Item> b = genFromExp(el);
		PairUtil pu = new PairUtil(a, b);
		pu.go();
		for(Entry<Set<Integer>, Set<Integer>> e: pu.getMatch().entrySet()) {
			Set<Invoice> is = new HashSet<>();
			for(Integer i: e.getKey()) {
				is.add(il.get(i));
			}
			Set<ExpRecord> es = new HashSet<>();
			for(Integer i: e.getValue()) {
				es.add(el.get(i));
			}
			matches.put(is, es);
		}
	}
	
	
	private List<Item> genFromInvoice(List<Invoice> l){
		List<Item> p = new ArrayList<>();
		for(int i = 0; i<l.size(); i++) {
			Item pi = new Item();
			pi.setAmount((int)(l.get(i).getAmount()*100));
			pi.initIndex(i);
			p.add(pi);
		}
		return p;
	}

	private List<Item> genFromExp(List<ExpRecord> l){
		List<Item> p = new ArrayList<>();
		for(int i = 0; i < l.size(); i++) {
			Item pi = new Item();
			pi.setAmount((int)(l.get(i).getAmount()*100));
			pi.initIndex(i);
			p.add(pi);
		}
		return p;
	}


	public List<Invoice> getInvList() {
		return il;
	}

	public void setInvList(List<Invoice> invList) {
		this.il= invList;
	}

	public List<ExpRecord> getExpList() {
		return el;
	}

	public void setExpList(List<ExpRecord> expList) {
		this.el= expList;
	}

	public Map<Set<Invoice>, Set<ExpRecord>> getMatch() {
		return matches;
	}

	public void setMatch(Map<Set<Invoice>, Set<ExpRecord>> match) {
		this.matches = match;
	};
	
}