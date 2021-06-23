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
	
	@GetMapping("/signin")
	public String signin() {
		return "frontend/signin";
	}	
	
	@PostMapping("/signin")
	public String signinProcess(@RequestParam Map map,Model model, HttpSession session) {
		System.out.println(map.size());
		//서비스 호출(성공:1,실패시:)
		int flag = memberService.loginOk(map);
		if(flag==1) {//로그인 성공
			session.setAttribute("id", map.get("email"));

			return "redirect:/personal/dashboard";
		}
		return "frontend/signin";
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
