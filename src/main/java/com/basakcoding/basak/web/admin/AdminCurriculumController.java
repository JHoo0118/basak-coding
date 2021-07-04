package com.basakcoding.basak.web.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.CurriculumService;


@Controller
@RequestMapping("/admin/curriculum")
public class AdminCurriculumController {

	@Autowired
	CurriculumService curriculumService;
	
	// 커리큘럼 관리 화면으로 이동
	@GetMapping("/management")
	public String curriculumList(Model model) {
		List<CurriculumDTO> listCurriculums = curriculumService.curriculumSelect();
		model.addAttribute("listCurriculums", listCurriculums);
		model.addAttribute("title", "커리큘럼 관리");
		return "admin/curriculumManagement";
	}//curriculumList
	

}
