package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.OrderItem;
import com.jiayun.scp.model.ProdC1;
import com.jiayun.scp.model.ProdC2;
import com.jiayun.scp.model.ProdComboTemplate;
import com.jiayun.scp.model.ProdPart;
import com.jiayun.scp.model.ProdPartItem;
import com.jiayun.scp.model.ProdSelling;
import com.jiayun.scp.model.SalesOrder;

@Controller
@RequestMapping("/product/combo")
public class ProdSellingComboController {
	
	@Autowired
	private DaoService<ProdSelling> pss;

	@Autowired
	private DaoService<ProdPart> pps;
	
	@Autowired
	private DaoService<ProdPartItem> ppis;
	
	@Autowired
	private DaoService<ProdC1> pc1s;
	
	@Autowired
	private DaoService<ProdComboTemplate> pcts;
	
	@Autowired
	private DaoService<SalesOrder> sos;
	
	@Autowired
	private SessionFactory sf;
	
	public ProdSellingComboController(){
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<ProdSelling> l = new ArrayList<>();
		for(ProdSelling ps : pss.getAll()) {
			if(ps.getPartslist().size()>1) {
				// 零件数大于1，不是单件，是套装
				l.add(ps);
			}
		}
		model.addAttribute("combolist", l);
		model.addAttribute("pageTitle","产品套装列表");
		model.addAttribute("pageContent", "product/ComboList");
		return "mainpage";
	}
	
	@RequestMapping("/detail/{id}")
	@Transactional
	public String detail(Model model, @PathVariable int id) {
		ProdSelling combo= pss.getById(id);
		List<SalesOrder> orders = genRelatedOrderList(id);
		
		model.addAttribute("orders", orders);
		model.addAttribute("combo", combo);
		model.addAttribute("pageTitle","套装信息");
		model.addAttribute("pageContent", "product/ComboDetail");
		return "mainpage";
	}

	@RequestMapping("/input")
	public String input(Model model, @ModelAttribute ProdSelling combo) {
		// 这里在后台数据库中，指定C1类型“套装”的 ID 为1
		Set<ProdC2> c2list = pc1s.getById(1).getC2list();
		model.addAttribute("c2list", c2list);
		
		List<ProdPart> lp = pps.getAll();
		Map<String,ArrayList<ProdPart>> partOpts = genProdOpts(lp);
		model.addAttribute("partOpts", partOpts);
		
		model.addAttribute("partSummary",getPartSummary());
		
		if(combo.getPartslist().size()<1 && (combo.getName() ==null || combo.getName().isEmpty())){
			// 刚刚新生成的 Combo, 还没有进行任务修改
			ProdComboTemplate pct = pcts.getById(0);
			if(pct != null) {
				for(ProdPartItem ppi: pct.getPs().getPartslist()) {
					ProdPartItem ppiNew = new ProdPartItem();
					ppiNew.setPart(ppi.getPart());
					ppiNew.setQuantity(ppi.getQuantity());
					combo.getPartslist().add(ppiNew);
					combo.genListprice();
				}
			}
		}

		List<ProdPartItem> toBeDel = combo.removeEmptyItems();
		for(ProdPartItem ppi: toBeDel) {
			ppis.del(ppi);
		}
		combo.getPartslist().add(new ProdPartItem());
		
		model.addAttribute("combo", combo);
		model.addAttribute("pageTitle","添加新套装");
		model.addAttribute("pageContent", "product/ComboInput");
		return "mainpage";
	}

