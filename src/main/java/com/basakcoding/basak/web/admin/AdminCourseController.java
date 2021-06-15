package com.basakcoding.basak.web.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basakcoding.basak.service.CourseService;

@Controller
@RequestMapping("/admin/course")
public class AdminCourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/management")
	public String categoryList(Model model) {
		List<Map<String, String>> listCourses = courseService.selectList();
		model.addAttribute("listCourses", listCourses);
		model.addAttribute("title", "강의 관리");
		return "admin/courseManagement";
	}
}
