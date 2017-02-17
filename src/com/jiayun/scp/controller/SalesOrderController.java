package com.jiayun.scp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.Customer;
import com.jiayun.scp.model.OrderItem;
import com.jiayun.scp.model.PayStatus;
import com.jiayun.scp.model.ProdC1;
import com.jiayun.scp.model.ProdSelling;
import com.jiayun.scp.model.SalesOrder;
import com.jiayun.scp.model.UserSaleType;
import com.jiayun.scp.util.SalesOrderUtil;

@Controller
@RequestMapping(value = {"/sale/order", "/"})
public class SalesOrderController {
	
	private static final String REDIR_ORDER_LIST="redirect:/sale/order/list";
	
	@Autowired
	private DaoService<SalesOrder> sos;
	
	@Autowired
	private DaoService<Customer> cs;
	
	@Autowired
	private DaoService<OrderItem> ois;
	
	@Autowired
	private DaoService<UserSaleType> usts;
	
	@Autowired
	private DaoService<ProdC1> pc1s;
	
	@Autowired
	private DaoService<ProdSelling> pss;
	
	@Autowired
	private SessionFactory sf;
	
	@Autowired
	private SalesOrderUtil soUtil;

	@RequestMapping(value = {"/list", "index"})
	public String list(Model model) {
		List<SalesOrder> l = sos.getAll();
		model.addAttribute("orders", l);
		model.addAttribute("pageTitle","订单列表");
		model.addAttribute("pageContent", "sale/SalesOrderList");
		return "mainpage";	
	}
	
	@RequestMapping("/input")
	public String input(Model model, @ModelAttribute SalesOrder order) {
/*
 *  	We need customer, userSaleType, and ProdSelling information during creating a new order.
 */
		List<UserSaleType> userSaleTypes = usts.getAll();

		List<ProdSelling> prodSellings = pss.getAll();
		Map<String, ArrayList<ProdSelling>> prodOpts = new HashMap<>();
		prodOpts = genProdOpts(prodSellings);
		// 生成产品价目表, 以便前端浏览器用 js 自动计算价格
		Map<Integer, Double> priceList; 
		priceList = genPriceList(prodSellings);
		Gson gson = new Gson();
		
		List<Customer> customers = cs.getAll();
		if(order.getCustomer() != null) {
			if(order.getCustomer().getNameAndPhone()!=null) {
				if(!order.getCustomer().getNameAndPhone().isEmpty()) {
					customers.add(order.getCustomer());
				}
			}
		}
		model.addAttribute("customers", customers);
		model.addAttribute("userSaleTypes", userSaleTypes);
		model.addAttribute("prodOpts", prodOpts);
		model.addAttribute("pricelist", gson.toJson(priceList));

		// 添加订单序号
		if(order.getSn() == null || order.getSn().isEmpty()) {
			order.setSn(soUtil.genSN());
		}
		
		order.removeEmptyItems();
		order.updatePriceAndDiscount();
		model.addAttribute("order", order);
		model.addAttribute("pageTitle","新订单");
		model.addAttribute("pageContent", "sale/SalesOrderInput01");
		return "mainpage";
	}
	
	
	@RequestMapping("/save")
	@Transactional
	public String save(Model model, @ModelAttribute SalesOrder order, BindingResult br) {
		if(br.hasErrors()) {
//			List<FieldError> fel = br.getFieldErrors();
//			for(FieldError fe: fel) {
//				System.out.println("Error code = "+fe.getCode()+", object = "+fe.getObjectName()+", field = "+fe.getField());
//			}
			model.addAttribute("pageTitle","新订单");
			model.addAttribute("pageContent", "sale/SalesOrderInput01");
			return "mainpage";
		}
		Session session = sf.getCurrentSession();
		Customer c = getCustomerByInfo(session, order.getCustomer().getNameAndPhone());
		order.setCustomer(c);
		updateUserSaleType(order);
		order.removeEmptyItems();
		order.updatePriceAndDiscount();
		updateItemProdSelling(order);
		order.updateProductSummary();
		sos.save(order);
		return REDIR_ORDER_LIST;
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable int id) {
		SalesOrder order = sos.getById(id);

		model.addAttribute("order", order);
		model.addAttribute("pageTitle","订单详情");
		model.addAttribute("pageContent", "sale/SalesOrderDetail");
		return "mainpage";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @ModelAttribute SalesOrder order, @PathVariable int id) {
		if(order.getItems().isEmpty()) {
			order = sos.getById(id);
		}
		List<Customer> customers = cs.getAll();
		model.addAttribute("customers", customers);

		List<ProdSelling> prodSellings = pss.getAll();

		List<UserSaleType> userSaleTypes = usts.getAll();
		model.addAttribute("userSaleTypes", userSaleTypes);

		Map<String,ArrayList<ProdSelling>> prodOpts = genProdOpts(prodSellings);
		model.addAttribute("prodOpts", prodOpts);

		Map<Integer, Double> priceList; 
		priceList = genPriceList(prodSellings);
		Gson gson = new Gson();
		model.addAttribute("pricelist", gson.toJson(priceList));

		//update order and delete unused items.
		List<OrderItem> toBeDel = order.removeEmptyItems();
		if(order.getCustomer().getName()==null || order.getCustomer().getName().isEmpty()) {
			Customer c = cs.getById(order.getCustomer().getId());
			order.setCustomer(c);
		}
		sos.update(order);
		for(OrderItem oi: toBeDel) {
			if(oi.getId()!=null && oi.getId()>0) {
				ois.delById(oi.getId());
			}
		}

		order.getItems().add(new OrderItem());
		order.updatePriceAndDiscount();
		model.addAttribute("order", order);
		
		model.addAttribute("pageTitle","编辑订单");
		model.addAttribute("pageContent", "sale/SalesOrderEdit");

		return "mainpage";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute SalesOrder order) {
		if(order.getCustomer().getName()==null || order.getCustomer().getName().isEmpty()) {
			Customer c = cs.getById(order.getCustomer().getId());
			order.setCustomer(c);	
		}
		List<OrderItem> toBeDel = order.removeEmptyItems();
		updateUserSaleType(order);
		updateItemProdSelling(order);
		order.updatePriceAndDiscount();
		order.updateProductSummary();
		sos.update(order);

		for(OrderItem oi: toBeDel) {
			if(oi.getId()!=null && oi.getId()>0) {
				ois.delById(oi.getId());
			}
		}

		return "redirect:/sale/order/detail/"+order.getId();
	}
	
