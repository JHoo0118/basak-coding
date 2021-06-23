package com.basakcoding.basak.web.admin;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.AdminService;

@RestController
public class AdminAdminRestController {
	
	@Autowired
	private AdminService adminService;
	
	// 관리자 생성 시 이메일 중복 체크
	@PostMapping("/admin/admin/check_email")
	public String checkDuplicateEmailAndNameAdmin(
			@Param("email") String email,
			@Param("name") String name) {
		if (adminService.getAdminByEmail(email) != null)
			return "em";
		
		if (adminService.getAdminByName(name) != null)
			return "na";

		return "yes";
	}
}
