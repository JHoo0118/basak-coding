package com.basakcoding.basak.web.frontend;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.basakcoding.basak.service.CatalogService;
import com.basakcoding.basak.service.CourseDTO;


@SessionAttributes("memberId")
@Controller
public class CatalogController {
	
	
	//리뷰서비스 주입
	@Autowired
	private CatalogService catalogService;
	
	// 강의목록 페이지
	@GetMapping("/catalog")
	public String catalog(Model model ) {
		List<Map> courseList = catalogService.courseList();
		
		model.addAttribute("courseList", courseList);
		
		return "frontend/catalog";
	}
	
	
	
	//강의목록 상세 페이지
	@GetMapping("/catalog/{courseId}")
	public String catalogDetail(@PathVariable String courseId , Model model) {
			
		Map map = catalogService.selectOne(courseId);
		System.out.println("Map:"+map);
		
		model.addAttribute("course",map);
		
		return "/frontend/catalogDetail";

	}
	
	
	//좋아요 안좋아요
	@PostMapping("/catalog/count_like")
	public @ResponseBody int catalogLike(@RequestBody Map map ,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	    //String memberId = req.getSession().getAttribute("memberId").toString();	
		String memberId = (String)map.get("memberId");
		String courseId	= (String) map.get("courseId");
		
		System.out.println("memberId:"+memberId+"\r\n"+"courseId:"+courseId);
		
		int affected;
		
		if(memberId == null){//비로그인시
			resp.setContentType("text/html; charset-UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>");			
			out.println("alert('로그인 후 이용 가능합니다');");
			out.println("history.back();");
			out.println("</script>");
			System.out.println("로그인상태가 아님");
			return affected=2;
			
		}//큰 if
		else {//로그인 했을때 
			int likeCheck = catalogService.likeCheck(map);
			
			if(likeCheck == 0) {//좋아요 등록이 안돼있으면
				int like = catalogService.like(map);
				System.out.println("likeCheck:" + likeCheck);
				int likePlus = catalogService.likeCount(map);
				return affected=0;
			
			}//작은 if
			else {//likeCheck 결과 값이 1일떄 (이미 좋아요가 등록이 돼있을때)
				
				int unLike = catalogService.unLike(map);
				System.out.println("likeCheck:" + likeCheck);
				int likeMinus = catalogService.likeCount(map);
				return affected=1;
			
			}//작은 else
			
		}//큰 else
		
	}///catalogLike
	
	
}
