package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.QuestionMapper;

@Service
public class QuestionService {
	
	//라이크맵퍼 주입
	@Autowired
	private QuestionMapper questionMapper;
	
	//질문 리스트 가져오기
	public List<QuestionDTO> questionSelect(){
		return questionMapper.questionSelect();
	}//questionSelect
	
	//질문 가져오기
	public QuestionDTO selectOne(Map map) {
		return questionMapper.selectOne(map);
	}//selectOne
}//class
