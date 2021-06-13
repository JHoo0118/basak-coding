package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.MemberDTO;

@Repository
@Mapper
public interface MemberMapper {
	
	List<Map> selectListWithReview();
	List<MemberDTO> selectList();
	int createMember(Map map);
	MemberDTO getMemberByEmail(String email);
	int updateMember(Map map);
	MemberDTO getMemberById(String memberId);
	int deleteMultpleMember(Map map);
}
