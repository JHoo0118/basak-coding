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
import com.basakcoding.basak.service.CategoryService;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/like")
public class AdminLikeController {

	@Autowired
	CategoryService categoryService;
	
	// 카테고리 관리 화면으로 이동
	@GetMapping("/management")
	public String likeList(Model model) {
//		List<CategoryDTO> listCategories = categoryService.selectList();
//		model.addAttribute("listCategories", listCategories);
//		model.addAttribute("title", "카테고리 관리");
		return "admin/likeManagement";
	}
	
//	// 카테고리 생성 및 수정 폼으로 이동
//	@GetMapping("/management/form")
//	public String categoryForm(@RequestParam Map<String, String> map, Model model) {
//		String categoryId = map.get("categoryId");
//		CategoryDTO category = new CategoryDTO();
//		if (categoryId != null) {
//			category = categoryService.getCategoryById(categoryId);
//		}
//		model.addAttribute("category", category);
//		model.addAttribute("title", "카테고리 관리");
//		return "admin/categoryForm";
//	}
//	
//	// 카테고리 생성 및 수정
//	@PostMapping("/save")
//	public String createCategory(
//			@RequestParam Map map,
//			RedirectAttributes redirectAttributes) {
//		if (map.get("categoryId").equals("")) {
//			categoryService.createCategory(map);
//			redirectAttributes.addFlashAttribute("message", "카테고리가 저장되었습니다.");
//		}
//		else {
//			categoryService.updateCategory(map);
//			redirectAttributes.addFlashAttribute("message", "카테고리가 수정되었습니다.");
//		}
//		return "redirect:/admin/category/management";
//	}
//	
//	// 카테고리 수정 폼 이동 or 삭제
//	@PostMapping("/management/process")
//	public String process(
//			@RequestParam Map map, 
//			@RequestParam List<String> target, 
//			RedirectAttributes redirectAttributes) throws IOException {
//		String action = map.get("action").toString();
//		if ("edit".equals(action)) {
//			redirectAttributes.addAttribute("categoryId", map.get("target").toString());
//			return "redirect:/admin/category/management/form";
//		} else {
//			map.put("target", target);
//			categoryService.deleteMultpleCategory(map);
//			redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");
//			return "redirect:/admin/category/management";
//		}
//	}
}
