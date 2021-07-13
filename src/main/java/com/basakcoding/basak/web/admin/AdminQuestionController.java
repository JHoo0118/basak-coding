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

import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.QuestionDTO;
import com.basakcoding.basak.service.QuestionService;


@Controller
@RequestMapping("/admin/question")
public class AdminQuestionController {

	@Autowired
	QuestionService questionService;
	
	// 질문 관리 화면으로 이동
	@GetMapping("/management")
	public String questionList(Model model) {
		List<QuestionDTO> listQuestions = questionService.lionList();
		model.addAttribute("listQuestions", listQuestions);
		model.addAttribute("title", "질문 관리");
		
		return "admin/questionManagement";
	}//likeList
	
	
	// 질문 카테고리 관리 화면으로 이동
	@GetMapping("/catagory")
	public String questioncatagory(Model model) {
//		List<QuestionDTO> listQuestions = questionService.questionList();
//		model.addAttribute("listQuestions", listQuestions);
//		model.addAttribute("title", "문의 카테고리 관리");
		
		return "admin/questioncatagory";
		
	}
	
	
	// 문의 카테고리 생성 및 수정 폼으로 이동
	@GetMapping("/catagory/form")
	public String questionForm(@RequestParam Map<String, String> map, Model model) {
		String questionId = map.get("questionId");
		QuestionDTO question = new QuestionDTO();
		if (questionId != null) {
			question = questionService.getQuestionById(questionId);
		}
		
		model.addAttribute("question", question);
		model.addAttribute("title", "문의 카테고리 관리");
		return "admin/questionForm";
	}
	// 문의 카테고리 생성 및 수정
	@PostMapping("/catagory/form/save")
	public String createQuestion(
			@RequestParam Map map,
			RedirectAttributes redirectAttributes) {
		if (map.get("questionId").equals("")) {
			questionService.createQuestion(map);
			redirectAttributes.addFlashAttribute("message", "문의내용이 등록되었습니다.");
		}
		else {
			questionService.updateQuestion(map);
			redirectAttributes.addFlashAttribute("message", "문의내용이 수정되었습니다.");
		}
		return "redirect:/admin/question/category";
	}

	
	// 문의 카테고리 수정 폼 이동 or 삭제
	@PostMapping("/catagory/process")
	public String process(
			@RequestParam Map map, 
			@RequestParam List<String> target, 
			RedirectAttributes redirectAttributes) throws IOException {
		String action = map.get("action").toString();
		if ("edit".equals(action)) {
			redirectAttributes.addAttribute("questionId", map.get("target").toString());
			return "redirect:/admin/question/category/form";
		} else {
			map.put("target", target);
			questionService.deleteMultpleQuestion(map);
			redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");
			return "redirect:/admin/question/category";
		}
	}	
}


