package com.basakcoding.basak.web.admin;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.basakcoding.basak.service.AdminDTO;
import com.basakcoding.basak.service.AdminRoleService;
import com.basakcoding.basak.service.AdminService;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.PriceRoleService;
import com.basakcoding.basak.service.PriceService;
import com.basakcoding.basak.service.RoleDTO;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/price")
public class AdminPriceController {

	@Autowired
	private PriceService priceService;
	
	//@Autowired
	//private PriceRoleService priceRoleService;
	
	@GetMapping("/management")
	public String priceList(Model model) {
		List<Map> listPrices = priceService.getPriceList();
		model.addAttribute("listPrices", listPrices);
		model.addAttribute("title", "결제 관리");
		return "admin/priceManagement";
	}
	
	/*
	// 결제 관리 폼 페이지
	@GetMapping("/management/form")
	public String memberForm(@RequestParam Map<String, String> map, Model model) {
		String adminId = map.get("adminId");
		AdminDTO admin = new AdminDTO();
		if (adminId != null) {
			admin = priceService.getAdminById(adminId);
		}
		List<RoleDTO> priceRoles = priceRoleService.selectList();
		model.addAttribute("priceRoles", priceRoles);
		model.addAttribute("admin", admin);
		model.addAttribute("title", "결제 관리");
		return "admin/priceForm";
	}
	*/
	
	

}
