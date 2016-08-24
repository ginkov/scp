package com.jiayun.scp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiayun.scp.util.FrRpt;

@Controller
@RequestMapping("/finance/balance")
public class FrWeeksController {
	
	@Autowired
	private FrRpt rpt;
	
	@RequestMapping("/list")
	public String list(Model model) {

		rpt.go();
		
		model.addAttribute("balance", rpt.getBalance());
		model.addAttribute("totalIn", rpt.getTotalIn());
		model.addAttribute("totalOut", rpt.getTotalOut());
		model.addAttribute("frWeeks", rpt.getFrWeeksCompressed());

		model.addAttribute("pageTitle","账户历史");
		model.addAttribute("pageContent", "finance/BalanceList");
		return "mainpage";
	
	}

}
