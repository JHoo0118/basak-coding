package com.basakcoding.basak.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basakcoding.basak.service.AdminDTO;
import com.basakcoding.basak.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("")
	public String adminHome(Model model) {
		model.addAttribute("title", "홈");
		return "admin/home";
	}
	
	@GetMapping("/login")
	public String loginForm() {
//		AdminDTO admin = adminService.getAdminByEmail("admin@admin.com");
//		adminService.passwordEncoding(admin);

		return "admin/login";
	}
}
