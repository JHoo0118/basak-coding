package com.basakcoding.basak.android;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.basakcoding.basak.service.android.AndroidMemberService;
import com.basakcoding.basak.util.FileUploadUtil;

@RestController
@RequestMapping("/api/1.0")
public class AndroidMemberController {
	
	@Autowired
	AndroidMemberService androidMemberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//마이페이지 정보 뿌려주기
	@GetMapping("/mypage")
	public Map getMyPage(@Param("memberId") String memberId) {
		Map MyPage = new HashMap();
		MyPage = androidMemberService.getMyPage(memberId);
		Map params = new HashMap();
		params.put("memberId", memberId);
		return MyPage;
	}
	
	//마이페이지 회원정보 수정
	@CrossOrigin
	@PostMapping("/updateMember")
	public Map updateMember(@RequestParam Map map,
							@RequestParam(required = false, name = "image") MultipartFile image) throws IOException {
		
		if (image != null) {
			String filename = StringUtils.cleanPath(image.getOriginalFilename());
			String uploadDir = "upload/member/" + map.get("memberId").toString();
		    FileUploadUtil.cleanDir(uploadDir);
		    FileUploadUtil.saveFile(uploadDir, filename, image);
		    map.put("avatar", filename);
		}
		androidMemberService.updateMem(map);
		return map;
   }

	
}
