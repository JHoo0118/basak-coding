package com.basakcoding.basak.web.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.service.PaymentService;
import com.basakcoding.basak.util.FileUploadUtil;



@Controller
public class PaymentController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PaymentService paymentService;
	
	//결제 시작전 페이지, 취소시 이동할 기본 창
	//로그인 아이디로 구매자 이메일 얻어오고 강사명, 수강기간등 얻어오기 
	@GetMapping("/orders/payments/{courseId}")
	public String payment(@PathVariable String courseId, Model model, Authentication auth) {
		Map map = paymentService.listAll(courseId);
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		
		MemberDTO memberDto = paymentService.getMemberById(memberId);
		map.put("EMAIL", memberDto.getEmail());
		//강의 이미지 불러오기
		if (map.containsKey("THUMBNAIL")) {
			map.put("THUMB_PATH", "/upload/course/" + map.get("COURSE_ID") + "/thumbnail/" + map.get("THUMBNAIL"));
		} else {
			map.put("THUMBNAIL", null);
		}
		model.addAttribute("course", map);
		
		return "frontend/payment";
	}
	
	
	//결제 완료시 이동할 페이지 
	//구매자 이름과 강사명, 결제 내역 얻어오기
	@GetMapping("/orders/complete/{courseId}")
	public String paymentResult(@PathVariable String courseId, Model model, Authentication auth) {
		Map map2 = new HashMap();
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		map2.put("courseId", courseId);
		map2.put("memberId", memberId);
		Map map = paymentService.priceList(map2);
		//강의 이미지 불러오기
		if (map.containsKey("THUMBNAIL")) {
			map.put("THUMB_PATH", "/upload/course/" + map.get("COURSE_ID") + "/thumbnail/" + map.get("THUMBNAIL"));
		} else {
			map.put("THUMBNAIL", null);
		}
		
		MemberDTO memberDto = paymentService.getMemberById(memberId);
		map.put("USERNAME", memberDto.getUsername());
		//강사 이미지 불러오기
		if (map.containsKey("AVATAR")) {
			map.put("PATH", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));
		} else {
			map.put("AVATAR", null);
		}
		model.addAttribute("course", map);
		
		return "frontend/paymentConfirm";
	}
	
	//
	@PostMapping("/payments/complete")
	@ResponseBody
	public String paymentsComplete (@RequestParam Map map, Authentication auth) throws IOException {
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		map.put("memberId", memberId);

		int affected = paymentService.insertPayment(map);
		if (affected == 1) {
			String courseId = map.get("courseId").toString();
			List<String> videoIds = paymentService.getAllVideoIds(courseId);
			Map params = new HashMap();
			params.put("memberId", memberId);
			for (int i=0; i<videoIds.size(); i++) {
				params.put("videoId", videoIds.get(i));
				int result = paymentService.insertVideoRecord(params);
				List<String> filenameList = paymentService.getFilenameList(videoIds.get(i));
				for (String filename : filenameList) {
					String copyFileDirName = "upload/course/" + courseId + "/file/copy-" + memberId + "-" + filename;
					String originFileDirName =  "upload/course/" + courseId + "/file/" + filename;
					FileUploadUtil.copyFile(originFileDirName, copyFileDirName);
				}
			}
			return "1";
		}
		return "-1";
	}
	
	@PostMapping("/payments/check")
	@ResponseBody
	public String paymentsCheck (@RequestParam Map map, Authentication auth) {
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		map.put("memberId", memberId);
		int count = paymentService.alreadyPayment(map);
		if (count >= 1)
			return "-1";
		return "1";
	}
	
}
