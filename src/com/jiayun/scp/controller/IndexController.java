package com.jiayun.scp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controller for mapping context root.
 * * @author xinyin
 * 
 * DO NOT DELETE!
 * DO NOT DELETE!
 * DO NOT DELETE!
 * 
 * even you may think is controller is 'simple', actually it's very important -- it is for root context
 */
@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index(Model model) {
		return "login";
	}

}
