package com.basakcoding.basak.web.frontend;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.PaymentService;



@Controller
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	//결제 시작전 페이지, 취소시 이동할 기본 창
	//로그인 아이디로 구매자 이메일 얻어오고 강사명, 수강기간등 얻어오기 
	@GetMapping("/orders/payments/{courseId}")
	public String payment(@PathVariable String courseId, Model model, Authentication auth) {
		Map map = paymentService.listAll();
		String memberId = ((UserDetails)auth.getPrincipal()).getUsername();
		
		MemberDTO memberDto = paymentService.getMemberById(memberId);
		map.put("EMAIL", memberDto.getEmail());
		//강사 이미지 불러오기
		if (map.containsKey("AVATAR")) {
			map.put("PATH", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));
		} else {
			map.put("AVATAR", null);
		}
		System.out.println(map);
		System.out.println(courseId);
		model.addAttribute("course", map);
		
		return "frontend/payment";
	}
	
	
	//결제 완료시 이동할 페이지 
	//구매자 이름과 강사명, 결제 내역 얻어오기
	@GetMapping("/orders/complete/{courseId}")
	public String paymentResult(@PathVariable String courseId, Model model, Authentication auth) {
		Map map = paymentService.priceList();
		String memberId = ((UserDetails)auth.getPrincipal()).getUsername();
		
		MemberDTO memberDto = paymentService.getMemberById(memberId);
		map.put("USERNAME", memberDto.getUsername());
		//강사 이미지 불러오기
		if (map.containsKey("AVATAR")) {
			map.put("PATH", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));
		} else {
			map.put("AVATAR", null);
		}
		System.out.println(map);
		System.out.println(courseId);
		System.out.println(model);
		model.addAttribute("course", map);
		
		return "frontend/paymentConfirm";
	}
	
	
	
}
