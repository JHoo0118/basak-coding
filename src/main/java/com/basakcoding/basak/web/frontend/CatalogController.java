package com.basakcoding.basak.web.frontend;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basakcoding.basak.service.CatalogService;
import com.basakcoding.basak.service.CourseDTO;

@Controller
public class CatalogController {
	
	
	//리뷰서비스 주입
	@Autowired
	private CatalogService catalogService;
	
	// 강의목록 페이지
	@GetMapping("/catalog")
	public String catalog(Model model) {
		List<CourseDTO> courseList = catalogService.courseList();
		
		model.addAttribute("courseList", courseList);
		
		return "frontend/catalog";
	}
	
	
	
	//강의목록 상세 페이지
	@GetMapping("/catalog/{id}")
	public String catalogDetail(@PathVariable String id , Model model) {
			
		Map map = catalogService.selectOne(id);
		System.out.println("Map:"+map);
		
		model.addAttribute("course",map);
		
		return "/frontend/catalogDetail";

	}
	
	
	//좋아요 안좋아요
	@PostMapping("/catalog/count_like")
	public @ResponseBody String catalogLike(@RequestParam Map<String,String> map) {
	String userId	= map.get("userId");
	String courseId	= map.get("courseId");
		
		System.out.println(map.get("userId")+","+map.get("courseId")); 
		System.out.println(userId+courseId);
		
		
		return "";
	}
	
	
	
}
