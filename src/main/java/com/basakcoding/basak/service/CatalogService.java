package com.basakcoding.basak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CatalogMapper;

@Service
public class CatalogService {
	
	//리뷰맵퍼 주입
	@Autowired
	private CatalogMapper catalogMapper;
	
	// 강의목록 리스트 가져오기
	public List<CourseDTO> courseList(){
		return catalogMapper.courseList();
	}
	
	
}
