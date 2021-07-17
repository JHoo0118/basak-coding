package com.basakcoding.basak.service.android;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.android.AndroidCourseMapper;
import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.ReviewDTO;

@Service
public class AndroidCourseService {

	@Autowired
	AndroidCourseMapper androidCourseMapper;
	
	public List<Map> getMyCourseList(String memberId) {
		return androidCourseMapper.getMyCourseList(memberId);
	}

	public int getSeenCount(Map params) {
		return androidCourseMapper.getSeenCount(params);
	}

	public Map getCourseDetail(String courseId) {
		return androidCourseMapper.getCourseDetail(courseId);
	}

	public List<CurriculumDTO> getCurriculumList(String courseId) {
		return androidCourseMapper.getCurriculumList(courseId);
	}

	public List<FAQDTO> getFAQList(String courseId) {
		return androidCourseMapper.getFAQList(courseId);
	}

	public List<ReviewDTO> getReviewList(String courseId) {
		return androidCourseMapper.getReviewList(courseId);
	}

	public String getCourseDescription(String courseId) {
		return androidCourseMapper.getCourseDescription(courseId);
	}
	
}
