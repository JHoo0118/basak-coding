package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.InquiryMapper;

@Service
public class InquiryService {
	
	//맵퍼 주입
	@Autowired
	private InquiryMapper inquiryMapper;
	
	//문의 리스트 가져오기
	public List<InquiryDTO> inquirySelect(){
		return inquiryMapper.inquirySelect();
	}
	
	//문의 상세보기 가져오기
	public InquiryDTO selectOne(Map map) {
		return inquiryMapper.selectOne(map);
	}
	
	//문의 삭제하기(1개)
	public int deleteOne(Map map) {
		return inquiryMapper.deleteOne(map);
	}

	//문의하기 생성
	public int insertInquiry(Map map) {
		return inquiryMapper.insertInquiry(map);
	}
	
	
	
	
	//문의 카테고리
	public ClassificationDTO getInquiryById(String clId) {
		return inquiryMapper.getInquiryById(clId);
	
	}//2

	
	public int createInquiry(Map map) {
		return inquiryMapper.createInquiry(map);
	}//3

	public int updateInquiry(Map map) {
		return inquiryMapper.updateInquiry(map);
	}//4

	public int deleteMultpleInquiry(Map map) {
		return inquiryMapper.deleteMultpleInquiry(map);
	}//5


	public List<ClassificationDTO> selectList() {
		return inquiryMapper.selectList();
	}//1
	
	
	public int isAnswered(String inquiryId) {
		return inquiryMapper.isAnswered(inquiryId);
	}

	public int createAnswer(Map map) {
		return inquiryMapper.createAnswer(map);		
	}
	
	public int updateAnswer(Map map) {
		return inquiryMapper.updateAnswer(map);		
	}
}
