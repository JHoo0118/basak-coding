package com.basakcoding.basak.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
	
	@GetMapping("/admin/login")
	public String login() {
		return "/admin/login";
	}
}
