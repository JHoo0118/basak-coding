package com.basakcoding.basak.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/management")
	public String categoryList(Model model) {
		List<CategoryDTO> listCategories = categoryService.selectList();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("title", "카테고리 관리");
		return "admin/categoryManagement";
	}
}
