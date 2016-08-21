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
import com.jiayun.scp.model.SalesOrder;
import com.jiayun.scp.model.UserSaleType;

@Controller
@RequestMapping("/sale/usertype")
public class UserSaleTypeController {
	
	@Autowired
	private DaoService<UserSaleType> usts;
	
	@Autowired
	private SessionFactory sf;

	@RequestMapping("/list")
	public String list(Model model) {
		List<UserSaleType> l = usts.getAll();
		model.addAttribute("userSaleTypeList", l);
		model.addAttribute("pageTitle","优惠类型列表");
		model.addAttribute("pageContent", "sale/UserSaleTypeList");
		return "mainpage";
	}
	
	@RequestMapping("/detail/{id}")
	@Transactional
	public String detail(Model model, @PathVariable int id) {
		UserSaleType ut = usts.getById(id);
		Session session = sf.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<SalesOrder> selected = session.createCriteria(SalesOrder.class).add(Restrictions.eq("userSaleType.id", id)).list();

		model.addAttribute("ut", ut);
		model.addAttribute("orders", selected);
		model.addAttribute("pageTitle","优惠类型详情");
		model.addAttribute("pageContent", "sale/UserSaleTypeDetail");
		return "mainpage";
	}
	
	@RequestMapping("/input")
	public String input(Model model) {
		model.addAttribute("userSaleType", new UserSaleType());
		model.addAttribute("pageTitle","添加优惠类型");
		model.addAttribute("pageContent", "sale/UserSaleTypeInput");
		return "mainpage";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute UserSaleType userSaleType, RedirectAttributes ra) {
		String err="";
		try {
			usts.save(userSaleType);
		}
		catch(ConstraintViolationException e) {
			err="优惠类型已存在";
		}
		ra.addFlashAttribute("err",err);
		return "redirect:/sale/usertype/list";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute UserSaleType userSaleType, RedirectAttributes ra) {
		String err="";
		try {
			usts.update(userSaleType);
		}
		catch(ConstraintViolationException e) {
			err="优惠类型已存在";
		}
		ra.addFlashAttribute("err",err);
		return "redirect:/sale/usertype/list";
	}

	@RequestMapping("/del/{id}")
	public String del(Model model, @PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			usts.delById(id);
		}
		catch(DataIntegrityViolationException ce) {
			err="记录使用中, 无法删除";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/sale/usertype/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		UserSaleType ut = usts.getById(id);
		model.addAttribute("userSaleType", ut);
		model.addAttribute("pageTitle","修改优惠类型");
		model.addAttribute("pageContent", "sale/UserSaleTypeEdit");
		return "mainpage";
	}
}
