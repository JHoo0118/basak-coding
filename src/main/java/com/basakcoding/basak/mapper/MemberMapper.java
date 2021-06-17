package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.MemberDTO;

@Repository
@Mapper
public interface MemberMapper {
	
	List<MemberDTO> selectList();
	//파일 업로드
	int fileUpdate(Map map);
	
}
