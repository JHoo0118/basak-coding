package com.basakcoding.basak.frontend.security.oauth;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class MemberOAuth2User implements OAuth2User {

	private String clientName;
	private OAuth2User oauth2User;
	
	public MemberOAuth2User(OAuth2User oauth2User, String clientName) {
		this.oauth2User = oauth2User;
		this.clientName = clientName;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {
		LinkedHashMap kakaoAccount = oauth2User.getAttribute("kakao_account");
		if (kakaoAccount != null) {
			LinkedHashMap profile = (LinkedHashMap) kakaoAccount.get("profile");
			return profile.get("nickname").toString();
		}
		return oauth2User.getAttribute("name");
	}
	
	public String getEmail() {
		LinkedHashMap kakaoAccount = oauth2User.getAttribute("kakao_account");
		if (kakaoAccount != null) {
			return kakaoAccount.get("email").toString();
		}
		return oauth2User.getAttribute("email");
	}

	public String getClientName() {
		return clientName;
	}
}
