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

import com.basakcoding.basak.mapper.InquiryMapper;
import com.basakcoding.basak.service.InquiryDTO;
import com.basakcoding.basak.service.InquiryService;


@Controller
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {

	@Autowired
	InquiryService inquiryService;
	
	//문의 관리 화면으로 이동
	@GetMapping("/management")
	public String inquiryList(Model model) {
		List<InquiryDTO> listInquirys = inquiryService.inquirySelect();
		model.addAttribute("listInquirys", listInquirys);
		model.addAttribute("title", "문의 관리");
		model.addAttribute("tab", "inq");
		return "admin/inquiryManagement";
	}//likeList
	
	@RequestMapping("/management/view")
	public String inquiryView(@RequestParam Map map,Model model) {
		InquiryDTO inquiry = inquiryService.selectOne(map);
		model.addAttribute("inquiry", inquiry);
		if(map.get("page") != null) {
			model.addAttribute("title", "문의 관리");
			return "admin/inquiryView";
		}
		model.addAttribute("title", "문의 관리");
		return "admin/inquiryView";
	}//문의 상세보기

	@RequestMapping("/management/deleteone")
	public String deleteInquiryOne(@RequestParam Map map) {
		int affected = inquiryService.deleteOne(map);
		if(affected==1) {

			return "forward:/admin/inquiry/management";
		}
		else {
			return "forward:/admin";
		}
	}
}//class
