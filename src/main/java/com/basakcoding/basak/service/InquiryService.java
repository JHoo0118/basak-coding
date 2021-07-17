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
}
