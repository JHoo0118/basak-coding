package com.basakcoding.basak.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {
	
	@GetMapping("/catalog")
	public String catalog() {
		return "/frontend/catalog";
	}
	
	@GetMapping("/catalog/{id}")
	public String catalogDetail() {
		return "/frontend/catalogDetail";
	}
}
