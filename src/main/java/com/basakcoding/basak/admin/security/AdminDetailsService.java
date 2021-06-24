//package com.basakcoding.basak.admin.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.basakcoding.basak.mapper.AdminMapper;
//import com.basakcoding.basak.service.AdminDTO;
//
//public class AdminDetailsService implements UserDetailsService {
//
//	@Autowired
//	private AdminMapper adminMapper;
//	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		AdminDTO admin = adminMapper.getAdminByEmail(email);
//		if (admin != null) {
//			return new AdminDetails(admin);
//		}
//		
//		throw new UsernameNotFoundException("이메일을 찾을 수 없습니다.");
//
//	}
//
//}
