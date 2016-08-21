package com.jiayun.scp.controller;

import java.util.List;

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
	public String edit(Model model, @PathVariable int orderId)
	{
		SalesOrder order = sos.getById(orderId);
		order.getServiceRecords().add(new ServiceRecord());
		model.addAttribute("order", order);

		model.addAttribute("pageTitle","编辑服务记录");
		model.addAttribute("pageContent", "sale/ServiceRecordEdit");

		return "mainpage";
	}

	@RequestMapping("/update")
	public String update(Model model, @ModelAttribute SalesOrder order) {
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
		return "redirect:/sale/order/detail/"+oid;
	}
}
