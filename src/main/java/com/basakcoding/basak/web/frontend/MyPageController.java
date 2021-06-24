package com.basakcoding.basak.web.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.basakcoding.basak.service.MemberService;

@Controller
@RequestMapping("/personal")
public class MyPageController {
	@Autowired
	private MemberService memberService;
	//임시 유저아이디
	int userId=1;
	public Model userInfo(Model model) {
		//내결제정보
		int paymentCount = memberService.paymentCount(userId);//로그인된 회원아이디 받아와서넣어줘야함
		model.addAttribute("paymentCount", paymentCount);
		//뿌려줄 내정보 가져오기
		Map map=memberService.selectMyInfo(userId);//로그인된 회원아이디 받아와서넣어줘야함
		if (!map.containsKey("AVATAR"))
			map.put("AVATAR", null);
		
		model.addAttribute("member",map);
		//내댓글 개수
		int commentsCount = memberService.commentsCount(userId);
		model.addAttribute("commentsCount",commentsCount);
		//내 질문 개수
		int questionCount = memberService.questionCount(userId);
		model.addAttribute("questionCount",questionCount);
		
		return model;
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		//임시 유저아이디
		userId=1;
		userInfo(model);
		System.out.println(model);
		return "frontend/dashboard";
	}
	

	@GetMapping("/profile")
	public String profile(Model model) {
		//임시 유저아이디
		int userId=1;
		userInfo(model);
		return "frontend/profile";
	}
	
	@GetMapping("/qAndA/questions")
	public String qAndA(Model model) {
		//임시 유저아이디
		int userId=1;
		userInfo(model);
				
		return "frontend/qAndA";
	}

	@RequestMapping("/dashBoard/profileImgUpdate")
	public @ResponseBody int profileImgUpdate(@RequestParam int userId,@RequestParam String imgname,@RequestParam MultipartFile file) throws IllegalStateException, IOException {
		URL url = this.getClass().getClassLoader().getResource("static/upload");
		String _path = url.getPath();
		//File f = new File(_path+"/upload");
		//if (!f.exists()) f.mkdir();
		//_path += "/upload";
	
//		File f = new File(_path);
//		if (!f.exists()) f.mkdir();
		//String path = Paths.get("").toFile().getAbsolutePath();
	    //  System.out.println(path);
		//String physicalPath =req.getServletContext().getRealPath("static/upload");
		//System.out.println("req로 경로:"+physicalPath);
		//System.out.println(_path);
		//System.out.println("파일명:"+file.getOriginalFilename());
		String file_name=file.getOriginalFilename();
		//System.out.println("아이디:"+userId);
		Map map = new HashMap();
		map=memberService.selectMyInfo(userId);
		//File객체 생성
		File dest = new File(_path+File.separator+file_name);
		File orgfile=null;
		if(map.get("AVATAR")!=null) {
			orgfile = new File(_path+File.separator+(map.get("AVATAR")));//원래잇던 이미지 삭제용
		}
		//File dest = new File(physicalPath+File.separator+file.getOriginalFilename());
		String file_image =dest.getName();
	   	int file_kind= file_image.lastIndexOf(".");
	   	String file_type= file_image.substring(file_kind+1,file_image.length());
	   	String[] check_file_type ={"jpg","gif","png","jpeg","bmp","tif"};
		
		map.put("userId",userId);
		map.put("file_name", file_name);
		//업로드 처리
		file.transferTo(dest);
		//DB에 업데이트
		int success;
		success =memberService.fileUpdate(map);
		//System.out.println("success:"+success);
		if(!Arrays.asList(check_file_type).contains(file_type)){ //이미지파일이 아닌경우
			success= -1;
			dest.delete();
			}
	   	  else if(dest.length() > 1024 *1024) { //용량이 너무클경우
			success= -2;
			dest.delete();
			}
	   	  else if(success ==0) {//DB오류
			dest.delete();
		}
	   	  else if(success ==1) { //성공했을때 원래있던 이미지 삭제처리
	   		  if(orgfile !=null) 
	   			  orgfile.delete();
	   	  }
		return success;
	}

	@RequestMapping("/userprofile/edit.do")
	public @ResponseBody int userNameEdit(@RequestBody Map map) {
		String username = (String)map.get("username");
		int userId = Integer.parseInt((String) map.get("userId"));
		if(username.length()>=11) { //11글자이상입력했을경우
			return -1;
		}
		map.put("username", username);
		map.put("userId", userId);
		int success;
		success = memberService.userNameEdit(map);
		
		return success;
	}
}
