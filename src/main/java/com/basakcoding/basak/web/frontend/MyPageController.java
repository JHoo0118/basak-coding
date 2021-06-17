package com.basakcoding.basak.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
public class MyPageController {

	@GetMapping("/dashboard")
	public String dashboard() {
		
		return "/frontend/dashboard";
	}
	

	@GetMapping("/profile")
	public String profile() {
		return "/frontend/profile";
	}
	
	@GetMapping("/qAndA/questions")
	public String qAndA() {
		return "/frontend/qAndA";
	}
}
