package com.basakcoding.basak.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {
	
	@GetMapping("/class/{id}")
	public String course() {
		return "frontend/course";
	}
}
