package com.basakcoding.basak.android;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.service.android.AndroidMemberService;

@RestController
@RequestMapping("/api/1.0")
public class AndroidAuthRestController {
	@Autowired
	private AndroidMemberService androidMemberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@CrossOrigin
	@PostMapping("/login")
	public MemberDTO login(@RequestBody Map<String, String> map) {

		MemberDTO member = null;
		String password = androidMemberService.getPassword(map.get("email"));
		if (password == null) return member;
		
		boolean isCorrect = passwordEncoder.matches(map.get("password"), password);

		if (isCorrect) {
			member = androidMemberService.login(map.get("email"));
		}
		return member;
	}
	
	@CrossOrigin
	@PostMapping("/google")
	public int googleSignUp(@RequestBody Map<String, String> map) {
		String memberId = androidMemberService.isAlreadyJoined(map.get("email"));
		if (memberId == null) {
			androidMemberService.googleJoin(map);
			memberId = map.get("memberId");
		}
		
		return Integer.parseInt(memberId);
	}
}
