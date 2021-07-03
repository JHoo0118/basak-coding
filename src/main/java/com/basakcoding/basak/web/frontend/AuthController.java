package com.basakcoding.basak.web.frontend;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basakcoding.basak.service.MemberService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@GetMapping("/signin")//로그인 페이지
	public String signin() {
		return "frontend/signin";
	}//signin	

	
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
		return "frontend/signupConfirm";
	}
}
