package com.basakcoding.basak.mapper.android;

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
}
