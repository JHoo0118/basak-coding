package com.basakcoding.basak.service.android;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.android.AndroidMemberMapper;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;

@Service
public class AndroidMemberService {
	
	@Autowired
	private AndroidMemberMapper androidMemberMapper;
	@Autowired
    private PasswordEncoder passwordEncoder;

	//로그인 처리
    public MemberDTO login(String email) {
    	return androidMemberMapper.login(email);
    }

	public String getPassword(String password) {
		return androidMemberMapper.getPassword(password);
	}

	//마이페이지 뿌려주기
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
    
    // 비밀번호 해싱
    private void encodePassword(Map map) {
       String encodedPassword = passwordEncoder.encode(map.get("password").toString());
       map.put("password", encodedPassword);
    }
    
    //회원가입
	public int signUp(Map map) {
		encodePassword(map);
		return androidMemberMapper.signUp(map);
	}
	
	//email중복체크
	public int emailCheck(String email) {
		return androidMemberMapper.emailCheck(email);
	}

    //회원정보 수정
	public int updateMem(Map map) {
		encodePassword(map);
		return androidMemberMapper.updateMem(map);
	}
}
