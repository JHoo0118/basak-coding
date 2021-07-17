package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.QuestionDTO;

@Repository
@Mapper
public interface QuestionMapper {

	//질문 리스트 가져오기
	public List<QuestionDTO> questionSelect();
	
	//질문 정보 가져오기
	public List<QuestionDTO> lionList();
	
	public QuestionDTO getQuestionById(String questionId);
	
	public int updateQuestion(Map map);
	
	public int createQuestion(Map map);

	//질문 가져오기
	public QuestionDTO selectOne(Map map);
	
	public int deleteMultpleQuestion(Map map);

}
