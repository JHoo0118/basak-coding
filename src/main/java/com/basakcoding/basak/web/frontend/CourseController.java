package com.basakcoding.basak.web.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basakcoding.basak.frontend.security.oauth.MemberOAuth2User;
import com.basakcoding.basak.service.CourseService;
import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FileDTO;
import com.basakcoding.basak.service.InquiryService;
import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.service.MyCommentDTO;
import com.basakcoding.basak.service.QuestionDTO;
import com.basakcoding.basak.service.VideoDTO;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
public class CourseController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	InquiryService inquiryService;
	
	@Autowired
    ResourceLoader resourceLoader;
	@GetMapping("/class/{videoId}")
	public String course(@PathVariable String videoId, Model model, HttpServletRequest req, Authentication auth) throws IOException {
//		for (int i = 0; i < videoId.length(); i++) {
//			if ('0' <= videoId.charAt(i) && '9' >= videoId.charAt(i)) {
//				continue;
//			}
//		}
		String courseId = courseService.getCourseId(videoId);
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
			memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}

		Map paymentCheck = new HashMap();
		paymentCheck.put("courseId", courseId);
		paymentCheck.put("memberId", memberId);
		int count = courseService.alreadyPayment(paymentCheck);
		if (count < 1) {
			return "redirect:/personal/dashboard";
		}
		VideoDTO video = courseService.getVideo(videoId);
		video.setCourseId(courseId);
		
		// ??????????????? ????????? ??????
		List<CurriculumDTO> curriculumList = courseService.getCurriculumList(courseId);
		Map params = new HashMap();
		params.put("memberId", memberId);
		for (int i=0; i<curriculumList.size(); i++) {
			for (VideoDTO v : curriculumList.get(i).getVideos()) {
				params.put("videoId", v.getVideoId());
				int videoLength = Integer.parseInt(v.getVideoLength());
				int mins = videoLength/60;
				int secs = videoLength%60;
				String timeFormat = "" + (mins < 10 ? "0" + mins : mins) + ":" + (secs < 10 ? "0" + secs : secs);
				v.setVideoLength(timeFormat);
				int result = courseService.isSeen(params);
				if (result == 1) v.setSeen('Y');
				else v.setSeen('N');
			}
		}
		// ????????? ????????? ????????????
		List<String> cssFileList = new ArrayList<String>();
		List<Map> fileList = courseService.getFileList(videoId);
		for (int i=0; i<fileList.size(); i++) {
			String filename = fileList.get(i).get("FILENAME").toString();
			String fileExtension = filename.substring(filename.lastIndexOf(".")+1, filename.length());
			fileList.get(i).put("FILE_EXTENSION", fileExtension);

			if (fileExtension.equals("css")) {
				cssFileList.add("/upload/course/" + courseId + "/file/copy-" + memberId + "-" + filename);
			}
			// courseId ??????????????? input hidden??????
			
			if (i == 0) {
				String dirName = "upload/course/" + courseId + "/file/copy-" + memberId + "-" + filename;
				Path dir = Paths.get(dirName);
				String path = dir.toFile().getAbsolutePath();
				File file = new File(path);
				StringBuilder sb = new StringBuilder();
				
				if (file.exists()) {
					String data;
					BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
					while ((data=br.readLine()) != null) {
						sb.append(data);
						sb.append("\r\n");
					}
					br.close();
				} else {
					System.out.println("?????? ??????");
				}
				
//				if (fileExtension.equals("html"))
					fileList.get(0).put("INITIAL_CODE", sb.toString());
			}

			// fileList.get(i).put("FILE_CONTENT", sb.toString());
		}
		if (!fileList.get(0).containsKey("INITIAL_CODE")) {
			fileList.get(0).put("INITIAL_CODE", "abc");
		}
		
		//????????? ???????????????
		List<QuestionDTO> questionList = courseService.questionList(courseId);
		//System.out.println("qestionList:"+questionList);
		for(QuestionDTO questionDate : questionList) {
			
			questionDate.setMemberPath(questionDate.getMemberAvatarImagePath());
			
			/*
			
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			Calendar baseTime = new GregorianCalendar();
			baseTime.setTime(today);
			Calendar targetTime = new GregorianCalendar();
			targetTime.setTime(questionDate.getCreatedAt());
			
			long diffSec = (baseTime.getTimeInMillis()-targetTime.getTimeInMillis())/1000;
			long diffHours= diffSec/ (60*60); //  60???*60??? ????????? ??????
			long diffDays = diffSec/ (24*60*60);// 24??????*60???*60??? ????????? ???
			//System.out.println(date.format(today));
			
			questionDate.setUpdateDays(diffDays);
			questionDate.setUpdateHours(diffHours);
			*/
		}
		
		//System.out.println("questionList:"+questionList);
		
			
		model.addAttribute("courseId",courseId);
		model.addAttribute("questionList", questionList);
		model.addAttribute("cssFileList", cssFileList);
		model.addAttribute("fileList", fileList);
		model.addAttribute("currVideo", video);
		model.addAttribute("curriculums", curriculumList);
		return "frontend/course";
	}
	
	@PostMapping("/class/initial-code")
	@ResponseBody
	public void initializeCode(@RequestParam Map<String, String> map, Authentication auth) throws FileNotFoundException, IOException {
		String videoId = map.get("videoId");
		String filename = map.get("filename");
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
			memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		String courseId = courseService.getCourseId(videoId);
		
		// List<Map> fileList = courseService.getFileList(videoId);
		
		String copyFileDirName = "upload/course/" + courseId + "/file/copy-" + memberId + "-" + filename;
		String originFileDirName =  "upload/course/" + courseId + "/file/" + filename;
		
		Path copyFileDir = Paths.get(copyFileDirName);
		Path originFileDir = Paths.get(originFileDirName);
		
		String copyFilePath = copyFileDir.toFile().getAbsolutePath();
		String originFilePath = originFileDir.toFile().getAbsolutePath();
		
		File copyFile = new File(copyFilePath);
		File originFile = new File(originFilePath);
		
		// ?????? ?????? ?????? ?????????
		new FileOutputStream(copyFile).close();
		
		BufferedReader br = new BufferedReader(new FileReader(originFile.getAbsolutePath()));
		BufferedWriter bw = new BufferedWriter(new FileWriter(copyFile.getAbsolutePath()));
		
		String data;
		StringBuilder sb = new StringBuilder();
		while ((data=br.readLine()) != null) {
			bw.write(data);
			bw.newLine();
			sb.append(data);
			sb.append("\r\n");
			bw.flush();
		}
		bw.close();
		br.close();
	}
	
	@PostMapping("/class/autosave-code")
	@ResponseBody
	public String autoSave(@RequestParam Map<String, String> map, Authentication auth) throws IOException {
		String courseId = courseService.getCourseId(map.get("videoId"));
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
			memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		
		String currFilename = map.get("currFilename");
		String nextFilename = map.get("nextFilename");

		String currFileDirName = "upload/course/" + courseId + "/file/copy-" + memberId + "-" + currFilename;
		String nextFileDirName = "upload/course/" + courseId + "/file/copy-" + memberId + "-" + nextFilename;
		
		Path currFileDir = Paths.get(currFileDirName);
		Path nextFileDir = Paths.get(nextFileDirName);
		
		String currFilePath = currFileDir.toFile().getAbsolutePath();
		String nextFilePath = nextFileDir.toFile().getAbsolutePath();
		
		File currFile = new File(currFilePath);
		File nextFile = new File(nextFilePath);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(currFile.getAbsolutePath()));
		bw.write(map.get("currFileContent"));
		
		StringBuilder nextFileContent = new StringBuilder();
		
		String data;
		BufferedReader br = new BufferedReader(new FileReader(nextFile.getAbsolutePath()));
		while ((data=br.readLine()) != null) {
			nextFileContent.append(data);
			nextFileContent.append("\r\n");
		}
		
		br.close();
		bw.close();
		return nextFileContent.toString();
	}
	
	@PostMapping("/class/save-code")
	@ResponseBody
	public void saveCode(@RequestParam Map<String, String> map, Authentication auth) throws IOException {
		String courseId = courseService.getCourseId(map.get("videoId"));
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
			memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		
		String currFilename = map.get("currFilename");
		String currFileDirName = "upload/course/" + courseId + "/file/copy-" + memberId + "-" + currFilename;
	
		Path currFileDir = Paths.get(currFileDirName);
		String currFilePath = currFileDir.toFile().getAbsolutePath();
		File currFile = new File(currFilePath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(currFile.getAbsolutePath()));
		bw.write(map.get("currFileContent"));
		bw.close();
	}
	
	@PostMapping("/class/update-seen")
	@ResponseBody
	public String saveCode(@RequestParam String videoId, Authentication auth) {
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
			memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		Map params = new HashMap();
		params.put("memberId", memberId);
		params.put("videoId", videoId);
		int result = courseService.updateSeen(params);
		return Integer.toString(result);
	}

	//????????????
	@PostMapping("/class/inquiry")
	@ResponseBody
	public String inquiryProcess(@RequestParam Map map, Authentication auth) {
		String memberId = null;
		if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
			MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
			memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		} else {
			memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		map.put("memberId", memberId);
		int result = inquiryService.insertInquiry(map);
		return Integer.toString(result);
		
	}


	//?????? ????????????
    @PostMapping("/newQuestion.do")
    @ResponseBody
    public Map newQuestion(@RequestParam Map map,Authentication auth) {
       String memberId = null;
       if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
          MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
          memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
       } else {
          memberId = ((UserDetails) auth.getPrincipal()).getUsername();
       }
       map.put("memberId", memberId);
       int result= courseService.newQuestion(map);
       //????????? ???????????????
       String courseId = map.get("courseId").toString();
       List<QuestionDTO> questionList = courseService.questionList(courseId);
       for (QuestionDTO dto : questionList) {
          dto.setMemberPath(dto.getMemberAvatarImagePath());
     }
       map.put("questionLists", questionList);
       map.put("result", result);
       return map;
    }
	 //?????? ????????????
	   @PostMapping("/newComment.do")
	   @ResponseBody
	   public Map newComment(@RequestParam Map map,Authentication auth) {
	      String memberId = null;
	      if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
	         MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
	         memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	      } else {
	         memberId = ((UserDetails) auth.getPrincipal()).getUsername();
	      }
	      map.put("memberId", memberId);
	      
	      String path;
	      String questionId = map.get("questionId").toString();
	      
	      //???????????? ?????? ??????
	      int commentCount = courseService.commentCount(questionId);
	      
	      
	      // ??????
          
       
	      MyCommentDTO myComment = null;
	      if (commentCount==1 ) {
	    	  int result= courseService.newComment(map);
	    	  myComment = courseService.getComment(map);
	    	  map.put("result", result);
	    	  
	      }
	      
	       
	      	myComment.setAdminPath(myComment.getAdminAvatarImagePath());
            path=myComment.getMemberAvatarImagePath();
            myComment.setMemberPath(path);
	         
            map.put("myComment", myComment);
	         
	      
	      
	      
	      
	      //???????????? ????????? ??????
	      String totalCommentCount= courseService.totalCommentCount(questionId);
	      map.put("totalCommentCount", totalCommentCount);
	      
	      System.out.println("?????? map:"+map);
	      
	      return map;
	   }
	   
	   //?????? ???????????? ??? ??? ????????????
	   @PostMapping("/clickUpdate.do")
	   @ResponseBody
	   public QuestionDTO clickUpdate(@RequestParam Map map,Authentication auth) {
	      String memberId = null;
	      if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
	         MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
	         memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	      } else {
	         memberId = ((UserDetails) auth.getPrincipal()).getUsername();
	      }
	      map.put("memberId", memberId);
	      //System.out.println("???????????? ????????? map??? ?????????:"+map);
	      
	      String questionId = map.get("questionId").toString();
	      //DTO??? ???????????? question???????????? CONTENT??? ?????????????????? ?????? ?????? ????????? String????????? ???????????? ??????
	      QuestionDTO ex_Result= (QuestionDTO) courseService.clickUpdate(questionId);
	     // System.out.println("???????????? ????????? map??? ?????????:"+ex_Result);
	      
	      
	      return  ex_Result;
	  
	   }
	  
	   
	   //?????? ????????????
	   @PostMapping("/updateQuestion.do")
	   @ResponseBody
	   public int updateQuestion(@RequestParam Map map,Authentication auth) {
	      String memberId = null;
	      if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
	         MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
	         memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	      } else {
	         memberId = ((UserDetails) auth.getPrincipal()).getUsername();
	      }
	      map.put("memberId", memberId);
	      
	      System.out.println("???????????? map??? ?????????:"+map);
	      int updateResult= courseService.updateQuestion(map);
	      return  updateResult;
	  
	   }
	  
	   
	   
	   
	 //?????? ???????????? 
	      @PostMapping("/course/questionSelect")
	      @ResponseBody
	      public Map questionSelect(@RequestBody Map map, Authentication auth,Model model) {
	        
	        String memberId = null;
	         if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
	            MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
	            memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	         } else {
	            memberId = ((UserDetails) auth.getPrincipal()).getUsername();
	         }
	         map.put("memberId", memberId);
	         String questionId = map.get("questionId").toString();
	         
	         String path;
	         // ??????
	         Map questionDetails = courseService.questionDetails(questionId);
	         
	         String avatar = null;
	         if (questionDetails.containsKey("AVATAR")) {
		         avatar = questionDetails.get("AVATAR").toString();
	         }
	        
	        
	         String memberPath=null;
	         if(avatar==null) {
	            memberPath="/images/image4.jpg";
	         }
	         else memberPath ="/upload/member/" + memberId + "/" + avatar;
	         questionDetails.put("memberPath", memberPath);
	       
	         
	         //?????? ????????? ????????? ????????? ??????
	         int likeCheck = courseService.likeCheck(map);
	         questionDetails.put("likeCheck", likeCheck);
	         model.addAttribute("likeCheck",likeCheck);
	       
	         // ??????
	         List<MyCommentDTO> commentLists = memberService.commentList(questionId);
	        
	         for (MyCommentDTO dto : commentLists) {
	            dto.setAdminPath(dto.getAdminAvatarImagePath());
	            path=dto.getMemberAvatarImagePath();
	            dto.setMemberPath(path);
	         }
	         questionDetails.put("commentList", commentLists);
	         
	         return questionDetails;
	           
	      }

	 //????????? ????????????
		@PostMapping("/course/count_like")
		public @ResponseBody int course(Authentication auth , @RequestBody Map map) throws IOException {
			
		      String memberId = null;
		      if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		         MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		         memberId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
		      } else {
		         memberId = ((UserDetails) auth.getPrincipal()).getUsername();
		      }
		      map.put("memberId", memberId);
		     // String questionId = map.get("questionId").toString();
		     //map.put("questionId", questionId); 		      
		      
		      System.out.println("????????? map??? ?????????:"+map);
		      
			
			int likeCheck;
			int affected;
			
			likeCheck = courseService.likeCheck(map);
			
			if(likeCheck == 0) {//????????? ????????? ???????????????
				courseService.like(map);
				courseService.likeCount(map);
				 
				return  affected=0;
			
			}//?????? if
			else {//likeCheck ?????? ?????? 1?????? (?????? ???????????? ????????? ????????????)
				courseService.unLike(map);
				courseService.likeCount(map);
				
				return  affected=1;
			
			}//?????? else
	
		}///courseLike
		
}
