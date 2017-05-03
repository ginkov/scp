package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.jiayun.scp.model.Invoice;
import com.jiayun.scp.model.Role;
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
	private DaoService<Invoice> invs;
	
	@Autowired
	private DaoService<Staff> ss;
	
	@Autowired
	private DaoService<ExpT1> et1s;
	
	@Autowired
	private DaoService<ExpT2> et2s;
	
	@Autowired
	private ExpRecordUtil erUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = {"/list"})
	public String list(Model model, HttpServletRequest hsr) {

		// get current user
		String username = hsr.getUserPrincipal().getName();
		Staff user = ss.getByName(username);
		
		// whether has FINANCE role
		boolean isFinance = false;
		for(Role r: user.getRoles()) {
			if("ROLE_FINANCE".equals(r.getRole())) {
				isFinance = true;
				break;
			}
		}
		
		List<ExpRecord> l ;
		if(isFinance) {
			// 如果在财务组里，显示所有的列表
			l = ers.getAll();
		}
		else {
			// 如果不在财务组里，只查询 Owner 是自己的
			Session session = sf.getCurrentSession();
//			l = session.createCriteria(ExpRecord.class).createAlias("owner", "o")
//					.createAlias("o.name", "ownername").add(Restrictions.eq("ownername", username)).list();
			l = session.createCriteria(ExpRecord.class).add(Restrictions.eq("owner", user)).list();
		}
		
		model.addAttribute("totalExp", getTotalExpense(l));
		model.addAttribute("ers", l);
		model.addAttribute("pageTitle","支出列表");
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "M";
		}
		
		model.addAttribute("pageContent", "finance/ExpRecordList"+mobileSuffix);
		return "mainpage"+mobileSuffix;	
	}
	
	@RequestMapping("/input")
	public String input(Model model, @ModelAttribute ExpRecord er, HttpServletRequest hsr) {
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
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "M";
		}
		
		model.addAttribute("pageContent", "finance/ExpRecordInput"+mobileSuffix);
		return "mainpage"+mobileSuffix;
	}
	
	@RequestMapping("/save")
	public String save(ExpRecord er, HttpServletRequest hsr) {
		// set staff
		Staff s = ss.getByName(er.getStaff().getName());
		er.setStaff(s);
		// set owner
		er.setOwner(ss.getByName(er.getOwner().getName()));
		er.setT2(et2s.getByName(er.getT2().getName()));
		// set invoice
		String invNum = er.getInvoiceNum();
		if(invNum != null) {invNum = invNum.trim();}
		Invoice inv = null;
		if(invNum != null && ! invNum.isEmpty()) {
			inv = invs.getByUniqueString("sn", invNum);
			if(inv == null) {
				inv = new Invoice();
				// 在保存发票之前，由于 Invoice 对象里面反过来也引用了这个 ExpRecord 对象，因此要先临时保存一下这个 Invoice 对象
				inv.setSn(invNum);
				inv.setDescription(er.getSummary());
				inv.setDate(er.getDate());
				inv.setIssuer(er.getSupplierName());
				inv.setAmount(er.getAmount());
				inv.setOriginal(true);
				invs.save(inv);
			//TODO: 把支出类型与财务类型匹配
			}
			er.getInvoiceSet().add(inv);
		}
		ers.save(er);
		if(inv != null) {
			// 保存了本 ExpRecord 记录之后，在更新相应的 Invoice 信息，然后保存到数据库里
//			inv = invs.getByUniqueString("sn", invNum); // 必须要先从数据库里读出一次，再修改才算是更新
//			inv.copyFrom(er);
//			invs.update(inv);
		}
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "?mobile";
		}
		
		return "redirect:/finance/expense/list"+mobileSuffix;
	}

	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable int id, HttpServletRequest hsr) {
		ExpRecord er = ers.getById(id);
		model.addAttribute("er",er);


		Set<Invoice> il = er.getInvoiceSet();
		Set<ExpRecord> el;
		if(il.size()>0) {
			el = il.iterator().next().getErSet();
		}
		else {
			el = new HashSet<ExpRecord>();
		}
		
		model.addAttribute("il", il);
		model.addAttribute("el", el);
		model.addAttribute("pageTitle","支出详情");
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "M";
		}
		
		model.addAttribute("pageContent", "finance/ExpRecordDetail"+mobileSuffix);
		return "mainpage"+mobileSuffix;
	}
	
	@RequestMapping("/detach/{id}")
	public String detach(@PathVariable int id, HttpServletRequest hsr) {
		ExpRecord er = ers.getById(id);
		Set<Invoice>   il = er.getInvoiceSet();
		Set<ExpRecord> el = il.iterator().next().getErSet();
		for(ExpRecord e: el) {
			e.clearInvSet();
			e.setInvoiceNum("");
			ers.save(e);
		}
		for(Invoice i: il) {
			i.clearErSet();
			invs.save(i);
		}
		return "redirect:/finance/expense/detail/"+id;
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id, HttpServletRequest hsr) {
		Gson gson = new Gson();
		model.addAttribute("t1names", gson.toJson(getT1Names()));
		model.addAttribute("t2names", gson.toJson(getT2Names()));
		model.addAttribute("staffs", getNormalStaffList());
		model.addAttribute("er",ers.getById(id));
		model.addAttribute("pageTitle","修改支出记录");
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "M";
		}
		model.addAttribute("pageContent", "finance/ExpRecordEdit"+mobileSuffix);
		return "mainpage"+mobileSuffix;
	}
	

	
	@RequestMapping("/update")
	public String update(ExpRecord er, HttpServletRequest request, RedirectAttributes ra) {
		String currentUser = request.getUserPrincipal().getName();
		if(er.getOwner().getName().equals(currentUser)) {
			ExpRecord old = ers.getById(er.getId());
			old.setAmount(er.getAmount());
			old.setDate(er.getDate());
			old.setExpName(er.getExpName());
			
			if(! er.getInvoiceNum().isEmpty()) {
				if(old.getInvoiceSet().size()>1) {
					old.setInvoiceNum("");
				}
				else if(old.getInvoiceSet().size() == 1) {
					old.getInvoiceSet().iterator().next().setSn(er.getInvoiceNum());
				}
				else {
					Invoice invoice = new Invoice();
					invoice.copyFrom(er);
					old.getInvoiceSet().add(invoice);
				}
			}

			old.setStaff(ss.getByName(er.getStaff().getName()));
			old.setSummary(er.getSummary());
			old.setSupplierName(er.getSupplierName());
			old.setT2(et2s.getByName(er.getT2().getName()));
			ers.update(old);
		}
		else {
			ra.addFlashAttribute("err", "只有记录人才能修改本条记录");
		}
		
		String mobileSuffix = "";
		if(request.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "?mobile";
		}
		
		return "redirect:/finance/expense/list"+mobileSuffix;
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
		
		String mobileSuffix = "";
		if(request.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "?mobile";
		}
		
		return "redirect:/finance/expense/list"+mobileSuffix;
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
