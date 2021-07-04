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

import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.FAQService;


@Controller
@RequestMapping("/admin/faq")
public class AdminFAQController {

	@Autowired
	FAQService faqService;
	
	// 카테고리 관리 화면으로 이동
	@GetMapping("/management")
	public String faqList(Model model) {
		List<FAQDTO> listFAQs = faqService.faqSelect();
		model.addAttribute("listFAQs", listFAQs);
		model.addAttribute("title", "FAQ 관리");
		return "admin/faqManagement";
	}//faqList
	

}
