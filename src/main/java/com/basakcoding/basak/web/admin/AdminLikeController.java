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

import com.basakcoding.basak.service.LikeDTO;
import com.basakcoding.basak.service.LikeService;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/like")
public class AdminLikeController {

	@Autowired
	LikeService likeService;
	
	// 카테고리 관리 화면으로 이동
	@GetMapping("/management")
	public String likeList(Model model) {
		List<LikeDTO> listLikes = likeService.courseSelect();
		model.addAttribute("listLikes", listLikes);
		model.addAttribute("title", "좋아요 관리");
		return "admin/likeManagement";
	}//likeList
	
	//강의 질문 전환
	@GetMapping("/management/change")
	public String likeChange(@RequestParam Map map,Model model) {
		if(map.get("action").equals("lec")) {
		List<LikeDTO> listLikes = likeService.courseSelect();
		model.addAttribute("listLikes", listLikes);
		model.addAttribute("title", "좋아요 관리");
		return "admin/likeManagement";
		}	
		else {
			List<LikeDTO> listLikes = likeService.questionSelect();
			model.addAttribute("listLikes", listLikes);
			model.addAttribute("title", "좋아요 관리");
			return "admin/likeManagement";
		}
	}//likeChange

}
