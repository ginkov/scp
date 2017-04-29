package com.jiayun.scp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.SalesOrder;
import com.jiayun.scp.model.ServiceRecord;


@Controller
@RequestMapping("/sale/servicerecord")
public class ServiceRecordController {
	
	@Autowired
	private DaoService<SalesOrder> sos;
	
	@Autowired
	private DaoService<ServiceRecord> srs;

	@RequestMapping("/edit/{orderId}")
	public String edit(Model model, @PathVariable int orderId, HttpServletRequest hsr)
	{
		SalesOrder order = sos.getById(orderId);
		order.getServiceRecords().add(new ServiceRecord());
		model.addAttribute("order", order);
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "M";
		}

		model.addAttribute("pageTitle","编辑服务记录");
		model.addAttribute("pageContent", "sale/ServiceRecordEdit"+mobileSuffix);

		return "mainpage"+mobileSuffix;
	}

	@RequestMapping("/update")
	public String update(Model model, @ModelAttribute SalesOrder order, HttpServletRequest hsr) {
		List<ServiceRecord> toBeDel = order.removeEmptyServiceRecords();
		int oid = order.getId();
		SalesOrder updateOrder = sos.getById(oid);
		updateOrder.setServiceRecords(order.getServiceRecords());

		sos.update(updateOrder);

		//delete unused service records after update.
		for(ServiceRecord sr: toBeDel) {
			if(sr.getId()!=null && sr.getId()>0) {
				srs.delById(sr.getId());
			}
		}
		
		String mobileSuffix = "";
		if(hsr.getParameterMap().containsKey("mobile")) {
			mobileSuffix = "?mobile";
		}
		return "redirect:/sale/order/detail/"+oid+mobileSuffix;
	}
}
