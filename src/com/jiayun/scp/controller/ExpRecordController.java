package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.ExpRecord;
import com.jiayun.scp.model.ExpT1;
import com.jiayun.scp.model.ExpT2;
import com.jiayun.scp.model.Staff;
import com.jiayun.scp.util.ExpRecordUtil;

@Controller
@RequestMapping("/finance/expense")
public class ExpRecordController {
	
	@Autowired
	private SessionFactory sf;
	
	@Autowired
	private DaoService<ExpRecord> ers;
	
	@Autowired
	private DaoService<Staff> ss;
	
	@Autowired
	private DaoService<ExpT1> et1s;
	
	@Autowired
	private DaoService<ExpT2> et2s;
	
	@Autowired
	private ExpRecordUtil erUtil;
	
	@RequestMapping(value = {"/list"})
	public String list(Model model) {
		List<ExpRecord> l = ers.getAll();
		model.addAttribute("totalExp", getTotalExpense(l));
		model.addAttribute("ers", l);
		model.addAttribute("pageTitle","支出列表");
		model.addAttribute("pageContent", "finance/ExpRecordList");
		return "mainpage";	
	}
	
	@RequestMapping("/input")
	public String input(Model model, @ModelAttribute ExpRecord er) {
		if(er.getSn()==null || er.getSn().isEmpty()) {
			String sn = erUtil.genSN();
			er.setSn(sn);
		}
		Gson gson = new Gson();
		model.addAttribute("t1names", gson.toJson(getT1Names()));
		model.addAttribute("t2names", gson.toJson(getT2Names()));
		model.addAttribute("staffs", getNormalStaffList());
		model.addAttribute("er", er);
		model.addAttribute("pageTitle","新订单");
		model.addAttribute("pageContent", "finance/ExpRecordInput");
		return "mainpage";
	}
	
	@RequestMapping("/save")
	public String save(ExpRecord er) {
		// set staff
		Staff s = ss.getByName(er.getStaff().getName());
		er.setStaff(s);
		// set owner
		er.setOwner(ss.getByName(er.getOwner().getName()));
		er.setT2(et2s.getByName(er.getT2().getName()));
		ers.save(er);
		return "redirect:/finance/expense/list";
	}

	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable int id) {
		model.addAttribute("er",ers.getById(id));
		model.addAttribute("pageTitle","支出详情");
		model.addAttribute("pageContent", "finance/ExpRecordDetail");
		return "mainpage";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Gson gson = new Gson();
		model.addAttribute("t1names", gson.toJson(getT1Names()));
		model.addAttribute("t2names", gson.toJson(getT2Names()));
		model.addAttribute("staffs", getNormalStaffList());
		model.addAttribute("er",ers.getById(id));
		model.addAttribute("pageTitle","修改支出记录");
		model.addAttribute("pageContent", "finance/ExpRecordEdit");
		return "mainpage";
	}
	
	@RequestMapping("/update")
	public String update(ExpRecord er, HttpServletRequest request, RedirectAttributes ra) {
		String currentUser = request.getUserPrincipal().getName();
		if(er.getOwner().getName().equals(currentUser)) {
			ExpRecord old = ers.getById(er.getId());
			old.setAmount(er.getAmount());
			old.setDate(er.getDate());
			old.setExpName(er.getExpName());
			old.setInvoiceNum(er.getInvoiceNum());
			old.setStaff(ss.getByName(er.getStaff().getName()));
			old.setSummary(er.getSummary());
			old.setSupplierName(er.getSupplierName());
			old.setT2(et2s.getByName(er.getT2().getName()));
			ers.update(old);
		}
		else {
			ra.addFlashAttribute("err", "只有记录人才能修改本条记录");
		}
		return "redirect:/finance/expense/list";
	}
	
	@RequestMapping("/del/{id}")
	public String del(@PathVariable int id, HttpServletRequest request, RedirectAttributes ra) {
		String currentUser = request.getUserPrincipal().getName();
		ExpRecord er = ers.getById(id);
		if(er.getOwner().getName().equals(currentUser)) {
			ers.delById(id);
		}
		else {
			ra.addFlashAttribute("err","只有记录人才能删除本记录");
		}
		return "redirect:/finance/expense/list";
	}

	private List<Staff> getNormalStaffList(){
		Staff superStaff = ss.getByName("super");
		List<Staff> staffs = ss.getAll();
		staffs.remove(superStaff);
		return staffs;
	}
	
	private List<String> getT1Names(){
		List<String> t1names = new ArrayList<>();
		for(ExpT1 t1: et1s.getAll()) {
			t1names.add(t1.getName());
		}
		return t1names;
	}
	
	private Map<String, List<String>> getT2Names(){
		Map<String, List<String>> t2names = new HashMap<>();
		for(ExpT1 t1: et1s.getAll()) {
			List<String> t2s = new ArrayList<>();
			for(ExpT2 t2: t1.getT2list()) {
				t2s.add(t2.getName());
			}
			t2names.put(t1.getName(), t2s);
		}
		return t2names;
	}
	
	private double getTotalExpense(List<ExpRecord> l) {
		double sum = 0.0;
		for(ExpRecord er: l) {
			sum += er.getAmount();
		}
		return sum;
	}
}
