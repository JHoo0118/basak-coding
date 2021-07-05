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
import com.basakcoding.basak.service.InquiryDTO;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.PriceRoleService;
import com.basakcoding.basak.service.PriceService;
import com.basakcoding.basak.service.ReviewDTO;
import com.basakcoding.basak.service.ReviewService;
import com.basakcoding.basak.service.RoleDTO;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/review")
public class AdminReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/management")
	public String reviewList(Model model) {
		List<Map> listReviews = reviewService.AllReviewList();
		model.addAttribute("listReviews", listReviews);
		model.addAttribute("title", "후기 관리");
		System.out.println(listReviews);
		return "admin/reviewManagement";
	}
	
	
	//후기 상세보기 페이지
	@RequestMapping("/management/view")
	public String reviewForm(@RequestParam Map map, Model model) {
		ReviewDTO dto = reviewService.getReviewList(map);
		model.addAttribute("review", dto);
		model.addAttribute("title", "후기 관리");
		System.out.println("상세페이지:"+map);
		return "admin/reviewView";
	}
	
	
	
	

}
