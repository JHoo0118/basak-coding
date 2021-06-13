package com.basakcoding.basak.web.admin;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.MemberService;

@RestController
public class AdminMemberRestController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/admin/member/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return memberService.isEmailUnique(email) ? "yes" : "no";
	}
}
