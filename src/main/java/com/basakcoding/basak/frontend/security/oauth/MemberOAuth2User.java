package com.basakcoding.basak.frontend.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class MemberOAuth2User implements OAuth2User {

	private OAuth2User oauth2User;
	
	public MemberOAuth2User(OAuth2User oauth2User) {
		this.oauth2User = oauth2User;
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
		return oauth2User.getAttribute("name");
	}
	
	public String getEmail() {
		return oauth2User.getAttribute("email");
	}
	
	public String getMemberId() {
		return Integer.toString(oauth2User.getAttribute("memberId"));
	}

}
