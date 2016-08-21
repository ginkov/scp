package com.jiayun.scp.controller;

import java.util.List;

import org.hibernate.Criteria;
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
import com.jiayun.scp.model.OrgType;
import com.jiayun.scp.model.SalesOrder;


@Controller
@RequestMapping("/customer/orgtype")
public class OrgTypeController {
	
	@Autowired
	private DaoService<OrgType> ots;
	
	@Autowired
	private SessionFactory sf;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<OrgType> l = ots.getAll();
		model.addAttribute("orgTypeList", l);
		model.addAttribute("pageTitle","用户组织类型");
		model.addAttribute("pageContent", "customer/OrgTypeList");
		return "mainpage";
	}
	
	@RequestMapping("/detail/{id}")
	@Transactional
	public String detail(Model model, @PathVariable int id) {
		OrgType ot = ots.getById(id);
		Session session = sf.getCurrentSession();
		Criteria cri = session.createCriteria(SalesOrder.class).createAlias("customer.orgType", "customerOrgType");

		@SuppressWarnings("unchecked")
		List<SalesOrder> selected = cri.add(Restrictions.eq("customerOrgType.id", id)).list();
		
		model.addAttribute("ot", ot);
		model.addAttribute("orders", selected);
		model.addAttribute("pageTitle","用户组织类型");
		model.addAttribute("pageContent", "customer/OrgTypeDetail");
		return "mainpage";
	}

	@RequestMapping("/input")
	public String input(Model model) {
		model.addAttribute("orgType", new OrgType());
		model.addAttribute("pageTitle","添加用户组织类型");
		model.addAttribute("pageContent", "customer/OrgTypeInput");
		return "mainpage";
	}

	@RequestMapping("/save")
	public String save(@ModelAttribute OrgType orgType, RedirectAttributes ra) {
		String err="";
		try {
			ots.save(orgType);
		}
		catch(ConstraintViolationException e) {
			err="已有同名类型存在";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/customer/orgtype/list";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute OrgType orgType, RedirectAttributes ra) {
		String err="";
		try {
			ots.update(orgType);
		}
		catch(ConstraintViolationException e) {
			err="已有同名类型存在";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/customer/orgtype/list";
	}

	@RequestMapping("/del/{id}")
	public String del(Model model, @PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			ots.delById(id);
		}catch(DataIntegrityViolationException e) {
			err="记录使用中, 无法删除";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/customer/orgtype/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		OrgType pm = ots.getById(id);
		model.addAttribute("orgType", pm);
		model.addAttribute("pageTitle","编辑用户类型");
		model.addAttribute("pageContent", "customer/OrgTypeEdit");
		return "mainpage";
	}
}
