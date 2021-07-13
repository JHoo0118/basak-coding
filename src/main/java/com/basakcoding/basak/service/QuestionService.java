package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.QuestionMapper;

@Service
public class QuestionService {
	
	@Autowired
	QuestionMapper questionMapper;
	
	//질문 리스트 가져오기
	public List<QuestionDTO> lionList(){
		return questionMapper.lionList();
	}
	
	public QuestionDTO getQuestionById(String questionId) {
		return questionMapper.getQuestionById(questionId);
	}
	
	public int updateQuestion(Map map) {
		return questionMapper.updateQuestion(map);
	}
	public int createQuestion(Map map) {
		return questionMapper.createQuestion(map);
	}
	public int deleteMultpleQuestion(Map map) {
		return questionMapper.deleteMultpleQuestion(map);
	}
}
