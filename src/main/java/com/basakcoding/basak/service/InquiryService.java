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
	
	//질문 리스트 가져오기
	public List<InquiryDTO> inquirySelect(){
		return inquiryMapper.inquirySelect();
	}
}
