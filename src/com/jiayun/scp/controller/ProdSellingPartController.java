package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.OrderItem;
import com.jiayun.scp.model.ProdC1;
import com.jiayun.scp.model.ProdPart;
import com.jiayun.scp.model.ProdPartItem;
import com.jiayun.scp.model.ProdSelling;
import com.jiayun.scp.model.SalesOrder;

@Controller
@RequestMapping("/product/part")
public class ProdSellingPartController {
	
	@Autowired
	private DaoService<ProdSelling> pss;

	@Autowired
	private DaoService<ProdPart> pps;
	
	@Autowired
	private DaoService<ProdC1> pc1s;
	
	@Autowired
	private DaoService<ProdPartItem> ppis;
	
	@Autowired
	private DaoService<SalesOrder> sos;
	
	@Autowired
	private SessionFactory sf;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<ProdSelling> l = new ArrayList<>();
		for(ProdSelling ps : pss.getAll()) {
			if(ps.getPartslist().size()<2) {
				// 零件数小于2，是单件，不是套装
				l.add(ps);
			}
		}
		model.addAttribute("partslist", l);
		model.addAttribute("pageTitle","产品部件列表");
		model.addAttribute("pageContent", "product/PartList");
		return "mainpage";
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable int id) {
		ProdSelling ps = pss.getById(id);

		List<SalesOrder> orders = genRelatedOrderList(id);
		
		model.addAttribute("ps", ps);
		model.addAttribute("orders", orders);
		model.addAttribute("pageTitle","部件信息");
		model.addAttribute("pageContent", "product/PartDetail");
		return "mainpage";
	}

	@RequestMapping("/input")
	public String input(Model model) {
		List<ProdC1> c1list = new ArrayList<>();
		for(ProdC1 c1: pc1s.getAll()) {
			//系统内置 C1 ID 为 1 的是套装
			if(c1.getId() != 1) {
				c1list.add(c1);
			}
		}
		model.addAttribute("ps", new ProdSelling());
		model.addAttribute("c1list", c1list);
		model.addAttribute("pageTitle","添加新部件");
		model.addAttribute("pageContent", "product/PartInput");
		return "mainpage";
	}

	@RequestMapping("/save")
	public String save(Model model, @ModelAttribute ProdSelling ps, RedirectAttributes ra) {
		if(pps.getByName(ps.getName())==null) {
			// Part is not existing
			ProdPart pp = new ProdPart();
			pps.save(pp.setByProdSelling(ps));

			pss.save(ps);
			ProdPartItem ppi = new ProdPartItem();
			ppi.setPart(pp);
			ppi.setQuantity(1);
			ppi.setSelling(ps);
//			ppis.save(ppi);
			
			ps.getPartslist().add(ppi);
			pss.update(ps);
		}
		else {
			ra.addFlashAttribute("err", "已有同名部件存在");
		}

		return "redirect:/product/part/list";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute ProdSelling ps, Model model, RedirectAttributes ra) {
		//TODO: 这段逻辑写得太烂了！应该重写。
		
		ProdSelling old = pss.getById(ps.getId());
		ProdPart pp;
		ProdPartItem ppi;
		
		if(pps.getByName(ps.getName())==null) {
			// 新名字没有被占用
			String oldName = old.getName();
			old.setName(ps.getName());
			old.setDescription(ps.getDescription());
			old.setC2(ps.getC2());
			old.setListprice(ps.getListprice());
			
			pp = pps.getByName(oldName);
			if(pp==null) {
				//这是一种异常情况, 没有叶子结点
				pp = new ProdPart().setByProdSelling(ps);
				ppi = new ProdPartItem();
				ppi.setPart(pp);
				ppi.setSelling(ps);
				ppi.setQuantity(1);
				List<ProdPartItem> l = new ArrayList<ProdPartItem>();
				l.add(ppi);
				old.setPartslist(l);
				
				pps.save(pp);
				pss.update(old);
			}
			else {
				pp.setByProdSelling(ps);
				// 有点累了，还应该校验一个 partslist 里面的内容是不是正确，不过算了，再说吧。
				pps.update(pp);
				pss.update(old);
			}
		}
		else if(pss.getByName(ps.getName()).getId() == ps.getId()){
			// 同一部件，名称未变
			pp = pps.getByName(ps.getName());
			pp.setByProdSelling(ps);
			pps.update(pp);
			pss.update(ps);
		}
		else {
			String err="已有同名部件存在";
			ra.addFlashAttribute("err",err);
			return "redirect:/product/part/edit/"+ps.getId();
		}
		
		return "redirect:/product/part/list";
	}

	@RequestMapping("/del/{id}")
	public String del(Model model, @PathVariable int id, RedirectAttributes ra) {
		String err="";
		ProdSelling ps;
		ProdPartItem ppi;
		ProdPart pp;
		ps = pss.getById(id);
		if(ps.getPartslist().size()>0) {
			ppi = ps.getPartslist().remove(0);
			pss.update(ps);
			pp = ppi.getPart();
			try {
				pss.del(ps);
			}
			catch(DataIntegrityViolationException e) {
				ps.getPartslist().add(ppi);
				err="有其它订单引用了这个部件，无法删除";
				ra.addFlashAttribute("err", err);
				return "redirect:/product/part/list";
			}
			try {
				pps.del(pp);
			}
			catch(DataIntegrityViolationException e) {
				ps.getPartslist().add(ppi);
				pss.save(ps);
				err="有其它套装引用了这个部件，无法删除";
				ra.addFlashAttribute("err", err);
				return "redirect:/product/part/list";
			}
		} //if
		else { // ps.getPartslist().size ==0
			// 上层封装的 PS 没有向下指的列表
			pp = pps.getByName(ps.getName());
			if(pp == null) {
				// 具体的 ProdPart 叶子结点也不见了
				try {
					pss.del(ps);
				}
				catch(DataIntegrityViolationException e){
					//不能删除 PS, 上面可能有 Order 正在用它
					pp = new ProdPart().setByProdSelling(ps);
					pps.save(pp);
					ppi = new ProdPartItem();
					ppi.setQuantity(1);
					ppi.setPart(pp);
					ppi.setSelling(ps);
					pss.save(ps);
					err="其它订单引用了这个部件，无法删除";
					ra.addFlashAttribute("err", err);
					return "redirect:/product/part/list";
				}
			}
			else {
				// PPI 列表没有，但是还是有孤立的 ProdPart叶子结点
				try {
					pss.del(ps);
				}
				catch(DataIntegrityViolationException e) {
					// 被其它订单引用
					ppi = new ProdPartItem();
					ppi.setQuantity(1);
					ppi.setPart(pp);
					ppi.setSelling(ps);
					ps.getPartslist().add(ppi);
					pss.save(ps);
					err="其它订单引用了这个部件，无法删除";
					ra.addFlashAttribute("err", err);
					return "redirect:/product/part/list";
				}
				try {
					pps.del(pp);
				}
				catch(DataIntegrityViolationException e) {
					ppi = new ProdPartItem();
					ppi.setQuantity(1);
					ppi.setPart(pp);
					ppi.setSelling(ps);
					ps.getPartslist().add(ppi);
					pss.update(ps);
					err="其它套装引用了这个部件，无法删除";
					ra.addFlashAttribute("err", err);
					return "redirect:/product/part/list";
				}
			}
		}
		ra.addFlashAttribute("err", err);
		return "redirect:/product/part/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		ProdSelling ps = pss.getById(id);
		List<ProdC1> c1l = pc1s.getAll();
		model.addAttribute("part", ps);
		model.addAttribute("c1list", c1l);
		model.addAttribute("pageTitle","编辑产品部件");
		model.addAttribute("pageContent", "product/PartEdit");
		return "mainpage";
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

}
