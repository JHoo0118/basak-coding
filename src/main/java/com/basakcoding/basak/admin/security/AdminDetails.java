//package com.basakcoding.basak.admin.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.basakcoding.basak.service.AdminDTO;
//import com.basakcoding.basak.service.AdminRoleService;
//
//public class AdminDetails implements UserDetails {
//	
//	private AdminDTO admin;
//	
//	public AdminDetails(AdminDTO admin) {
//		this.admin = admin;
//	}
//	
//	@Autowired
//	AdminRoleService adminRoleService;
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
////		List<RoleDTO> roles = adminRoleService.getRoles();
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("Admin"));
////		for (RoleDTO role : roles) {
////			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
////		}
//		return authorities;
////		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return admin.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		return admin.getEmail();
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
