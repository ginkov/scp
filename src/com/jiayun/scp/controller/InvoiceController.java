package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.ExpRecord;
import com.jiayun.scp.model.Invoice;
import com.jiayun.scp.model.Invoice2Expense;
import com.jiayun.scp.model.InvoiceType;
import com.jiayun.scp.util.InvoiceUtil;

@Controller
@RequestMapping("/finance/invoice")
public class InvoiceController {
	
	@Autowired
	private DaoService<Invoice> invs;
	
	@Autowired
	private DaoService<ExpRecord> ers;
	
	private final String HOME = "redirect:/finance/invoice/list";

	@RequestMapping("/list")
	public String list(Model model) {
		List<Invoice> l = invs.getAll();
		model.addAttribute("invoiceList", l);
		List<Invoice> unused = getUnused(l);
		l.removeAll(unused);
		
		double invAmtUnPaired = 0.0;
		for(Invoice i : unused) { invAmtUnPaired += i.getAmount(); }
		
		double invAmtPaired = 0.0;
		for(Invoice i: l) { invAmtPaired += i.getAmount(); }
		
		double expAmtPaired = 0.0;
		double expAmtUnPaired = 0.0;
		List<ExpRecord> el = ers.getAll();
		for(ExpRecord e: el) {
			if(e.isPaired()) {
				expAmtPaired += e.getAmount();
			} else {
				expAmtUnPaired += e.getAmount();
			}
		}
		
		model.addAttribute("invAmtPaired",   invAmtPaired);
		model.addAttribute("invAmtUnPaired", invAmtUnPaired);
		model.addAttribute("expAmtPaired",   expAmtPaired);
		model.addAttribute("expAmtUnPaired", expAmtUnPaired);
		model.addAttribute("unused", unused);
		model.addAttribute("i2eList", genInv2Exp(l));
		model.addAttribute("pageTitle","发票列表");
		model.addAttribute("pageContent", "finance/InvoiceList");
		return "mainpage";
	}
	
	@RequestMapping("/detail/{id}")
	@Transactional
	public String detail(Model model, @PathVariable int id) {
		Invoice inv = invs.getById(id);
		if(inv.isUsed()) {
			model.addAttribute("el", inv.getErSet());
			model.addAttribute("il",inv.getErSet().iterator().next().getInvoiceSet());
		}
		model.addAttribute("invoice", inv);
		model.addAttribute("pageTitle","发票信息");
		model.addAttribute("pageContent", "finance/InvoiceDetail");
		return "mainpage";
	}

	@RequestMapping("/input")
	public String input(Model model) {
		model.addAttribute("invoice", new Invoice());
		model.addAttribute("invType", InvoiceType.values());
		model.addAttribute("pageTitle","添加新发票");
		model.addAttribute("pageContent", "finance/InvoiceInput");
		return "mainpage";
	}

	@RequestMapping("/save")
	public String save(@ModelAttribute Invoice invoice, RedirectAttributes ra) {
		String err="";
		try {
			invs.save(invoice);
		}
		catch(ConstraintViolationException e) {
			err="已有同号发票存在";
		}
		ra.addFlashAttribute("err", err);
		return HOME;
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute Invoice invoice, RedirectAttributes ra) {
		String err="";
		Invoice o = invs.getById(invoice.getId());
		o.setAmount(invoice.getAmount());
		o.setDate(invoice.getDate());
		o.setDescription(invoice.getDescription());
		o.setIssuer(invoice.getIssuer());
		o.setOriginal(invoice.getOriginal());
		o.setSn(invoice.getSn());
		o.setType(invoice.getType());
		try {
			invs.update(o);
		}
		catch(ConstraintViolationException e) {
			err="已有同号发票存在";
		}
		ra.addFlashAttribute("err", err);
		return HOME;
	}

	@RequestMapping("/del/{id}")
	public String del(Model model, @PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			invs.delById(id);
		}catch(DataIntegrityViolationException e) {
			err="记录使用中, 无法删除";
		}
		ra.addFlashAttribute("err", err);
		return HOME;
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Invoice inv = invs.getById(id);
		model.addAttribute("invoice", inv);
		model.addAttribute("pageTitle","编辑发票");
		model.addAttribute("pageContent", "finance/InvoiceEdit");
		return "mainpage";
	}
	
	@RequestMapping("/detach/{id}")
	public String detach(@PathVariable int id) {
		Invoice invoice = invs.getById(id);
		Set<ExpRecord> el = invoice.getErSet();
		Set<Invoice>   il = el.iterator().next().getInvoiceSet();
		for(ExpRecord e: el) {
			e.clearInvSet();
			e.setInvoiceNum("");
			ers.save(e);
		}
		for(Invoice i: il) {
			i.clearErSet();
			invs.save(i);
		}
		return "redirect:/finance/invoice/detail/"+id;
	}
	
	@RequestMapping("/autopair")
	public String autopair() {
		List<Invoice> unusedInvoices = new ArrayList<>();
		for(Invoice i : invs.getAll()) {
			if(! i.isUsed()) { unusedInvoices.add(i); }
		}
		
		List<ExpRecord> unpairedExp = new ArrayList<>();
		for(ExpRecord e: ers.getAll()) {
			if(! e.isPaired()) {unpairedExp.add(e);}
		}
		InvoiceUtil iu = new InvoiceUtil();
		iu.setInvList(unusedInvoices);
		iu.setExpList(unpairedExp);
		iu.go();
		for(Entry<Set<Invoice>, Set<ExpRecord>> e: iu.getMatch().entrySet()) {
			for(Invoice inv: e.getKey()) {
				inv.setErSet(e.getValue());
				invs.update(inv);
			}
			for(ExpRecord er: e.getValue()) {
				er.setInvoiceSet(e.getKey());
				ers.update(er);
			}
		}
		return HOME;
	}

	private List<Invoice> getUnused(List<Invoice> all){
		List<Invoice> unused = new ArrayList<>();
		for(Invoice i : all) {
			if(! i.isUsed()) {
				unused.add(i);
			}
		}
		return unused;
	}
	
	private List<Invoice2Expense>genInv2Exp(List<Invoice> l) {
		List<Invoice2Expense> ie = new ArrayList<>();
		while(l.size()>0) {
			Invoice t = l.get(0);
			Set<ExpRecord> el = t.getErSet();
			Set<Invoice>   il = el.iterator().next().getInvoiceSet();
			Invoice2Expense i2e = new Invoice2Expense();
			i2e.setEl(el);
			i2e.setIl(il);
			i2e.sum();
			ie.add(i2e);
			l.removeAll(il);
		}
		return ie;
	}
}
