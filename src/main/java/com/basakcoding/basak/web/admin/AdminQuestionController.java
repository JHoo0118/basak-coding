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
		List<QuestionDTO> listQuestions = questionService.questionSelect();
		model.addAttribute("listQuestions", listQuestions);
		model.addAttribute("title", "질문 관리");
		return "admin/questionManagement";
	}//likeList
	
//	//강의 질문 전환
//	@GetMapping("/management/change")
//	public String likeChange(@RequestParam Map map,Model model) {
//		if(map.get("action").equals("lec")) {
//		List<LikeDTO> listLikes = likeService.courseSelect();
//		model.addAttribute("listLikes", listLikes);
//		model.addAttribute("title", "강의 좋아요");
//		return "admin/questionManagement";
//		}	
//		else {
//			List<LikeDTO> listLikes = likeService.questionSelect();
//			model.addAttribute("listLikes", listLikes);
//			model.addAttribute("title", "질문 좋아요");
//			return "admin/questionManagement";
//		}
//	}//likeChange

}
