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

import com.basakcoding.basak.service.CommentDTO;
import com.basakcoding.basak.service.CommentService;


@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {

	@Autowired
	CommentService commentService;
	
	//댓글 관리 화면으로 이동
	@GetMapping("/management")
	public String commentList(Model model) {
		List<CommentDTO> listComments = commentService.adminSelect();
		System.out.println(listComments.size());
		model.addAttribute("listComments", listComments);
		model.addAttribute("title", "댓글 관리");
		return "admin/commentManagement";
	}//commentList
	
	//강의 질문 전환
	@GetMapping("/management/change")
	public String commentChange(@RequestParam Map map,Model model) {
		if(map.get("action").equals("manage")) {
		List<CommentDTO> listComments = commentService.adminSelect();
		model.addAttribute("listComments", listComments);
		model.addAttribute("title", "댓글 관리");
		return "admin/commentManagement";
		}	
		else {
			List<CommentDTO> listComments = commentService.memberSelect();
			model.addAttribute("listComments", listComments);
			model.addAttribute("title", "댓글 관리");
			return "admin/commentManagement";
		}
	}//commentList

}
