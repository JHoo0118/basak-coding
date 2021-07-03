package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.LikeDTO;

@Repository
@Mapper
public interface LikeMapper {

	//강의 좋아요 정보 가져오기
	public List<LikeDTO> courseSelect();
	//질문 좋아요 정보 가져오기
	public List<LikeDTO> questionSelect();


}
