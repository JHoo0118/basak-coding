package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CourseMapper;

@Service
public class CourseService {
	
	@Autowired
	private CourseMapper courseMapper;
	
	// 카테고리 목록
	public List<CategoryDTO> categoryList() {
		return courseMapper.categoryList();
	}
	
	// 강의 목록
	public List<Map<String, String>> selectList() {
		return courseMapper.selectList();
	}

	// 강의 생성
	public int createCourse(Map map) {
		return courseMapper.createCourse(map);
	}
	
	// FAQ 생성
	public int createFaq(Map map) {
		return courseMapper.createFaq(map);
	}

	// 커리큘럼 생성
	public int createCurri(Map map) {
		return courseMapper.createCurri(map);
		
	}

	// 비디오 생성
	public int createVideo(Map map) {
		return courseMapper.createVideo(map);
	}

	// 파일 생성
	public int createFile(Map map) {
		return courseMapper.createFile(map);
	}
	
	// 강의 비디오 총 개수 및 총 길이 업데이트
	public int updateVideoCntAndLength(Map map) {
		return courseMapper.updateVideoCntAndLength(map);
	}

	// 파일 목록 가져오기
	public List<Map> getFileList(String videoId) {
		return courseMapper.getFileList(videoId);
	}

	public String getCourseId(String videoId) {
		return courseMapper.getCourseId(videoId);
	}

	public VideoDTO getVideo(String videoId) {
		return courseMapper.getVideo(videoId);
	}

	public List<CurriculumDTO> getCurriculumList(String courseId) {
		return courseMapper.getCurriculumList(courseId);
	}

	public int isSeen(Map params) {
		return courseMapper.isSeen(params);
	}
	
	// 강의 상세보기 - 강의정보
	public CourseDTO getCourseOne(Map map) {
		return courseMapper.getCourseOne(map);
	}

	public int alreadyPayment(Map paymentCheck) {
		return courseMapper.alreadyPayment(paymentCheck);
	}
}
