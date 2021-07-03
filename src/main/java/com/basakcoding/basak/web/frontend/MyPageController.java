package com.basakcoding.basak.web.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/personal")
public class MyPageController {
	@Autowired
	private MemberService memberService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping("/password/edit.do")
	public @ResponseBody boolean passwordEdit(@RequestBody Map map, Authentication auth) {
		// System.out.println("넘어오는 비밀번호:"+map);
		boolean booleanpass = passwordEncoder.matches((map.get("pass").toString()),
				((UserDetails) auth.getPrincipal()).getPassword());
		// System.out.println("비밀번호가 일치하는지!"+booleanpass);
		return booleanpass;
	}

	public Model userInfo(Model model, Authentication auth) {
		int userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
		// 내결제정보
		int paymentCount = memberService.paymentCount(userId);
		model.addAttribute("paymentCount", paymentCount);
		// 뿌려줄 내정보 가져오기
		Map map = memberService.selectMyInfo(userId);
		if (!map.containsKey("AVATAR"))
			map.put("AVATAR", null);
		else {
			model.addAttribute("PATH", "/upload/member/" + userId + "/" + map.get("AVATAR"));
		}
		model.addAttribute("member", map);
		model.addAttribute("userId", userId);
		// 내댓글 개수
		int commentsCount = memberService.commentsCount(userId);
		model.addAttribute("commentsCount", commentsCount);
		// 내 질문 개수
		int questionCount = memberService.questionCount(userId);
		model.addAttribute("questionCount", questionCount);
		// 파일 이미지 경로
		String uploadDir = "upload/member/" + userId;
		model.addAttribute("uploadDir", uploadDir);
		return model;
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Authentication auth) {
		userInfo(model, auth);
		// 내강의
		int userId = (int) model.getAttribute("userId");
		List<Map> map = memberService.myCourses(userId);
		if (!map.isEmpty())
			model.addAttribute("myCourses", map);

		return "frontend/dashboard";
	}

	@GetMapping("/profile")
	public String profile(Model model, Authentication auth) {
		userInfo(model, auth);
		// 내결제
		int userId = (int) model.getAttribute("userId");
		List<Map> map = memberService.myPayment(userId);
		if (!map.isEmpty())
			model.addAttribute("myPayment", map);
		return "frontend/profile";
	}

	@GetMapping("/qAndA/questions")
	public String qAndA(Model model, Authentication auth) {
		userInfo(model, auth);
		// 내 문의 제목,시간
		int userId = (int) model.getAttribute("userId");
		List<Map> map = memberService.myInquiry(userId);
		if (!map.isEmpty())
			model.addAttribute("myInquiry", map);
		// 내 댓글 제목,시간
		List<Map> map1 = memberService.myComments(userId);
		if (!map1.isEmpty())
			model.addAttribute("myComments", map1);
		
		return "frontend/qAndA";
	}

	@RequestMapping("/dashBoard/profileImgUpdate")
	public @ResponseBody int profileImgUpdate(@RequestParam String imgname, @RequestParam MultipartFile file,
			Authentication auth) throws IllegalStateException, IOException {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		int userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
		String uploadDir = "upload/member/" + userId;
		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, filename, file);
		Map map = new HashMap();
		int file_kind = filename.lastIndexOf(".");
		String file_type = filename.substring(file_kind + 1, filename.length());
		String[] check_file_type = { "jpg", "gif", "png", "jpeg", "bmp", "tif" };
		map.put("userId", userId);
		map.put("file_name", filename);
		// DB에 업데이트
		int success;
		success = memberService.fileUpdate(map);
		if (!Arrays.asList(check_file_type).contains(file_type)) { // 이미지파일이 아닌경우
			success = -1;
		} else if (file.getSize() > 1024 * 1024) { // 용량이 너무클경우
			success = -2;
		} else if (success == 0) {// DB오류
		}
		return success;
	}

	@RequestMapping("/userprofile/edit.do")
	public @ResponseBody int userNameEdit(@RequestBody Map map, Authentication auth) {
		int userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
		String username = (String) map.get("username");
		if (username.length() >= 11) { // 11글자이상입력했을경우
			return -1;
		}
		map.put("username", username);
		map.put("userId", userId);
		int success;
		success = memberService.userNameEdit(map);

		return success;
	}

	@PostMapping("/newpass/edit.do")
	public String newPass(@RequestParam Map map, Model model) {
		map.put("password", map.get("newpass1"));
		int newpass = memberService.passwordEdit(map);
		model.addAttribute("passedit", newpass);
		return "forward:/logout";
	}

	@Controller
	public class LogoutController {

		@RequestMapping(value = "/logout")
		public String logout(HttpServletRequest request, HttpServletResponse response) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.isAuthenticated()) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			return "frontend/signin";
		}

	}//////////// LogoutController
		// 상세보기 정보가져오기

	@RequestMapping(value = "/viewDetails.do", method = RequestMethod.POST)
	public @ResponseBody Map viewDetails(@RequestParam("payment_code") String payment_code, Model model,
			Authentication auth) {
		String userId = ((UserDetails) auth.getPrincipal()).getUsername();
		String pay_code = payment_code;
		Map map = memberService.viewDetails(userId, pay_code);
		return map;
	}

	// 문의 상세보기
	@RequestMapping(value = "/inquDetails.do", method = RequestMethod.POST)
	public @ResponseBody Map inquDetails(@RequestParam("inquiry_id") String inquiry_id, Model model, Authentication auth) {
		String userId = ((UserDetails) auth.getPrincipal()).getUsername();
		Map map = memberService.inquDetails(userId, inquiry_id);
		
		if (map == null) {
			map = memberService.inquDetailNotExist(userId, inquiry_id);
		} 
		else {
			if (!map.containsKey("ANSWER_ID"))
				map.put("ANSWER_ID", null);
			else model.addAttribute("admin_path","/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));
			
		}
		return map;
	}

	//댓글 상세보기
	@RequestMapping(value = "/commentsDetails.do", method = RequestMethod.POST)
	public @ResponseBody Map commentDetails(@RequestParam("commenTitle") String commenTitle, Model model, Authentication auth) {
		String userId = ((UserDetails) auth.getPrincipal()).getUsername();
		Map map = memberService.commentsDetails(userId, commenTitle);
		return map;
	}

}///////////// MyPageController