	@RequestMapping("/del/{id}")
	public String del(@PathVariable int id) {
		sos.delById(id);
		return REDIR_ORDER_LIST;
	}
	
	@RequestMapping("/fullpay/{id}")
	public String fullpay(@PathVariable int id) {
		//TODO: only FINANCE people can do this.
		SalesOrder so = sos.getById(id);
		so.setPayStatus(PayStatus.PAID);
		so.setPayDate(new Date());
		sos.save(so);
		return REDIR_ORDER_LIST;
	}

	private void updateUserSaleType(SalesOrder order) {
		int utid = order.getUserSaleType().getId();
		if(utid > 0) {
			order.getUserSaleType().setName(usts.getById(utid).getName());
		}
	}
	
	private void updateItemProdSelling(SalesOrder order) {
		for(OrderItem i:order.getItems()) {
			int pmid = i.getProdSelling().getId();
			// 未没有名字的 ProductModel 填充名字
			if(i.getProdSelling().getName()==null && pmid >0) {
				String name = pss.getById(pmid).getName();
				i.getProdSelling().setName(name);
			}
		}
	}
	
	private Map<Integer, Double> genPriceList(List<ProdSelling> p){
		Map<Integer, Double> priceList = new HashMap<>();
		for(ProdSelling ps: p) {
			priceList.put(ps.getId(), ps.getListprice());
		}
		return priceList;
	}
	
	private Map<String,ArrayList<ProdSelling>> genProdOpts(List<ProdSelling> prodSellings){
		Map<String, ArrayList<ProdSelling>> prodOpts = new HashMap<>();
		for(ProdC1 c1: pc1s.getAll()) {
			prodOpts.put(c1.getName(), new ArrayList<ProdSelling>());
		}
		
		for(ProdSelling ps: prodSellings) {
			prodOpts.get(ps.getC2().getC1().getName()).add(ps);
		}
		return prodOpts;
	}
	
	/**
	 * 根据输入的 Customer 姓名、电话信息，查找已有 Customer, 或者新建一个 Customer
	 * @param info: String, customer name only, or customerName_customerPhone1
	 * @return  Existing or new Customer object.
	 */
	private Customer getCustomerByInfo(Session s, String info) {
		Customer c = null;
		String name, phone1;
		String[] tokens = info.split("_");
		if(tokens.length == 2) {
			name = tokens[0];
			phone1 = tokens[1];
		}
		else {
			name = info;
			phone1 = "";
		}
		c = (Customer)s.createCriteria(Customer.class)
					.add(Restrictions.eq("name", name))
					.add(Restrictions.eq("phone1", phone1))
					.uniqueResult();
		if (c==null) {
			c = new Customer();
			c.setName(name);
			c.setPhone1(phone1);
			c.setNameAndPhone(info);
		}
		return c;
	}
	

}
