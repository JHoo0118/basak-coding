package com.basakcoding.basak.service.android;

import java.util.List;
import java.util.Map;

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

	public Map getMyPage(String memberId) {
		return androidMemberMapper.getMyPage(memberId);
	}
	
	//회원정보 가져오기
    public Map selectMyInfo(int userId) {
       return androidMemberMapper.selectMyInfo(userId);
    }
    
	//사용자 아이디로 결제강의 개수가져오기
    public int paymentCount(int userId) {
       return androidMemberMapper.paymentCount(userId);
    }
}
