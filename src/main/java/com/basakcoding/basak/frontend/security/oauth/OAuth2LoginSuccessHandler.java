package com.basakcoding.basak.frontend.security.oauth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private MemberService memberService;
	
	@Autowired 
	public OAuth2LoginSuccessHandler(@Lazy MemberService memberService) {
		this.memberService = memberService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		MemberOAuth2User oauth2User = (MemberOAuth2User) authentication.getPrincipal();
		
		String name = oauth2User.getName();
		String email = oauth2User.getEmail();
		String clientName = oauth2User.getClientName();
		
		MemberDTO member = memberService.getMemberByEmail(email);
		if (member == null) {
			memberService.addNewMemberByOAuthLogin(name, email);
		} else {
			// 가입 방식 구글 또는 카카오로 업데이트
			Map params = new HashMap();
			params.put("email", email);
			params.put("type", clientName);
			memberService.updateAuthenticationType(params);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	
	
}
