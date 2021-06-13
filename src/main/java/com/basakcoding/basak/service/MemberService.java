package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.MemberMapper;

@Service
public class MemberService {
	private MemberMapper memberMapper;
    public  MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<MemberDTO> selectList() {
        return memberMapper.selectList();
    }
    
    public int createMember(Map map) {
    	encodePassword(map);
    	return memberMapper.createMember(map);
    }
    
    private void encodePassword(Map map) {
    	String encodedPassword = passwordEncoder.encode(map.get("password").toString());
    	map.put("password", encodedPassword);
    }
    
    public boolean isEmailUnique(String email) {
    	MemberDTO memberByEmail = memberMapper.getMemberByEmail(email);
    	return memberByEmail == null;
    }

	public int updateMember(Map map) {
		return memberMapper.updateMember(map);
	}

	public MemberDTO getMemberById(String memberId) {
		return memberMapper.getMemberById(memberId);
	}

	public List<Map> selectListWithReview() {
		return memberMapper.selectListWithReview();
	}

	public int deleteMultpleMember(Map map) {
		return memberMapper.deleteMultpleMember(map);
	}
}