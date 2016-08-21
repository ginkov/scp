package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.ExpT1;
import com.jiayun.scp.model.ExpT2;

@Controller
@RequestMapping("/finance/exptype")
public class ExpTypeController {
	
	private static final String REDIR_TO_LIST= "redirect:/finance/exptype/list";

	@Autowired
	private DaoService<ExpT1> et1s;
	
	@Autowired
	private DaoService<ExpT2> et2s;

	@RequestMapping("/list")
	public String list(Model model) {
		List<ExpT1> t1s = et1s.getAll();
		List<String> t1NameList = new ArrayList<>();
		for(ExpT1 t1: t1s) {
			t1NameList.add(t1.getName());
		}
		Gson gson = new Gson();
		model.addAttribute("t1s", t1s);
		model.addAttribute("t1NameList",gson.toJson(t1NameList));
		model.addAttribute("pageTitle","支出类别与种类");
		model.addAttribute("pageContent", "finance/ExpTypeList");
		return "mainpage";
	}
	
	@RequestMapping("/input")
	public String input(@RequestParam(value="t1Names[]") String[] t1Names, 
			@RequestParam String t2Name, Model model, RedirectAttributes ra) {
		String err;
		if (t1Names.length > 0) {
			String t1Name = t1Names[0];
			ExpT1 t1 = et1s.getByName(t1Name);
			if(t1 == null) {
				t1 = new ExpT1();
				t1.setName(t1Name);
				t1.setDescription("");
				try {
					et1s.save(t1);
				}
				catch(ConstraintViolationException e) {
					err="支出类别已存在";
					ra.addFlashAttribute("err",err);
					return REDIR_TO_LIST;
				}
			}
			// 创建一个新类型时，可以不填子类型的名字，缺省会加一个相同名称的子类型。
			if(t2Name.isEmpty()) {
				t2Name = t1Name;
			}
			ExpT2 t2 = et2s.getByName(t2Name);
			if(t2 == null) {
				t2 = new ExpT2();
				t2.setT1(t1);
				t2.setName(t2Name);
				t2.setDescription("");
				t1.getT2list().add(t2);
				try {
					et1s.update(t1);
				}
				catch(ConstraintViolationException e) {
					err="支出类别已存在";
					ra.addFlashAttribute("err",err);
					return REDIR_TO_LIST;
				}
			}
			else {
				// subgrp already exist!
				err="支出种类已存在";
				ra.addFlashAttribute("err", err);
			}
		}
		else {
			err="类型不能为空";
		}
		return REDIR_TO_LIST;
	}
	
	@RequestMapping("/edit")
	public String edit(@RequestParam String tid, Model model) {
		int	id = Integer.parseInt(tid.split("\\-")[1]);
		model.addAttribute("pageTitle","编辑支出类别");

		if(tid.startsWith("t1")) {
			ExpT1 t1 = et1s.getById(id);
			if(t1!=null) {
				model.addAttribute("t1", t1);
				model.addAttribute("pageContent", "finance/ExpT1Edit");
				return "mainpage";
			}
			else {
				return REDIR_TO_LIST;
			}
		}
		else {
			ExpT2 t2 = et2s.getById(id);
			if(t2!=null) {
				model.addAttribute("t2", t2);
				model.addAttribute("pageContent", "finance/ExpT2Edit");
				return "mainpage";
			}
			else {
				return REDIR_TO_LIST;
			}
		}
	}
	
	@RequestMapping("/update")
	public String groupupdate(@ModelAttribute ExpT1 t1, RedirectAttributes ra) {
		String err="";
		ExpT1 origT1 = et1s.getById(t1.getId());
		if(origT1 != null) {
			origT1.setName(t1.getName());
			origT1.setDescription(t1.getDescription());
			try {
				et1s.update(origT1);
			}
			catch(ConstraintViolationException e) {
				err="同名功能类型已存在";
				ra.addFlashAttribute("err",err);
			}
		}
		return REDIR_TO_LIST;
	}
	
	@RequestMapping("/l2update")
	public String subgrpupdate(@ModelAttribute ExpT2 t2, RedirectAttributes ra) {
		String err="";
		ExpT2 orig = et2s.getById(t2.getId());
		if(orig != null) {
			orig.setName(t2.getName());
			orig.setDescription(t2.getDescription());
		}
		try {
			et2s.update(orig);
		}
		catch(ConstraintViolationException e) {
			err="子类已存在";
		}
		ra.addFlashAttribute("err",err);
		return REDIR_TO_LIST;
	}

	@RequestMapping("/del/{id}")
	public String groupdel(@PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			et1s.delById(id);
		}catch(DataIntegrityViolationException e) {
			err="记录使用中, 无法删除";
		}
		ra.addFlashAttribute("err", err);
		return REDIR_TO_LIST;
	}
	
	@RequestMapping("/l2del/{id}")
	public String subgrpdel(@PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			et2s.delById(id);
		} catch(DataIntegrityViolationException e) {
			err="记录使用中，无法删除";
		}
		ra.addFlashAttribute("err", err);
		return REDIR_TO_LIST;
	}
}