package com.basakcoding.basak.web.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.basakcoding.basak.frontend.security.oauth.MemberOAuth2User;
import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.service.MyCommentDTO;
import com.basakcoding.basak.util.FileUploadUtil;
import com.basakcoding.basak.util.ListPagingData;

@Controller
@RequestMapping("/personal")
public class MyPageController {
   @Autowired
   private MemberService memberService;

   @Autowired
   PasswordEncoder passwordEncoder;

   @RequestMapping("/password/edit.do")
   public @ResponseBody boolean passwordEdit(@RequestBody Map map, Authentication auth) {
      boolean booleanpass = passwordEncoder.matches((map.get("pass").toString()),
            ((UserDetails) auth.getPrincipal()).getPassword());
      return booleanpass;
   }

   public Model userInfo(Model model, Authentication auth) {
	  int userId = -1;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId();
	  } else {
	      userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
	  }
      // 내결제정보
      int paymentCount = memberService.paymentCount(userId);
      model.addAttribute("paymentCount", paymentCount);
      // 뿌려줄 내정보 가져오기
      Map map = memberService.selectMyInfo(userId);
      if (!map.containsKey("AVATAR"))
         map.put("AVATAR", null);
      else {
         model.addAttribute("PATH", "/upload/member/" + userId + "/" + map.get("AVATAR"));
      }
      model.addAttribute("member", map);
      model.addAttribute("userId", userId);
      // 내댓글 개수
      int commentsCount = memberService.commentsCount(userId);
      model.addAttribute("commentsCount", commentsCount);
      // 내 질문 개수
      int questionCount = memberService.questionCount(userId);
      model.addAttribute("questionCount", questionCount);
      // 파일 이미지 경로
      String uploadDir = "upload/member/" + userId;
      model.addAttribute("uploadDir", uploadDir);
      return model;
   }

   @GetMapping("/dashboard")
   public String dashboard(Model model, Authentication auth) {
      userInfo(model, auth);
      // 내강의
      int userId = (int) model.getAttribute("userId");
      List<Map> map = memberService.myCourses(userId);
      Map params = new HashMap();
      params.put("memberId", userId);
      for (Map map1 : map) {
         String courseId = map1.get("COURSE_ID").toString();
         params.put("courseId", courseId);
         // 다본 동영상 개수 가져오기
         int videoCount = memberService.videoCount(params);
         String thumbnail = ("/upload/course/" + courseId + "/thumbnail/" + map1.get("THUMBNAIL"));
         String courseVideoId = null;
         List<String> curriculumIds = memberService.getCurriculum(courseId);
         // 마지막 커리큘럼의 아이디
         String lastCurriculumId = curriculumIds.get(curriculumIds.size() - 1);
         // 마지막 비디오 아이디
         String lastVideoId = memberService.getLastVideo(lastCurriculumId);
         for (int i = 0; i < curriculumIds.size(); i++) {
            params.put("curriculumId", curriculumIds.get(i));
            courseVideoId = memberService.getVideo(params);
            if (courseVideoId != null)
               break;
         }
         if (courseVideoId != null) {
            map1.put("courseVideoid", courseVideoId);
         } else {
            // 마지막 동영상 아이디
            map1.put("courseVideoid", lastVideoId);
         }
         map1.put("progress",
               (int) (Math.ceil(videoCount * 100 / Double.parseDouble((map1.get("VIDEO_COUNT").toString())))));

         map1.put("thumbnail", thumbnail);
      }
      if (!map.isEmpty())
         model.addAttribute("myCourses", map);

      return "frontend/dashboard";
   }

   @GetMapping("/profile")
   public String profile(@RequestParam(required = false, defaultValue = "1") int nowPage,@RequestParam (required = false, defaultValue = "1")int where,Model model, Authentication auth) {
      userInfo(model, auth);
      if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
          MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
          model.addAttribute("clientName", oauth2User.getClientName());
      } else {
    	  model.addAttribute("clientName", "Email");
      }
      // 내결제
      int userId = (int) model.getAttribute("userId");
      ListPagingData listPayment = memberService.myPayment(userId,nowPage,where);
      if (listPayment!=null)
         model.addAttribute("myPayment", listPayment);
      return "frontend/profile";
   }

   @GetMapping("/qAndA/questions")
   public String qAndA(@RequestParam(required = false, defaultValue = "1") int nowPage,@RequestParam (required = false, defaultValue = "1")int where,Model model, Authentication auth) {
	  
	  userInfo(model, auth);
      int userId = (int) model.getAttribute("userId");
      // 내 질문
      ListPagingData listQuestion = memberService.myQuestion(userId,nowPage,where);
      if (listQuestion!=null)
         model.addAttribute("myQuestion", listQuestion);
      // 내 문의 제목,시간
      ListPagingData listInquiry= memberService.myInquiry(userId,nowPage,where);
      if (listInquiry!=null)
         model.addAttribute("myInquiry", listInquiry);
      // 내 댓글 제목,시간
      ListPagingData listComment = memberService.myComments(userId,nowPage,where);
      if (listComment!=null)
         model.addAttribute("myComments", listComment);

      return "frontend/qAndA";
   }

   // 내 프로필 이미지 변경
   @RequestMapping("/dashBoard/profileImgUpdate")
   public @ResponseBody int profileImgUpdate(@RequestParam String imgname, @RequestParam MultipartFile file,Authentication auth) throws IllegalStateException, IOException {
      String filename = StringUtils.cleanPath(file.getOriginalFilename());
      int userId = -1;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId();
	  } else {
	      userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
	  }
      String uploadDir = "upload/member/" + userId;
      FileUploadUtil.cleanDir(uploadDir);
      FileUploadUtil.saveFile(uploadDir, filename, file);
      Map map = new HashMap();
      int file_kind = filename.lastIndexOf(".");
      String file_type = filename.substring(file_kind + 1, filename.length());
      String[] check_file_type = { "jpg", "gif", "png", "jpeg", "bmp", "tif" };
      map.put("userId", userId);
      map.put("file_name", filename);
      // DB에 업데이트
      int success;
      success = memberService.fileUpdate(map);
      if (!Arrays.asList(check_file_type).contains(file_type)) { // 이미지파일이 아닌경우
         success = -1;
      } else if (file.getSize() > 1024 * 1024) { // 용량이 너무클경우
         success = -2;
      } else if (success == 0) {// DB오류
      }
      return success;
   }

   @RequestMapping("/userprofile/edit.do")
   public @ResponseBody int userNameEdit(@RequestBody Map map, Authentication auth) {
	  int userId = -1;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId();
	  } else {
	      userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
	  }
      String username = (String) map.get("username");
      if (username.length() >= 11) { // 11글자이상입력했을경우
         return -1;
      }
      map.put("username", username);
      map.put("userId", userId);
      int success;
      success = memberService.userNameEdit(map);

      return success;
   }

   // 비밀번호 변경
   @PostMapping("/newpass/edit.do")
   public String newPass(@RequestParam Map map, Model model) {
      map.put("password", map.get("newpass1"));
      int newpass = memberService.passwordEdit(map);
      model.addAttribute("passedit", newpass);
      return "forward:/logout";
   }

   // 로그아웃처리
   @Controller
   public class LogoutController {

      @RequestMapping(value = "/logout")
      public String logout(HttpServletRequest request, HttpServletResponse response) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
         }
         return "frontend/signin";
      }

   }//////////// LogoutController
   // 결제 상세보기 정보가져오기
   @RequestMapping(value = "/viewDetails.do", method = RequestMethod.POST)
   public @ResponseBody Map viewDetails(@RequestParam("payment_code") String payment_code, Model model,
         Authentication auth) {
	  String userId = null;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	  } else {
	      userId = ((UserDetails) auth.getPrincipal()).getUsername();
	  }
      String pay_code = payment_code;
      Map map = memberService.viewDetails(userId, pay_code);
      return map;
   }

   // 질문 상세보기
   @RequestMapping(value = "/questionDetails.do", method = RequestMethod.POST)
   public @ResponseBody Map questionDetails(@RequestParam("questionId") String questionId, Model model,
         Authentication auth) {
	  String userId = null;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	  } else {
	      userId = ((UserDetails) auth.getPrincipal()).getUsername();
	  }
      String path;
      // 질문
      Map map = memberService.questionDetails(userId, questionId);
      // 댓글
      List<MyCommentDTO> commentLists = memberService.commentList(questionId);
      for (MyCommentDTO dto : commentLists) {
         dto.setAdminPath(dto.getAdminAvatarImagePath());
         path=dto.getMemberAvatarImagePath();
         dto.setMemberPath(path);
      }
      map.put("commentList", commentLists);
      return map;
   }
   //질문 좋아요 
   @RequestMapping(value="/like.do", method = RequestMethod.POST)
   public @ResponseBody int like(@RequestParam("questionId")int questionId,@RequestParam("likeCount")int likeCount, Authentication auth) {
	  int userId = -1;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId();
	  } else {
	      userId = Integer.parseInt(((UserDetails) auth.getPrincipal()).getUsername());
	  }
      int affected;
      likeCount = memberService.likeCheck(userId,questionId);
      
      if(likeCount == 0) {//좋아요 등록이 안돼있으면
         memberService.like(userId,questionId);
         memberService.likeCount(questionId);
         return affected=0;
         
      }//작은 if
      else {//likeCheck 결과 값이 1일떄 (이미 좋아요가 등록이 돼있을때)
         memberService.unLike(userId,questionId);
         memberService.likeCount(questionId);
         return affected=1;         
      }//작은 else
   }

   // 문의 상세보기
   @RequestMapping(value = "/inquDetails.do", method = RequestMethod.POST)
   public @ResponseBody Map inquDetails(@RequestParam("inquiry_id") String inquiry_id, Model model,
         Authentication auth) {
	  String userId = null;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	  } else {
	      userId = ((UserDetails) auth.getPrincipal()).getUsername();
	  }
      Map map = memberService.inquDetails(userId, inquiry_id);
      if (map == null) {
         map = memberService.inquDetailNotExist(userId, inquiry_id);
      } else {
         if (!map.containsKey("ANSWER_ID"))
            map.put("ANSWER_ID", null);
         else
            model.addAttribute("admin_path", "/upload/admin/" + map.get("ADMIN_ID") + "/" + map.get("AVATAR"));

      }
      return map;
   }
   //내 질문 수정
   @RequestMapping("/questionEdit.do")
   public @ResponseBody int questionEdit(@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("questionId")String questionId){
	   questionId.toString();
	   int succese;
	   if((title.trim().equals("")) || (content.trim().equals(""))) {
		   succese=-1;
		   return succese;
	   }
	   succese=memberService.questionUpdate(title,content,questionId);
	   return succese;
   }
   //질문에 답변 추가
   @RequestMapping("/newComment.do")
   public @ResponseBody Map newComment(@RequestParam("newContent")String content,@RequestParam("questionId")String questionId,Authentication auth,Map map) {
	   String userId = null;
	   if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	   } else {
	      userId = ((UserDetails) auth.getPrincipal()).getUsername();
	   }
	   map.put("userId", userId);
	   map.put("content", content);
	   map.put("questionId", questionId);
	   int result=memberService.newComment(map);
	   Map commentmap=new HashMap();
	   if(result==1) {
		// 댓글
		List<MyCommentDTO> commentLists = memberService.commentList(questionId);
		   for (MyCommentDTO dto : commentLists) {
		      dto.setAdminPath(dto.getAdminAvatarImagePath());
		      dto.setMemberPath(dto.getMemberAvatarImagePath());
		 }
		  commentmap.put("commentList", commentLists);
		  memberService.commentCountUpdateAtQuestion(map);
		  memberService.commentCountUpdate(map);
		  String commentCount=map.get("commentCount").toString();
		  int mycomment=memberService.commentsCount(Integer.parseInt(userId));
		  commentmap.put("commentCount", commentCount);
		  commentmap.put("mycomment",mycomment);
	   }
	   return commentmap;
   }
   // 댓글 상세보기
   @RequestMapping(value = "/commentsDetails.do", method = RequestMethod.POST)
   public @ResponseBody Map commentDetails(@RequestParam("commentId") String commentId,Authentication auth) {
	  String userId = null;
	  if (auth.getPrincipal().toString().contains("MemberOAuth2User")) {
		  MemberOAuth2User oauth2User = (MemberOAuth2User) auth.getPrincipal();
		  userId = Integer.toString(memberService.getMemberByEmail(oauth2User.getEmail()).getMemberId());
	  } else {
	      userId = ((UserDetails) auth.getPrincipal()).getUsername();
	  }
	  String questionId = memberService.commentsDetails(commentId);
      // 질문
      Map map=memberService.commentQuestion(questionId);
      if(!userId.equals(map.get("MEMBER_ID").toString())) {
    	  map.remove("MEMBER_ID");
      }
      // 댓글
      List<MyCommentDTO> commentList = memberService.commentList(questionId);
      for (MyCommentDTO dto : commentList) {
         dto.setAdminPath(dto.getAdminAvatarImagePath());
         dto.setMemberPath(dto.getMemberAvatarImagePath());
      }
      map.put("commentLists", commentList);
      map.put("comquestionId", questionId);
      return map;
   }
   

}///////////// MyPageController