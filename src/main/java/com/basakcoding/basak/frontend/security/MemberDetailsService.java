//package com.basakcoding.basak.frontend.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.basakcoding.basak.mapper.MemberMapper;
//import com.basakcoding.basak.service.MemberDTO;
//
//public class MemberDetailsService implements UserDetailsService {
//
//	@Autowired
//	private MemberMapper memberMapper;
//	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		MemberDTO member = memberMapper.getMemberByEmail(email);
//		if (member != null)
//			return new MemberDetails(member);
//		
//		throw new UsernameNotFoundException("이메일을 찾을 수 없습니다.");
//	}
//
//}
