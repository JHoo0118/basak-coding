package com.basakcoding.basak.web.frontend;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basakcoding.basak.service.CatalogService;
import com.basakcoding.basak.service.CourseDTO;

@Controller
public class CatalogController {
	
	
	//리뷰서비스 주입
	@Autowired
	private CatalogService CatalogService;
	
	// 강의목록 페이지
	@GetMapping("/catalog")
	public String catalog(Model model) {
		List<CourseDTO> courseList = CatalogService.courseList();
		
		model.addAttribute("courseList", courseList);
		
		return "frontend/catalog";
	}
	
	
	
	
	
	
	//강의목록 상세 페이지
	@GetMapping("/catalog/{id}")
	public String catalogDetail() {
		
		
		return "/frontend/catalogDetail";
	}
}
