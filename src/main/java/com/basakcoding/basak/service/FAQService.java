package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.FAQMapper;

@Service
public class FAQService {
	
	//맵퍼 주입
	@Autowired
	private FAQMapper faqMapper;
	
	//faq 리스트
	public List<FAQDTO> faqSelect(){
		return faqMapper.faqSelect();
	}
	
}
