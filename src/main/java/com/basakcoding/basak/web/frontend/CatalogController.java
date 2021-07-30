package com.basakcoding.basak.web.frontend;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.MemberService;


@Controller
public class CatalogController {
	
	
	//리뷰서비스 주입
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CatalogService catalogService;
	
	// 강의목록 페이지
	@GetMapping("/catalog")
	public String catalog(Model model) {
		
		List<Map> courseList = catalogService.courseList();
		
		
		for (Map map : courseList) {
			
			map.put("PATH", "/upload/course/" + map.get("COURSE_ID") + "/thumbnail/" + map.get("THUMBNAIL"));
			if (map.containsKey("AVATAR")) {
				map.put("ADMIN_PATH", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));
			} else {
				map.put("AVATAR", null);
			}
		}
		
		model.addAttribute("courseList", courseList);
		return "frontend/catalog";
	
	
	}
	
	
	
	//강의목록 상세 페이지
	@GetMapping("/catalog/{courseId}")
	public  String catalogDetail(Authentication auth , @PathVariable String courseId , Model model) {
		//로그인 안됐을때 likeCheck값 기본 0으로 초기화된 상태로 맵에저장
		int likeCheck=0;
		int reviewCheck=0;
		int checkPayment=2;
		String star = "0";
		Map map = catalogService.selectOne(courseId);//강의 상세보기에 필요한 값 받아오기
		
		List<Map> map1 = catalogService.reviewList(courseId);//강의후기 리스트에 필요한 값 받아오기
		
		for (Map ma : map1) {
			if (ma.containsKey("AVATAR")) {
				ma.put("MEMBER_PATH", "/upload/member/" + ma.get("MEMBER_ID").toString() + "/" + ma.get("AVATAR"));
			} else {
				ma.put("AVATAR", null);
			}
		}
		
		List<Map> map2  = catalogService.faqList(courseId);
		
		List<CurriculumDTO> curriculumList = catalogService.getCurriculumList(courseId);
		
		
		int reviewCount = catalogService.reviewCount(courseId);//총 리뷰갯수 카운트
		
		String getClob = catalogService.getClobText(courseId);//강의설명(DESCRIPTION) 받아오기
		
		
		
		if(auth != null) {//로그인이 됐을때
			String memberId = null;
			if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
				memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
			} else {
				memberId = ((UserDetails) auth.getPrincipal()).getUsername();
			}
		
			map.put("memberId",memberId);
			map.put("courseId",courseId);
			
			//결제 내역 판단 여부
			checkPayment = catalogService.checkPayment(map);
			//이전 좋아요 여부 확인
			likeCheck = catalogService.likeCheck(map);
			//이전 리뷰등록 여부 확인
			reviewCheck = catalogService.reviewCheck(map);
			
		
			if(reviewCheck==1) {
			
				String reviewRating = catalogService.reviewRating(map);
				String a ="rating-input-";
				star = a.concat(reviewRating) ;
				
				
			}
			
		}
		
		if (!map.containsKey("AVATAR")) map.put("AVATAR", null);
		else map.put("PATH", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));


		//비로그인 시 likeCheck값/reviewCheck/checkPayment
		//위의 값들이 기본 0으로 초기화된 상태로 맵에저장
		map.put("likeCheck", likeCheck);
		map.put("reviewCheck", reviewCheck);
		map.put("checkPayment",checkPayment);
		
		//CLOB형 자료 스트링화 하여 맵에 저장
		map.put("DESCRIPTION", getClob);	
		
		map.put("star",star);
		

		model.addAttribute("course",map);
		
		model.addAttribute("reviewList",map1);
		
		model.addAttribute("faqList",map2);
		
		model.addAttribute("curriculums",curriculumList);
		System.out.println("curriculumList:"+curriculumList);
		model.addAttribute("reviewCount",reviewCount);
		
		String notPost ="현재 등록된 글이 없습니다. 강의등록 후 후기를 남겨주세요!";
		model.addAttribute("notPost", notPost);
		
		
		return "/frontend/catalogDetail";

	}
	
	
	//좋아요 안좋아요
	@PostMapping("/catalog/count_like")
	public @ResponseBody int catalogLike(Authentication auth , @RequestBody Map map) throws IOException {
		String memberId = null;
		
		int likeCheck;
		int affected;
		String courseId	= (String) map.get("courseId");
		
		if(auth == null) {
			return affected=2;
		
		} else {
			if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
				memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
			} else {
				memberId = ((UserDetails) auth.getPrincipal()).getUsername();
			}
			map.put("memberId", memberId);
			likeCheck = catalogService.likeCheck(map);
			
		}
			if(likeCheck == 0) {//좋아요 등록이 안돼있으면
				catalogService.like(map);
				catalogService.likeCount(map);
				return affected=0;
			
			}//작은 if
			else {//likeCheck 결과 값이 1일떄 (이미 좋아요가 등록이 돼있을때)
				catalogService.unLike(map);
				catalogService.likeCount(map);
				return affected=1;
			
			}//작은 else
			
	}///catalogLike
	
	
	
	@PostMapping("/catalog/reviewWrite")//후기작성
	public @ResponseBody Map reviewWrite(Authentication auth  ,@RequestBody Map map, Model model) {
		int affected;
		int checkPayment;
		String memberId = null;
		String courseId	= (String) map.get("courseId");
		
		if(auth == null){//로그인 돼있지 않을시
			affected=3;
			map.put("affected",affected);	
			return map;
		}
		else {//로그인 돼있을시
			if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
				memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
			} else {
				memberId = ((UserDetails) auth.getPrincipal()).getUsername();
			}
			map.put("memberId", memberId);
			map.put("courseId",courseId);
			checkPayment = catalogService.checkPayment(map);
		}
		
		
		if(checkPayment==0) {//결제한 이력이 없으면 0반환
			affected=0;
			map.put("affected", affected);
			return map;
		}
 		
		else {//결제한 이력도 있고 리뷰작성한 이력도 있으면 1반환
			if(catalogService.reviewCheck(map)==1) {

				String reviewContent = catalogService.reviewContent(map);
				map.put("reviewContent",reviewContent);
				String reviewRating = catalogService.reviewRating(map);
				String a ="rating-input-";
				String star = a.concat(reviewRating) ;
				map.put("star",star);
				
				affected=1;
				map.put("affected", affected);
				return map;
			}
			else {//결제는 했으나 리뷰를 작성 안한사람 2반환
				
				affected=2;
				map.put("affected", affected);
				return map;

				
			}
			
		}
		
	}
	
	@PostMapping("/catalog/reviewDelete")
	public @ResponseBody int reviewDelete(Authentication auth,@RequestBody Map map,Model model) {
		
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		map.put("memberId", memberId);
		
		String courseId	= (String) map.get("courseId");
		map.put("courseId", courseId);
		
		int reviewDelete = catalogService.reviewDelete(map);
		
		return reviewDelete;
	}
	
	//리뷰 작성 컨트롤러
	@PostMapping("/catalog/reviewInsert")
	public @ResponseBody Map reviewInsert(Authentication auth  ,@RequestBody Map map) {
		int affected;
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			memberId = Integer.toString(memberService.getMemberByEmail(((OAuth2User) auth.getPrincipal()).getAttribute("email")).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}


		String content = (String) map.get("content");
	
		
		String courseId	= (String) map.get("courseId");
		String selectedStar= (String) map.get("selectedStar");
	    String rating =selectedStar.split("-")[2];
		    
		    
		
		map.put("memberId",memberId );
		map.put("content", content);
		map.put("courseId", courseId);
		map.put("rating", rating);
		

		
		
		if( catalogService.reviewCheck(map) == 0 ) {//이전에 강의후기를 작성하지 않았을 경우
			//리뷰테이블에 강의후기 등록
			catalogService.reviewInsert(map);
			//ajax : data값 0반환
			affected=4;
			map.put("affected",affected);	
			return map;
			
		}
		else {//이전에 강의후기를 작성했을 경우
			
			// 업데이트  작성
			catalogService.reviewUpdate(map);
			affected =5;
			map.put("affected",affected);	
			return map;
		}
		
		
	}
	

	
	
	
}//////
