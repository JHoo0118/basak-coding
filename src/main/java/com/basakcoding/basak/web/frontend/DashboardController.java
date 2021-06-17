package com.basakcoding.basak.web.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.basakcoding.basak.service.MemberService;

@Controller
public class DashboardController {
	
	@Autowired
	private MemberService memberService;

	@RequestMapping("/dashBoard/profileImgUpdate")
	public @ResponseBody int profileImgUpdate(@RequestParam int userId,@RequestParam MultipartFile file) throws IllegalStateException, IOException {
		URL url = this.getClass().getClassLoader().getResource("static/upload");
		String _path = url.getPath();
		//String path = Paths.get("").toFile().getAbsolutePath();
	    //  System.out.println(path);
		//String physicalPath =req.getServletContext().getRealPath("static/upload");
		//System.out.println("req로 경로:"+physicalPath);
		System.out.println(_path);
		System.out.println("파일명:"+file.getOriginalFilename());
		String file_name=file.getOriginalFilename();
		//System.out.println("아이디:"+userId);
		//File객체 생성
		File dest = new File(_path+File.separator+file_name);
		//File dest = new File(physicalPath+File.separator+file.getOriginalFilename());
		String file_image =dest.getName();
	   	int file_kind= file_image.lastIndexOf(".");
	   	String file_type= file_image.substring(file_kind+1,file_image.length());
	   	String[] check_file_type ={"jpg","gif","png","jpeg","bmp","tif"};
	   	
		Map map = new HashMap();
		map.put("userId",userId);
		map.put("file_name", file_name);
		//업로드 처리
		file.transferTo(dest);
		//DB에 업데이트
		int success;
		success =memberService.fileUpdate(map);
		System.out.println("success:"+success);
		if(!Arrays.asList(check_file_type).contains(file_type)){
			success= -1;
			dest.delete();
			}
	   	  else if(dest.length() > 1024 *1024) {
			success= -2;
			dest.delete();
			}
	   	  else if(success ==0) {
			dest.delete();
		}
		return success;
	}
}
