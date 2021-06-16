package com.basakcoding.basak.service;

import java.util.HashMap;
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
}
