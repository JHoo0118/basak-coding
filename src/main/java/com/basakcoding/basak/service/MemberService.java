package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.MemberMapper;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
    public  MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 전체 리스트 가져오기
    public List<MemberDTO> selectList() {
        return memberMapper.selectList();
    }
    
    // 사용자 생성
    public int createMember(Map map) {
    	encodePassword(map);
    	return memberMapper.createMember(map);
    }
    
    
    // 비밀번호 해싱
    private void encodePassword(Map map) {
    	String encodedPassword = passwordEncoder.encode(map.get("password").toString());
    	map.put("password", encodedPassword);
    }
    
    // 사용자 이메일 중복 체크
    public boolean isEmailUnique(String email) {
    	MemberDTO memberByEmail = memberMapper.getMemberByEmail(email);
    	return memberByEmail == null;
    }

    // 사용자 수정
	public int updateMember(Map map) {
		return memberMapper.updateMember(map);
	}

	// 사용자 Id로 얻기(One)
	public MemberDTO getMemberById(String memberId) {
		return memberMapper.getMemberById(memberId);
	}

	// 사용자 여러 행 한 번에 삭제
	public int deleteMultpleMember(Map map) {
		return memberMapper.deleteMultpleMember(map);
	}

	// 테스트용
	public List<Map> selectListWithReview() {
		return memberMapper.selectListWithReview();
	}

    //파일 업로드
    public int fileUpdate(Map map) {
    	return memberMapper.fileUpdate(map);
    }
    
    //로그인 확인
    public String loginOk(Map map) {
    	return memberMapper.loginOk(map);
    }
    
}