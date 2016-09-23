package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.ProdComboTemplate;
import com.jiayun.scp.model.ProdSelling;

@Controller
@RequestMapping("/product/comboTemplate")
public class ProdComboTemplateController {
	
	@Autowired
	DaoService<ProdComboTemplate> pcts;
	
	@Autowired
	DaoService<ProdSelling> pss;
	
	@RequestMapping("/list")
	public String list(Model model) {
		ProdComboTemplate pct = pcts.getById(0);
		boolean hasTemplate = (pct != null);
		model.addAttribute("hasTemplate", hasTemplate);
		if(hasTemplate) {
			model.addAttribute("template", pct.getPs());
		}
		model.addAttribute("pageTitle","套装模板");
		model.addAttribute("pageContent", "product/ComboTemplateList");
		return "mainpage";	
	}
	
	@RequestMapping("/edit")
	public String edit(Model model) {
		Integer selected;
		List<ProdSelling> psl = new ArrayList<>();
		for(ProdSelling ps: pss.getAll()) {
			if(ps.getPartslist().size()>1) { psl.add(ps); }
		}
		model.addAttribute("combos", psl);

		ProdComboTemplate pct = pcts.getById(0);
		if(pct!=null) {
			selected = pct.getPs().getId();
		}
		else {
			selected = 0;
		}
		model.addAttribute("selected", selected);
		model.addAttribute("pageTitle","选择套装模板");
		model.addAttribute("pageContent", "product/ComboTemplateEdit");
		return "mainpage";	
	}

	@RequestMapping("/update")
	public String update(@RequestParam Integer selected) {
		ProdComboTemplate pct = pcts.getById(0);
		if(pct == null) {
			pct = new ProdComboTemplate();
			pct.setId(0);
		}
		if(selected == 0) {
			pcts.del(pct);
		}
		else {
			ProdSelling ps = pss.getById(selected);
			if(ps != null) {
				pct.setPs(ps);
				pcts.save(pct);
			}
		}
		return "redirect:/product/comboTemplate/list";
	}
}
