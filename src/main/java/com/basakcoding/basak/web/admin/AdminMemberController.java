package com.basakcoding.basak.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminMemberController {

	@Autowired
	private MemberService service;
	
	
	// 사용자 관리 페이지
	@GetMapping("/member/management")
	public String memberList(Model model) {
		List<MemberDTO> listMembers = service.selectList();
		model.addAttribute("listMembers", listMembers);
		model.addAttribute("title", "사용자 관리");
		return "/admin/memberManagement";
	}
	
	// 사용자 관리 폼 페이지
	@GetMapping("/member/management/form")
	public String memberForm(Model model) {
		model.addAttribute("title", "사용자 관리");
		return "/admin/memberForm";
	}
}
