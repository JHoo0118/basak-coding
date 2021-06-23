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
	
	@PostMapping("/signin")//로그인 확인
	public String signinProcess(@RequestParam Map map,Model model, HttpSession session) {
		System.out.println(map.size());
		//서비스 호출
		String flag = memberService.loginOk(map);
		if(flag != null) {//로그인 성공
			session.setAttribute("id", flag);

			return "redirect:/personal/dashboard";
		}
		return "";//로그인 실패
	}//signinProcess
	
	@RequestMapping("/logout")//로그아웃
	public String logout(HttpSession session) {
		//로그아웃 처리-세션영역 데이타 삭제
		session.invalidate();
		//뷰정보 번환]
		return "frontend/signin";
	}//logout
	
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
