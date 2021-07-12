package com.basakcoding.basak.service.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.android.AndroidMemberMapper;
import com.basakcoding.basak.service.MemberDTO;

@Service
public class AndroidMemberService {
	
	@Autowired
	private AndroidMemberMapper androidMemberMapper;
	    
    public MemberDTO login(String email) {
    	return androidMemberMapper.login(email);
    }

	public String getPassword(String password) {
		return androidMemberMapper.getPassword(password);
	}
}
