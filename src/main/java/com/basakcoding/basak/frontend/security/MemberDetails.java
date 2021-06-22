//package com.basakcoding.basak.frontend.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.basakcoding.basak.service.MemberDTO;
//
//public class MemberDetails implements UserDetails{
//
//	private MemberDTO member;
//	public MemberDetails(MemberDTO member) {
//		this.member = member;
//	}
//	
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return member.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		return Integer.toString(member.getMemberId());
////		return member.getEmail();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//}
