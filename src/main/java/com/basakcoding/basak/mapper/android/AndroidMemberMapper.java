package com.basakcoding.basak.mapper.android;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.MemberDTO;

@Repository
@Mapper
public interface AndroidMemberMapper {
	// *************************************************************
	// 안드로이드 REST API Mapper
	
	MemberDTO login(String email);

	String getPassword(String password);

	Map getMyPage(String memberId);
	
	// 내정보 가져오기
	Map selectMyInfo(int userId);
	   
	//결제 강의개수가져오기
	int paymentCount(int userId);

	//회원가입
	int signUp(Map map);

	//이메일 중복체크
	int emailCheck(String email);
	   
}

