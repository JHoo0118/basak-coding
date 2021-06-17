package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CourseDTO;

@Repository
@Mapper
public interface CatalogMapper {

	//강의 목록 리스트 가져오기
	 List<CourseDTO> courseList();

	Map selectOne(String id);
	
}
