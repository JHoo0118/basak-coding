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

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

   @Autowired
   private MemberService memberService;
   
   // 사용자 관리 페이지
   @GetMapping("/management")
   public String memberList(Model model) {
      List<MemberDTO> listMembers = memberService.selectList();
      model.addAttribute("listMembers", listMembers);
      model.addAttribute("title", "사용자 관리");
      return "admin/memberManagement";
   }
   
   // 사용자 관리 폼 페이지
   @GetMapping("/management/form")
   public String memberForm(@RequestParam Map<String, String> map, Model model) {
      String memberId = map.get("memberId");
      MemberDTO member = new MemberDTO();
      if (memberId != null) {
         member = memberService.getMemberById(memberId);
      }
      model.addAttribute("member", member);
      model.addAttribute("title", "사용자 관리");
      return "admin/memberForm";
   }
   
   // 사용자 생성 및 업데이트
   @PostMapping("/save")
   public String createMember(
         @RequestParam Map map, 
         @RequestParam("avatar") MultipartFile multipartFile,
         RedirectAttributes redirectAttributes) throws IOException {
      // 프로필 사진을 선택했을 때
      if (!multipartFile.isEmpty()) {
         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
         map.put("avatar", fileName);
         if (map.get("memberId").equals(""))
            memberService.createMember(map);
         else
            memberService.updateMember(map);
            
         String uploadDir = "upload/member/" + map.get("memberId").toString();
         FileUploadUtil.cleanDir(uploadDir);
         FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
      // 프로필 사진을 선택 안 했을 때
      } else {
         if (map.get("memberId").equals(""))
            memberService.createMember(map);
         else
            memberService.updateMember(map);
      }
      redirectAttributes.addFlashAttribute("message", "사용자가 저장되었습니다.");
      return "redirect:/admin/member/management";
   }
   
  
   
   
   // 사용자 수정 폼 이동 or 삭제
   @PostMapping("/management/process")
   public String process(@RequestParam Map map, @RequestParam List<String> target, RedirectAttributes redirectAttributes) throws IOException {
      String action = map.get("action").toString();
      if ("edit".equals(action)) {
         redirectAttributes.addAttribute("memberId", map.get("target").toString());
         return "redirect:/admin/member/management/form";
      } else {
         map.put("target", target);
         memberService.deleteMultpleMember(map);
         for (String memberId : target) {
            String dir = "upload/member/" + memberId;
            FileUploadUtil.deleteDir(dir);   
         }
         redirectAttributes.addFlashAttribute("message", "사용자가 삭제되었습니다.");
         return "redirect:/admin/member/management";
      }
   }
}


