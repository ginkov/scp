package com.jiayun.scp.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
import com.jiayun.scp.model.Customer;
import com.jiayun.scp.model.CustomerAddress;
import com.jiayun.scp.model.OrgType;
import com.jiayun.scp.model.SalesOrder;


@Controller
@RequestMapping("/customer/customer")
public class CustomerController {

	@Autowired
	private SessionFactory sf;
	
	@Autowired
	private DaoService<Customer> cs;
	
	@Autowired
	private DaoService<CustomerAddress> cas;

	@Autowired
	private DaoService<OrgType> ots;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<Customer> l = cs.getAll();
		model.addAttribute("customerList", l);
		model.addAttribute("pageTitle","客户列表");
		model.addAttribute("pageContent", "customer/CustomerList");
		return "mainpage";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/detail/{id}")
	@Transactional
	public String detial(Model model, @PathVariable int id) {
		Session session = sf.getCurrentSession();
		Customer c = cs.getById(id);
		List<SalesOrder> orders = session.createCriteria(SalesOrder.class).add(Restrictions.eq("customer.id", id)).list();
		model.addAttribute("customer", c);
		model.addAttribute("orders", orders);
		model.addAttribute("pageTitle","客户详情");
		model.addAttribute("pageContent", "customer/CustomerDetail");
		return "mainpage";
	}

	@RequestMapping("/input")
	public String input(Model model, @ModelAttribute Customer customer) {
		List<OrgType> orgTypes = ots.getAll();
		model.addAttribute("orgTypes", orgTypes);
		model.addAttribute("pageTitle","添加客户");
		model.addAttribute("pageContent", "customer/CustomerInput");
		return "mainpage";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute Customer customer, RedirectAttributes ra) {
		String err="";
		customer.removeEmptyAddresses();
		//fill org type name
		int orgId = customer.getOrgType().getId();
		if(customer.getOrgType().getName() == null && orgId > 0) {
			customer.getOrgType().setName(ots.getById(orgId).getName());
		}
		customer.genNameAndPhone();
		try {
			cs.save(customer);
		}
		catch(ConstraintViolationException e) {
			err="已有同名用户且同电话号码的用户存在";
		}
		ra.addFlashAttribute("err",err);
		return "redirect:/customer/customer/list";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute Customer customer, RedirectAttributes ra) {
		String err="";
		List<CustomerAddress> toBeDel = customer.removeEmptyAddresses();
		//fill org type name
		int orgId = customer.getOrgType().getId();
		if(customer.getOrgType().getName() == null && orgId > 0) {
			customer.getOrgType().setName(ots.getById(orgId).getName());
		}
		customer.genNameAndPhone();
		try {
			cs.update(customer);
		}
//		catch(ConstraintViolationException e) {
		catch(Exception e) {
			err="已有同名用户且同电话号码的用户存在";
		}
		ra.addFlashAttribute("err", err);

		// delete detached addresses
		// Note: you must update customer info first, then you can delete the unused addresses, 
		//	 otherwise, you will got constraint exception.
		
		for(CustomerAddress ca: toBeDel) {
			if(ca.getId() != null && ca.getId() > 0) {
				cas.delById(ca.getId());
			}
		}
		return "redirect:/customer/customer/list";
	}

	@RequestMapping("/del/{id}")
	public String del(Model model, @PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			cs.delById(id);
		}
		catch(DataIntegrityViolationException ce) {
			err="记录使用中, 无法删除";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/customer/customer/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit1(Model model, @ModelAttribute Customer customer, @PathVariable int id) {
		List<OrgType> orgTypes = ots.getAll();
		model.addAttribute("orgTypes", orgTypes);
		
		if(customer.getName() == null || customer.getName().isEmpty()) {
			customer = cs.getById(id);
		}
		
		// update customer and delete unused addresses.
		List<CustomerAddress> toBeDel = customer.removeEmptyAddresses();
		cs.update(customer);
		for(CustomerAddress ca: toBeDel) {
			if(ca.getId() != null && ca.getId() > 0) {
				cas.delById(ca.getId());
			}
		}
		
		customer.addAddress(new CustomerAddress());
		model.addAttribute("customer", customer);
		
		model.addAttribute("pageTitle","编辑客户");
		model.addAttribute("pageContent", "customer/CustomerEdit");

		return "mainpage";
	}
}
