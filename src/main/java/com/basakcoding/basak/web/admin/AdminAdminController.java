package com.basakcoding.basak.web.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.basakcoding.basak.service.AdminDTO;
import com.basakcoding.basak.service.AdminRoleService;
import com.basakcoding.basak.service.AdminService;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.RoleDTO;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	@GetMapping("/management")
	public String memberList(Model model) {
		List<AdminDTO> listAdmins = adminService.getAdminList();
		model.addAttribute("listAdmins", listAdmins);
		model.addAttribute("title", "강사 관리");
		return "admin/adminManagement";
	}
	
	// 사용자 관리 폼 페이지
	@GetMapping("/management/form")
	public String memberForm(@RequestParam Map<String, String> map, Model model) {
		String adminId = map.get("adminId");
		AdminDTO admin = new AdminDTO();
		if (adminId != null) {
			admin = adminService.getAdminById(adminId);
		}
		List<RoleDTO> listRoles = adminRoleService.selectList();
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("admin", admin);
		model.addAttribute("title", "강사 관리");
		return "admin/adminForm";
	}
	
	// 사용자 생성 및 업데이트
		@PostMapping("/save")
		public String createAdmin(
				@RequestParam Map map, 
				@RequestParam("avatar") MultipartFile multipartFile,
				RedirectAttributes redirectAttributes) throws IOException {
			// 프로필 사진을 선택했을 때
			if (!multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				map.put("avatar", fileName);
				if (map.get("adminId").equals("")) {
					int result = adminService.createAdminByMap(map);
					if (result == 0) {
						redirectAttributes.addFlashAttribute("message", "관리자 생성에 실패하였습니다.");
						return "redirect:/admin/admin/management";
					}
					adminRoleService.insertRelation(map);
				}
//				else
//					adminService.updateAdmin(map);
					
				String uploadDir = "upload/admin/" + map.get("adminId").toString();
				FileUploadUtil.cleanDir(uploadDir);
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			// 프로필 사진을 선택 안 했을 때
			} else {
				int result = adminService.createAdminByMap(map);
				if (result == 0) {
					redirectAttributes.addFlashAttribute("message", "관리자 생성에 실패하였습니다.");
					return "redirect:/admin/admin/management";
				}
				adminRoleService.insertRelation(map);
//				else
//					adminService.updateAdmin(map);
			}
			redirectAttributes.addFlashAttribute("message", "관리자가가 저장되었습니다.");
			return "redirect:/admin/admin/management";
		}
}
