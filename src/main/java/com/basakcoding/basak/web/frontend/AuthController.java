package com.basakcoding.basak.web.frontend;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@GetMapping("/signin")
	public String signin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "frontend/signin";
		}
		return "redirect:/personal/dashboard";
	}	
	
	@GetMapping("/signup")
	public String signup() {
		return "frontend/signup";
	}
	
	@GetMapping("/reset-pass")
	public String resetPassword() {
		return "frontend/resetPassword";
	}
	
	@GetMapping("/email-information")
	public String emailValidate() {
		return "frontend/signupConfirm.html";
	}
}
