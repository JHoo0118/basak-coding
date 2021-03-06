package com.basakcoding.basak.web.frontend;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.basakcoding.basak.service.CertificationService;
import com.basakcoding.basak.service.MemberService;
import com.basakcoding.basak.service.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Authenticator;
import com.basakcoding.basak.util.Gmail;
import com.basakcoding.basak.util.SHA256;
@Controller
@RequestMapping("/auth")
public class AuthController {
   
   @Resource(name="memberService")
   private MemberService memberService;
   
   @GetMapping("/signin")//로그인 페이지
   public String signin() {
      return "frontend/signin";
   }//signin   

   
   @GetMapping("/signup")
   public String signup() {
      return "frontend/signup";
   }
   
   @GetMapping("/reset-pass")
   public String resetPassword() {
      return "frontend/resetPassword";
   }
   
   //인증메일 인증확인시 emailvalidate=Y로 업데이트
   @PostMapping("/email-information")
   public String emailValidate(@RequestParam Map map) {
      if(memberService.updateEmailValidate(map)==1) 
      {
        return "frontend/signin";
      }
      return "frontend/signupConfirm";
   }
   
 
    @GetMapping("/kakaoLogin")
      public @ResponseBody String kakaoCallback(String code) {//Data를 리턴해주는 컨트롤러 함수
         
         //POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
         //Retrofit2 (안드로이드쪽에서 더 자주쓰임)
         //OkHttp
         //RestTemplate
         
         RestTemplate rt = new RestTemplate();
         
         
         
         //HttpHeaders 오브젝트 생성
         HttpHeaders headers = new HttpHeaders();
         headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
         
         //HttpBody 오브젝트 생성
         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
         params.add("grant_type","authorization_code");
         params.add("client_id","702e704b386e7afd4fdd82adab67d698");
         params.add("redirect_uri","http://localhost:9090/auth/kakaoLogin");
         params.add("code",code);
         
         //HttpHeaders와 HttpBody를 하나의 오브젝트에 담기
         HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
               new HttpEntity<>(params,headers);
         
         
         //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
         ResponseEntity<String> response = rt.exchange(
                  "https://kauth.kakao.com/oauth/token",
                  HttpMethod.POST,
                  kakaoTokenRequest,
                  String.class
               
               );
         
         //Gson,Json Simple,ObjectMapper
         ObjectMapper objectMapper = new ObjectMapper();
         OAuthToken oauthToken=null;
         try {
            oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
         } catch (JsonMappingException e) {
            
            e.printStackTrace();
         } catch (JsonProcessingException e) {
            
            e.printStackTrace();
         }
         System.out.println("카카오 엑세스 토큰:"+oauthToken.getAccess_token());
         
         
         
         
   RestTemplate rt2 = new RestTemplate();
            
         
         //HttpHeaders 오브젝트 생성
         HttpHeaders headers2 = new HttpHeaders();
         headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
         headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
         
         
         
         //HttpHeaders와 HttpBody를 하나의 오브젝트에 담기
         HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
               new HttpEntity<>(headers2);
         
         
         //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
         ResponseEntity<String> response2 = rt2.exchange(
               "https://kapi.kakao.com/v2/user/me",
                  HttpMethod.POST,
                  kakaoProfileRequest2,
                  String.class
               
               );
         
         return response2.getBody();
         
      }
 
    
    @PostMapping("/signup")
    public    String signup(@RequestParam Map map , Model model) throws IOException{
       int affected = memberService.registerMember(map);
       
        String host ="http://localhost:9090/auth";
        String from = "basakcoding@gmail.com";
        String to = memberService.getMemberById(map.get("memberId").toString()).getEmail();
        String subject = "사이트 이용을 위한 이메일 인증 메일 입니다.";
        String content = "다음 링크에 접속하여 이메일 인증을 진행하세요."+
        "<form id='emailfrm' action='"+host+"/email-information' method='POST'>"
              + "<input type='hidden' name='code' value='"+new SHA256().getSHA256(to)+"'/>"
              + "<input type='submit' value='이메일 인증하기'/></form>";

        
      if(affected==1) {
          // SMTP
          Properties p = new Properties();
          p.put("mail.smtp.user", from);
          p.put("mail.smtp.host", "smtp.googlemail.com");
          p.put("mail.smtp.port", "465");
          p.put("mail.smtp.starttls.enable", "true");
          p.put("mail.smtp.auth", "true");
          p.put("mail.smtp.debug", "true");
          p.put("mail.smtp.socketFactory.port", "465");
          p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          p.put("mail.smtp.socketFactory.fallback", "false");
          try{
              Authenticator auth = new Gmail();
              Session ses = Session.getInstance(p, auth);
              ses.setDebug(true);
              MimeMessage msg = new MimeMessage(ses); 
              msg.setSubject(subject);
              Address fromAddr = new InternetAddress(from);
              msg.setFrom(fromAddr);
              Address toAddr = new InternetAddress(to);
              msg.addRecipient(Message.RecipientType.TO, toAddr);
              msg.setContent(content, "text/html;charset=UTF-8");    
              Transport.send(msg);
          } catch(Exception e){
              e.printStackTrace();     
          }
          return "frontend/signupConfirm";
      }///if
         
      else {
    	  model.addAttribute("signupfail", "회원가입에 실패하였습니다.");
          return "frontend/signup";
      }//else
   }/////signup
   
       

