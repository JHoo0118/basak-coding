package com.basakcoding.basak.web.frontend;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.basakcoding.basak.service.CatalogService;
import com.basakcoding.basak.service.CourseDTO;


@Controller
public class CatalogController {
	
	
	//리뷰서비스 주입
	@Autowired
	private CatalogService catalogService;
	
	// 강의목록 페이지
	@GetMapping("/catalog")
	public String catalog(Model model ) {
		
		List<Map> courseList = catalogService.courseList();
		for (Map map : courseList) {
			map.put("PATH", "/upload/course/" + map.get("COURSE_ID") + "/thumbnail/" + map.get("THUMBNAIL"));
			
		}
		model.addAttribute("courseList", courseList);
		System.out.println("courseList"+courseList);
		return "frontend/catalog";
	
	
	}
	
	
	
	//강의목록 상세 페이지
	@GetMapping("/catalog/{courseId}")
	public  String catalogDetail(Authentication auth , @PathVariable String courseId , Model model) {
		//로그인 안됐을때 likeCheck값 기본 0으로 초기화된 상태로 맵에저장
		int likeCheck=0;
		
		Map map = catalogService.selectOne(courseId);//강의 상세보기에 필요한 값 받아오기
		List<Map> map1 = catalogService.reviewList(courseId);//강의후기 리스트에 필요한 값 받아오기
		int reviewCount = catalogService.reviewCount(courseId);//총 리뷰갯수 카운트
		String getClob = catalogService.getClobText(courseId);//강의설명(DESCRIPTION) 받아오기
		
		
		if(auth != null) {
			//로그인이 됐을때
			map.put("memberId",((UserDetails)auth.getPrincipal()).getUsername());
			map.put("courseId",courseId);
			likeCheck = catalogService.likeCheck(map);
		
		}
		if (!map.containsKey("AVATAR")) map.put("AVATAR", null);
		else map.put("PATH", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));


		//로그인 안됐을때 likeCheck값 기본 0으로 초기화된 상태로 맵에저장
		map.put("likeCheck", likeCheck);
		
		//CLOB형 자료 스트링화 하여 맵에 저장
		map.put("DESCRIPTION", getClob);	
		


		model.addAttribute("course",map);
		System.out.println("course:"+map);
		
		
		
		model.addAttribute("reviewList",map1);
		System.out.println("reviewList:"+map1);
		model.addAttribute(reviewCount);
		System.out.println("reviewCount:"+reviewCount);
		
		return "/frontend/catalogDetail";

	}
	
	
	//좋아요 안좋아요
	@PostMapping("/catalog/count_like")
	public @ResponseBody int catalogLike(Authentication auth , @RequestBody Map map) throws IOException {
		String memberId;
		int likeCheck;
		int affected;
		String courseId	= (String) map.get("courseId");
		
		if(auth == null) {
			System.out.println("로그인상태가 아님");
			return affected=2;
		
		} else {
			memberId = ((UserDetails)auth.getPrincipal()).getUsername();
			map.put("memberId", memberId);
			likeCheck = catalogService.likeCheck(map);
			System.out.println("memberId:"+memberId+"\r\n"+"courseId:"+courseId);
			
		}
			if(likeCheck == 0) {//좋아요 등록이 안돼있으면
				catalogService.like(map);
				System.out.println("likeCheck:" + likeCheck);
				catalogService.likeCount(map);
				return affected=0;
			
			}//작은 if
			else {//likeCheck 결과 값이 1일떄 (이미 좋아요가 등록이 돼있을때)
				catalogService.unLike(map);
				System.out.println("likeCheck:" + likeCheck);
				catalogService.likeCount(map);
				return affected=1;
			
			}//작은 else
			
	}///catalogLike
	
	//리뷰 작성 컨트롤러
	@PostMapping("/catalog/reviewInsert")
	public @ResponseBody int reviewInsert(Authentication auth  ,@RequestBody Map map) {
		int affected;
		String memberId;
		memberId = ((UserDetails)auth.getPrincipal()).getUsername();

		String content = (String) map.get("content");
		String courseId	= (String) map.get("courseId");
		String selectedStar= (String) map.get("selectedStar");
		    String rating =selectedStar.split("-")[2];
		    System.out.println("rating:"+rating);
		
		System.out.println("content:"+content);
		System.out.println("courseId:"+courseId);
		System.out.println("selectedStar:"+selectedStar);
		
		map.put("memberId",memberId );
		map.put("content", content);
		map.put("courseId", courseId);
		map.put("rating", rating);
		
		catalogService.reviewCheck(map);
		catalogService.reviewInsert(map);
		
		if(catalogService.reviewCheck(map) == 0 ) {
			catalogService.reviewInsert(map);
			return 0;
			
		}
		else {// catalogService.reviewCheck(map) ==1
			return 1;
		}
		
		
		
	
		
		/*
		if(auth == null){//로그인 돼있지 않을시
			
			
			return affected=2;	
		}
		else {
			memberId = ((UserDetails)auth.getPrincipal()).getUsername();
			map.put("memberId", memberId);
			map.put("courseId",courseId);
			checkPayment = catalogService.checkPayment(map);
		}
			if(checkPayment==0) {//로그인 후 미결제된 아이디일때
				System.out.println("checkPayment:"+checkPayment);
				
			return affected=0;
			}
			else {//로그인 후 결제가 된 아이디일때 //checkPayment 값이 1일때
				System.out.println("checkPayment:"+checkPayment);
			
			return affected=1;
			}
			*/
	
	
	}
	
	@PostMapping("/catalog/reviewWrite")
	public @ResponseBody int reviewWrite(Authentication auth  ,@RequestBody Map map) {
		int affected;
		int checkPayment;
		String memberId;
		String courseId	= (String) map.get("courseId");
		
		if(auth == null){//로그인 돼있지 않을시
			
			return affected=2;	
		}
		else {//로그인 돼있을시
			memberId = ((UserDetails)auth.getPrincipal()).getUsername();
			map.put("memberId", memberId);
			map.put("courseId",courseId);
			checkPayment = catalogService.checkPayment(map);
		}
		if(checkPayment==0) 
			return affected=0;//결제한 이력이 없으면 0반환
 		
		else return affected=1;//결제한 이력이 있으면 1반환
		
		
		
	}

	
	
	
}//////
