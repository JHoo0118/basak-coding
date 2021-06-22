package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CatalogMapper;

@Service
public class CatalogService {
	
	//리뷰맵퍼 주입
	@Autowired
	private CatalogMapper catalogMapper;
	
	// 강의목록 리스트 가져오기
	public List<Map> courseList(){
		return catalogMapper.courseList();
	}
	
	public Map selectOne(String courseId) {
		return catalogMapper.selectOne(courseId);
	}
	
	public int likeCheck(Map map) {
		return catalogMapper.likeCheck(map);
	}
	
	public int like(Map map) {
		return catalogMapper.like(map);
	}
	
	public int unLike(Map map) {
		return catalogMapper.unLike(map);
	}
	public int likeCount(Map map) {
		return catalogMapper.likeCount(map);
	}

	
}
