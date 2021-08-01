package com.basakcoding.basak.mapper.android;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.ReviewDTO;
import com.basakcoding.basak.service.VideoDTO;

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

	// 비디오 아이디 링크 얻기 시작
	List<String> getCurriculum(String courseId);

	String getLastVideo(String lastCurriculumId);

	String getVideo(Map params);

	// 비디오 아이디 링크 얻기 끝
	
	// 현재 재생할 비디오 얻기
	VideoDTO getCurrVideo(String videoId);

	int isSeen(Map params);

	int updateSeen(Map params);

}
