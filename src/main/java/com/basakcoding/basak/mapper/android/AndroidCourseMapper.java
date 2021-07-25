package com.basakcoding.basak.mapper.android;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.ReviewDTO;

@Repository
@Mapper
public interface AndroidCourseMapper {

	List<Map> getCoursesList();
		
	List<Map> getMyCourseList(String memberId);
	
	int getSeenCount(Map params);

	Map getCourseDetail(String courseId);

	List<CurriculumDTO> getCurriculumList(String courseId);

	List<FAQDTO> getFAQList(String courseId);

	List<ReviewDTO> getReviewList(String courseId);

	String getCourseDescription(String courseId);

}
