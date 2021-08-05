package com.basakcoding.basak.web.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.basakcoding.basak.mapper.InquiryMapper;
import com.basakcoding.basak.service.AdminDTO;
import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.ClassificationDTO;
import com.basakcoding.basak.service.InquiryDTO;
import com.basakcoding.basak.service.InquiryService;
import com.basakcoding.basak.service.QuestionDTO;


@Controller
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {

	@Autowired
	InquiryService inquiryService;
	
	//문의 관리 화면으로 이동
	@GetMapping("/management")
	public String inquiryList(Model model) {
		List<InquiryDTO> listInquirys = inquiryService.inquirySelect();
		for (InquiryDTO inquiry : listInquirys) {
			int result = inquiryService.isAnswered(Integer.toString(inquiry.getInquiryId()));
			inquiry.setIsAnswered(result);
		}
		
		System.out.println(listInquirys);
		model.addAttribute("listInquirys", listInquirys);
		model.addAttribute("title", "문의 관리");
		model.addAttribute("tab", "inq");
		return "admin/inquiryManagement";
	}//likeList
	
	
	@RequestMapping("/management/view")
	public String inquiryView(@RequestParam Map map,Model model) {
		InquiryDTO inquiry = inquiryService.selectOne(map);
		model.addAttribute("isAnswered", Integer.parseInt(map.get("isAnswered").toString()));
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
	

	
	
	// 질문 카테고리 관리 화면으로 이동
	@GetMapping("/category/management") 
	public String questioncategory(Model model) {
			List<ClassificationDTO> listInquirys = inquiryService.selectList();
			model.addAttribute("listInquirys", listInquirys);
			model.addAttribute("title", "문의 카테고리 관리");		
		return "admin/inquirycategory";
	}


		
		// 문의 카테고리 생성 및 수정 폼으로 이동
		@GetMapping("/category/form")
		public String questionForm(@RequestParam Map<String, String> map, Model model) {
			String clId = map.get("clId");
			ClassificationDTO inquiry = new ClassificationDTO();
			if (clId != null) {
				inquiry = inquiryService.getInquiryById(clId);
			}
			
			model.addAttribute("inquiry", inquiry);
			model.addAttribute("title", "문의 카테고리 관리");
			return "admin/inquiryForm";
		}
		
		// 문의 카테고리 생성 및 수정
		@PostMapping("/save")
		public String createSave(
				@RequestParam Map map,
				RedirectAttributes redirectAttributes) {
			if (map.get("clId").equals("")) {
				inquiryService.createInquiry(map);
				redirectAttributes.addFlashAttribute("message", "문의내용이 등록되었습니다.");
			}
			else {
				inquiryService.updateInquiry(map);
				redirectAttributes.addFlashAttribute("message", "문의내용이 수정되었습니다.");
			}
			return "redirect:/admin/inquiry/category/management";
		}

		
		// 문의 카테고리 수정 폼 이동 or 삭제
		@PostMapping("/category/process")
		public String Process(
				@RequestParam Map map, 
				@RequestParam List<String> target, 
				RedirectAttributes redirectAttributes) throws IOException {
			String action = map.get("action").toString();
			if ("edit".equals(action)) {
				redirectAttributes.addAttribute("clId", map.get("target").toString());
				return "redirect:/admin/inquiry/category/form";
			} 
			else {
				map.put("target", target);
				inquiryService.deleteMultpleInquiry(map);
				redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");
				return "redirect:/admin/inquiry/category/management";
			}
		}	
		@GetMapping("/management/form")
		public String memberForm(@RequestParam Map map, Model model) {

			model.addAttribute("title", "문의 관리");
			model.addAttribute("inquiryId", map.get("no"));
			return "admin/inquiryEdit";
		}
		
		
		@PostMapping("/management/form/save")
		public String memberForm2(@RequestParam Map map, HttpServletRequest res ,Model model, RedirectAttributes redirectAttributes) {
		System.out.println(map);
		map.put("adminId", res.getSession().getAttribute("adminId"));
		
		
		if (map.get("inquiryId").equals("")) {
			inquiryService.createAnswer(map);
			redirectAttributes.addFlashAttribute("message", "문의내용이 등록되었습니다.");
		}
		else {
			inquiryService.updateAnswer(map);
			redirectAttributes.addFlashAttribute("message", "문의내용이 수정되었습니다.");
		}
			
			return "redirect:/admin/inquiry/management";
		}


		
}//class
