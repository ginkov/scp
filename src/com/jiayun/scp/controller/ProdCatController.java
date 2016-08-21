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
import com.jiayun.scp.model.ProdC1;
import com.jiayun.scp.model.ProdC2;

@Controller
@RequestMapping("/product/category")

public class ProdCatController {
	
	@Autowired 
	private DaoService<ProdC1> pc1s;
	@Autowired 
	private DaoService<ProdC2> pc2s;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<ProdC1> grps = pc1s.getAll();
		List<String> grouplist = new ArrayList<>();
		for(ProdC1 pfg: grps) {
			grouplist.add(pfg.getName());
		}
		Gson gson = new Gson();
		model.addAttribute("grps", grps);
		model.addAttribute("grouplist",gson.toJson(grouplist));
		model.addAttribute("pageTitle","产品功能类型与子类");
		model.addAttribute("pageContent", "product/CList");
		return "mainpage";
	}
	
	@RequestMapping("/input")
	public String input(@RequestParam(value="c1Names[]") String[] c1Names, 
			@RequestParam String c2Name, Model model, RedirectAttributes ra) {
		String err;
		if (c1Names.length > 0) {
			String c1Name = c1Names[0];
			ProdC1 c1 = pc1s.getByName(c1Name);
			if(c1 == null) {
				c1 = new ProdC1();
				c1.setName(c1Name);
				c1.setDescription("");
				try {
					pc1s.save(c1);
				}
				catch(ConstraintViolationException e) {
					err="同名功能类型已存在";
					ra.addFlashAttribute("err",err);
					return "redirect:/product/category/list";
				}
			}
			// 创建一个新类型时，可以不填子类型的名字，缺省会加一个相同名称的子类型。
			if(c2Name.isEmpty()) {
				c2Name = c1Name;
			}
			ProdC2 c2 = pc2s.getByName(c2Name);
			if(c2 == null) {
				c2 = new ProdC2();
				c2.setC1(c1);
				c2.setName(c2Name);
				c2.setDescription("");
				c1.getC2list().add(c2);
				try {
					pc1s.update(c1);
				}
				catch(ConstraintViolationException e) {
					err="同名功能类型已存在";
					ra.addFlashAttribute("err",err);
					return "redirect:/product/category/list";
				}
			}
			else {
				// subgrp already exist!
				err="子类已存在";
				ra.addFlashAttribute("err", err);
			}
		}
		else {
			err="类型不能为空";
		}
		return "redirect:/product/category/list";
	}
	
	@RequestMapping("/edit")
	public String edit(@RequestParam String gid, Model model) {
		int	id = Integer.parseInt(gid.split("\\-")[1]);
		model.addAttribute("pageTitle","编辑产品功能类型");

		if(gid.startsWith("g")) {
			ProdC1 g = pc1s.getById(id);
			if(g!=null) {
				model.addAttribute("group", g);
				model.addAttribute("pageContent", "product/C1Edit");
				return "mainpage";
			}
			else {
				return "redirect:/product/category/list";
			}
		}
		else {
			// subgroup
			ProdC2 s = pc2s.getById(id);
			if(s!=null) {
				model.addAttribute("subgrp", s);
				model.addAttribute("pageContent", "product/C2Edit");
				return "mainpage";
			}
			else {
				return "redirect:/product/category/list";
			}
		}
	}
	
	@RequestMapping("/update")
	public String groupupdate(@ModelAttribute ProdC1 modifiedGrp, RedirectAttributes ra) {
		String err="";
		ProdC1 origGrp = pc1s.getById(modifiedGrp.getId());
		if(origGrp != null) {
			origGrp.setName(modifiedGrp.getName());
			origGrp.setDescription(modifiedGrp.getDescription());
			try {
				pc1s.update(origGrp);
			}
			catch(ConstraintViolationException e) {
				err="同名功能类型已存在";
				ra.addFlashAttribute("err",err);
			}
		}
		return "redirect:/product/category/list";
	}
	
	@RequestMapping("/l2update")
	public String subgrpupdate(@ModelAttribute ProdC2 modified, RedirectAttributes ra) {
		String err="";
		ProdC2 orig = pc2s.getById(modified.getId());
		if(orig != null) {
			orig.setName(modified.getName());
			orig.setDescription(modified.getDescription());
		}
		try {
			pc2s.update(orig);
		}
		catch(ConstraintViolationException e) {
			err="子类已存在";
		}
		ra.addFlashAttribute("err",err);
		return "redirect:/product/category/list";
	}

	@RequestMapping("/del/{id}")
	public String groupdel(@PathVariable int id, RedirectAttributes ra) {
		String err="";
		if(id==1) {
			// ID=1 是一个特殊的 ProdC1，是不系统内置的，不能删除
			ProdC1 p= pc1s.getById(1);
			err = p.getName() +" 是内置条目，代表部件的套装，不能删除";
		}
		else {
			try {
				pc1s.delById(id);
			}catch(DataIntegrityViolationException e) {
				err="记录使用中, 无法删除";
			}
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/product/category/list";
	}
	
	@RequestMapping("/l2del/{id}")
	public String subgrpdel(@PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			pc2s.delById(id);
		} catch(DataIntegrityViolationException e) {
			err="记录使用中，无法删除";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/product/category/list";
	}

}
