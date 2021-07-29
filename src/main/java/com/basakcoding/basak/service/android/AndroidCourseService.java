package com.basakcoding.basak.service.android;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.android.AndroidCourseMapper;
import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.ReviewDTO;
import com.basakcoding.basak.service.VideoDTO;

@Service
public class AndroidCourseService {

	@Autowired
	AndroidCourseMapper androidCourseMapper;
	
	public List<Map> getCourseList() {
		return androidCourseMapper.getCoursesList();
	}
	
	
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


	// 비디오 아이디 링크 얻기 시작
	public List<String> getCurriculum(String courseId) {
		return androidCourseMapper.getCurriculum(courseId);
	}


	public String getLastVideo(String lastCurriculumId) {
		return androidCourseMapper.getLastVideo(lastCurriculumId);
	}


	public String getVideo(Map params) {
		return androidCourseMapper.getVideo(params);
	}
	// 비디오 아이디 링크 얻기 끝


	public VideoDTO getCurrVideo(String videoId) {
		return androidCourseMapper.getCurrVideo(videoId);
	}


	public int isSeen(Map params) {
		return androidCourseMapper.isSeen(params);
	}


	public int updateSeen(Map params) {
		return androidCourseMapper.updateSeen(params);
	}
	
}