   @PostMapping("/signup2")
   @ResponseBody
   public String signupAjax (@RequestParam String email, Model model) throws IOException{
      if(memberService.isEmailUnique(email)==false) {
          //이메일 중복 입니다.       
         // model.addAttribute("errormsg", "이메일 중복 입니다.");     
         return "1";
      }
      return "-1";
   }
   
 //임시 비밀번호 발급 컨트롤러
   @PostMapping("/getresetpassword")
   public String getresetpassword(@RequestParam Map map , Model model, RedirectAttributes redirectAttributes) throws IOException {
      //임시비밀번호 랜덤으로 발생
       Random rand  = new Random();
           String password = "";
           for(int i=0; i<6; i++) {
               String ran = Integer.toString(rand.nextInt(10));
               password+=ran;
           }
           
           
         String email=(String)map.get("email");
         
         //입력된 이메일값=테이블 이메일값 중복
         if(memberService.isEmailUnique(email)==false) {
         
         Map params = new HashMap();
         params.put("password", password);
         params.put("email", email);
         memberService.updatepassword(params);
         
          String host ="http://localhost:9090/auth";
          String from = "basakcoding@gmail.com";
          String to = email;
          String subject = "바삭코딩 임시 비밀번호 발급 입니다.";
          String content = "귀하의 비밀번호가 아래와 같이 임시로 발급 되었습니다."+"["+password+"]";
        
        //구글 smtp 설정
        Properties p = new Properties();
        p.put("mail.smtp.user", from);
        p.put("mail.smtp.host", "smtp.googlemail.com");
        p.put("mail.smtp.port", "465");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.debug", "true");
        p.put("mail.smtp.socketFactory.port", "465");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.socketFactory.fallback", "false");
        try{
            Authenticator auth = new Gmail();
            Session ses = Session.getInstance(p, auth);
            ses.setDebug(true);
            MimeMessage msg = new MimeMessage(ses); 
            msg.setSubject(subject);
            Address fromAddr = new InternetAddress(from);
            msg.setFrom(fromAddr);
            Address toAddr = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO, toAddr);
            msg.setContent(content, "text/html;charset=UTF-8");    
            Transport.send(msg);
        } catch(Exception e){
            e.printStackTrace();       
        }
        return "frontend/signin";  
         }//if 
         
         else {
        	 //등록된 이메일 x
        	 redirectAttributes.addFlashAttribute("message", "등록된 이메일이 아닙니다.");
             return "redirect:/auth/reset-pass";	
         }
        	 
      
   }
      
      //SMS 인증 서비스
      CertificationService certificationService = new CertificationService();

      @PostMapping("/check/sendSMS")
       public @ResponseBody
       String sendSMS(@RequestParam String phoneNumber) {      
           Random rand  = new Random();
           String numStr = "";
           for(int i=0; i<4; i++) {
               String ran = Integer.toString(rand.nextInt(10));
               numStr+=ran;
           }

           System.out.println("수신자 번호 : " + phoneNumber);
           System.out.println("인증번호 : " + numStr);
           
         certificationService.certifiedPhoneNumber(phoneNumber,numStr);
           return numStr;
       }

}
