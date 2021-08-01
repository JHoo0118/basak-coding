package com.basakcoding.basak.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.android.AndroidCourseService;
import com.basakcoding.basak.service.android.AndroidMemberService;

@RestController
@RequestMapping("/api/1.0")
public class AndroidMemberController {
	
	@Autowired
	AndroidMemberService androidMemberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//마이페이지 정보 뿌려주기
	@GetMapping("/mypage")
	public Map getMyPage(@Param("memberId") String memberId) {
		Map MyPage = new HashMap();
		MyPage = androidMemberService.getMyPage(memberId);
		Map params = new HashMap();
		params.put("memberId", memberId);
		System.out.println(memberId);
		System.out.println(params);
		return MyPage;
	}
	
	//마이페이지 회원정보 수정
	@CrossOrigin
	@PostMapping("/updateMember")
	public Map updateMember(@RequestBody Map map) {
		System.out.println(map);
		androidMemberService.updateMem(map);
		
		return map;
   }

	
}
