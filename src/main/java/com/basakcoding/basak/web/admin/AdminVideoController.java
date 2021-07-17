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

import com.basakcoding.basak.service.VideoDTO;
import com.basakcoding.basak.service.VideoService;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/video")
public class AdminVideoController {

	@Autowired
	VideoService videoService;
	
	// 비디오 관리 화면으로 이동
	@GetMapping("/management")
	public String videoList(Model model) {
		List<VideoDTO> listVideos = videoService.videoSelect();
		model.addAttribute("listVideos", listVideos);
		model.addAttribute("title", "강의 관리");
		return "admin/videoManagement";
	}//videoList
	
	
}//class
