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

import com.basakcoding.basak.service.FileDTO;
import com.basakcoding.basak.service.FileService;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/file")
public class AdminFileController {

	@Autowired
	FileService fileService;
	
	// 비디오 관리 화면으로 이동
	@GetMapping("/management")
	public String fileList(Model model) {
		List<FileDTO> listFiles = fileService.fileSelect();
		model.addAttribute("listFiles", listFiles);
		model.addAttribute("title", "파일 관리");
		return "admin/fileManagement";
	}//videoList
	
	
}//class