	@RequestMapping("/save")
	public String save(@ModelAttribute ProdSelling combo, RedirectAttributes ra) {
		List<ProdPartItem> toBeDel = combo.removeEmptyItems();
		for(ProdPartItem ppi : toBeDel) {
			ppis.del(ppi);
		}
		if(combo.getPartslist().size()<2) {
			ra.addFlashAttribute("err", "错误: 至少要两种类型以上才能作套装");
			return "redirect:/product/combo/input";
		}
		else {
			if(getAllComboNameExceptMe(0).contains(combo.getName())) {
				// 已经有同名 Combo
				ra.addFlashAttribute("err", "错误: 已有同名套装");
//				ra.addFlashAttribute("combo", combo);
				return "redirect:/product/combo/input";
			}
			else {
				for(ProdPartItem ppi : combo.getPartslist()) {
					ppi.setSelling(combo);
				}
				pss.save(combo);
			}
		}
		return "redirect:/product/combo/list";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute ProdSelling combo, RedirectAttributes ra) {
		List<ProdPartItem> toBeDel = combo.removeEmptyItems();
		for(ProdPartItem ppi : toBeDel) {
			ppis.del(ppi);
		}
		if(combo.getPartslist().size()<2) {
			ra.addFlashAttribute("err", "错误: 至少要两种类型以上才能作套装");
			return "redirect:/product/combo/input";
		}
		else {
			if(getAllComboNameExceptMe(combo.getId()).contains(combo.getName())) {
				// 已经有同名 Combo
				ra.addFlashAttribute("err", "错误: 已有同名套装");
				return "redirect:/product/combo/list";
			}
			else {
				for(ProdPartItem ppi : combo.getPartslist()) {
					ppi.setSelling(combo);
				}
				pss.save(combo);
			}
		}
		return "redirect:/product/combo/list";
	}

	@RequestMapping("/del/{id}")
	public String del(Model model, @PathVariable int id, RedirectAttributes ra) {
		String err="";
		try {
			pss.delById(id);
		}catch(DataIntegrityViolationException e) {
			err="记录使用中, 无法删除";
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/product/combo/list";
	}
	
	@RequestMapping("/edit/{cid}")
	public String edit(Model model, @ModelAttribute ProdSelling combo, @PathVariable int cid) {
		// 系统内置 "套装" 的ID 为1
		Set<ProdC2> c2list = pc1s.getById(1).getC2list();
		model.addAttribute("c2list", c2list);
		
		List<ProdPart> lp = pps.getAll();
		Map<String,ArrayList<ProdPart>> partOpts = genProdOpts(lp);
		model.addAttribute("partOpts", partOpts);
		
		model.addAttribute("partSummary",getPartSummary());

		if(combo.getName() == null || combo.getName().isEmpty()) {
			combo = pss.getById(cid);
		}
		List<ProdPartItem> toBeDel = combo.removeEmptyItems();
		for(ProdPartItem ppi: toBeDel) {
			ppis.del(ppi);
		}
		combo.getPartslist().add(new ProdPartItem());
		model.addAttribute("combo", combo);
		
		model.addAttribute("pageTitle","修改产品套装");
		model.addAttribute("pageContent", "product/ComboEdit");
		return "mainpage";
	}
	private Map<String,ArrayList<ProdPart>> genProdOpts(List<ProdPart> lp){
		Map<String, ArrayList<ProdPart>> prodOpts = new HashMap<>();
		for(ProdPart p : lp) {
			String c1Name = p.getC2().getC1().getName();
			if(! prodOpts.containsKey(c1Name)) {
				prodOpts.put(c1Name, new ArrayList<ProdPart>());
			}
			prodOpts.get(c1Name).add(p);
		}
		return prodOpts;
	}

	private String getPartSummary() {
		Map<Integer, PartSummary> psm = new HashMap<>();
		for(ProdPart pp: pps.getAll()) {
			PartSummary ps = new PartSummary(pp.getC2().getC1().getName(), pp.getC2().getName(), pp.getListprice());
			psm.put(pp.getId(),ps);
		}
		Gson g = new Gson();
		return g.toJson(psm);
	}
	
	class PartSummary{
		String c1;
		String c2;
		double price;
		
		PartSummary(String c1, String c2, double price){
			this.c1=c1;
			this.c2=c2;
			this.price = price;
		}
	}
	
	private List<SalesOrder> genRelatedOrderList(int id){
		List<SalesOrder> l = new ArrayList<>();
		List<SalesOrder> all = sos.getAll();
		for(SalesOrder order: all) {
			for(OrderItem item: order.getItems()) {
				if(item.getProdSelling().getId()==id) {
					l.add(order);
					break;
				}
			}
		}
		return l;
	}
	
	private Set<String> getAllComboNameExceptMe(int me){
		Set<String> s = new HashSet<>();
		List<ProdSelling> l = pss.getAll();
		for(ProdSelling ps : l) {
			if(ps.getPartslist().size()>1 && ps.getId() != me) {
				s.add(ps.getName());
			}
		}
		return s;
	}
}
