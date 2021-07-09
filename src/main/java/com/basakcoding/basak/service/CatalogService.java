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
	public List<Map> faqList(String courseId){
		return catalogMapper.faqList(courseId);
	}
	public List<CurriculumDTO> getCurriculumList(String courseId){
		return catalogMapper.getCurriculumList(courseId);
	}
	
	public String getClobText(String courseId) {
		return catalogMapper.getClobText(courseId);

	}
	
	public List<Map> reviewList(String courseId) {
		return catalogMapper.reviewList(courseId);
	}
	
	public String  reviewContent(Map map) {
		return catalogMapper.reviewContent(map);
	}
	public String  reviewRating(Map map) {
		return catalogMapper.reviewRating(map);
	}
	
	
	public int reviewCount(String courseId) {
		return catalogMapper.reviewCount(courseId);
	}
	
	public int checkPayment(Map map) {
		return catalogMapper.checkPayment(map);
	}

	public int reviewCheck(Map map) {
		return catalogMapper.reviewCheck(map);
	}
	
	public int reviewInsert(Map map) {
		return catalogMapper.reviewInsert(map);
		
	}
	public int reviewUpdate(Map map) {
		return catalogMapper.reviewUpdate(map);
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
