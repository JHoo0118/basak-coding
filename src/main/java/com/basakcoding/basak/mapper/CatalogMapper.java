package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CourseDTO;

@Repository
@Mapper
public interface CatalogMapper {

	//강의 상세보기
	 List<Map> courseList();
	Map selectOne(String courseId);
	String getClobText(String courseId);
	
	//강의 후기 기능
	List<Map> reviewList(String courseId);
	String reviewContent(Map map);
	
	int reviewCount(String courseId);
	int checkPayment(Map map);
	
	int reviewCheck(Map map);
	int reviewInsert(Map map);
	int reviewUpdate(Map map);
	
	
	//강의상세보기 좋아요 기능
	int likeCheck(Map map);
	int like(Map map);
	int unLike(Map map);
	int likeCount(Map map);
	
}
