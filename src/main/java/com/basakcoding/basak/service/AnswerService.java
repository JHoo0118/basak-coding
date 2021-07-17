package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.AnswerMapper;

@Service
public class AnswerService {
	
	//맵퍼 주입
	@Autowired
	private AnswerMapper answerMapper;
	
	//문의 답변 리스트 가져오기
	public List<AnswerDTO> answerSelect(){
		return answerMapper.answerSelect();
	}//answerSelect
	
	//문의 답변 가져오기
	public AnswerDTO selectOne(Map map) {
		return answerMapper.selectOne(map);
	}//selectOne
	
	//문의 답변 삭제하기
	public int deleteOne(Map map) {
		return answerMapper.deleteOne(map);
	}//deleteOne
}//class
