package com.jiayun.scp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.Role;
import com.jiayun.scp.model.Staff;
import com.jiayun.scp.model.StaffChangePasswordForm;
import com.jiayun.scp.model.StaffForm;

@Controller
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	private DaoService<Staff> ss;
	
	@Autowired
	private DaoService<Role> rs;
	
	@RequestMapping("/passwd/change")
	public String passwordChangeInput(Model model, Principal p) {
		StaffChangePasswordForm scpf = new StaffChangePasswordForm();
		String staffName = p.getName();
		Staff s = ss.getByName(staffName);
		scpf.setDescription(s.getDescription());
		model.addAttribute("staffChangePasswordForm", scpf);
		model.addAttribute("staffName", p.getName());
		model.addAttribute("pageTitle","修改密码");
		model.addAttribute("pageContent", "staff/PasswordChange");
		return "mainpage";
	}

	@RequestMapping("/passwd/submit")
	public String passwordChangeSubmit(Model model, Principal principal, @ModelAttribute StaffChangePasswordForm scpf) {

		model.addAttribute("pageTitle","修改密码");
		String staffName = principal.getName();
		model.addAttribute("staffName", staffName);

		Staff s = ss.getByName(staffName);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		System.out.println("pass in db="+s.getPass_md5());
//		System.out.println("pass input="+passwordEncoder.encode(scpf.getOldPass()));
		if(passwordEncoder.matches(scpf.getOldPass(), s.getPass_md5())) {
			if (scpf.isNewPassOK()) {
				s.setPass_md5(passwordEncoder.encode(scpf.getNewPass1()));
				s.setDescription(scpf.getDescription());
				ss.update(s);
				model.addAttribute("pageContent", "staff/PasswordChangeOk");
			}
			else {
				model.addAttribute("error", scpf.getNewPassCheckResult());
				model.addAttribute("pageContent", "staff/PasswordChange");
			}
		}
		else {
			model.addAttribute("error", "旧密码错误");
			model.addAttribute("pageContent", "staff/PasswordChange");
		}
		return "mainpage";
	}
	
	@RequestMapping("/list")
	public String admin(Model model) {
		List<Staff> ls = ss.getAll();
		Staff superStaff = ss.getByName("super");
		ls.remove(superStaff);
		model.addAttribute("staffList", ls);
		model.addAttribute("pageTitle","员工列表");
		model.addAttribute("pageContent", "staff/StaffList");
		return "mainpage";
	}
	
	@RequestMapping("/input")
	public String add(Model model) {
		StaffForm s = new StaffForm();
		List<Role> roles = new ArrayList<>();
		for(Role r: rs.getAll()) { 
			if(r.getId() > 1) { roles.add(r); }
		};
		
		model.addAttribute("staffForm",s);
		model.addAttribute("roles",roles);
		model.addAttribute("pageTitle","添加用户");
		model.addAttribute("pageContent", "staff/StaffInput");
		return "mainpage";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute StaffForm sf, HttpServletRequest hsr, RedirectAttributes ra) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Staff s = new Staff();
		s.setName(sf.getName());
		
		// Staff 的描述不能为空. 如果为空，设为与 name 相同
		String desc = sf.getDescription();
		if (desc == null || desc.isEmpty()) {
			desc = sf.getName();
		}
		s.setDescription(desc);
		
		s.setPass_md5(passwordEncoder.encode(sf.getPass()));
		if(sf.getRoles() != null) {
			for(String r: sf.getRoles()) {
				Role role = rs.getByUniqueString("role", r);
				s.getRoles().add(role);
			}
		}
		
		String err="";
		try {
			ss.save(s);
		}
		catch (ConstraintViolationException e) {
			err="同名用户已存在";
		}
		ra.addFlashAttribute("err",err);
		return "redirect:/staff/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Staff s = ss.getById(id);
		StaffForm sf = new StaffForm(s);
		List<Role> roles = new ArrayList<>();
		for(Role r: rs.getAll()) { 
			if(r.getId() > 1) { 
				roles.add(r); 
			}
		};
		// 这里真得很有意思， Spring MVC 会自动设备哪些 Roles 是这个 Staff 已有的 
		model.addAttribute("sf", sf);
		model.addAttribute("roles",roles);
		model.addAttribute("pageTitle","修改用户权限");
		model.addAttribute("pageContent", "staff/StaffEdit");
		return "mainpage";
	}
	
	
	@RequestMapping("/update")
	public String update(@ModelAttribute StaffForm sf) {
		Staff s = ss.getByName(sf.getName());
		Set<Role> roles = new HashSet<>();
		for(String str : sf.getRoles()) {
			roles.add(rs.getByUniqueString("role", str));
		}
		s.setRoles(roles);
		ss.update(s);
		return "redirect:/staff/list";
	}
	@RequestMapping("/del/{id}")
	public String del(@PathVariable int id) {
		ss.delById(id);
		return "redirect:/staff/list";
	}
}
