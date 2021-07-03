package com.basakcoding.basak.web.frontend;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.basakcoding.basak.service.MemberService;

@Controller
public class HomeController {
	@Autowired
	private MemberService memberService;
	@GetMapping
	public String home(Map map) {
		/*
		map.put("password", "1234");
		map.put("userId", 1);
		int newpass=memberService.passwordEdit(map);
		System.out.println("성공햇는가"+newpass);
		*/
		return "frontend/home";
	}

}
