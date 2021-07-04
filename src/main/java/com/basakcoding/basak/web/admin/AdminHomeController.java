package com.basakcoding.basak.web.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basakcoding.basak.service.AdminDTO;
import com.basakcoding.basak.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("")
	public String adminHome(HttpSession session, Model model) {
		model.addAttribute("title", "홈");
		if(session.getAttribute("loginEmail")!=null){ 
            return "admin/home";
        }
		else{
            return "admin/login";
		}
	}
		
		
		
	
	@PostMapping("/login")
	public String LoginForm(@RequestParam Map map, HttpSession session) {
		
		
		if(adminService.loginAdmin(map)==1){ 
			session.setAttribute("loginEmail",map.get("email"));
            return "admin/home";
        }
		else{
            return "admin/login";
		}

		 
	}
	
	@RequestMapping("/logout")
    public String logoutForm(HttpSession session) {		
		session.invalidate();    //로그아웃 처리-세션영역 데이타 삭제
        return "admin/login";    //뷰정보 번환]
    }

	
	@GetMapping("/login")
	public String loginForm() {

		return "admin/login";
	}
}
