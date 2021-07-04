package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CurriculumMapper;

@Service
public class CurriculumService {
	
	//맵퍼 주입
	@Autowired
	private CurriculumMapper curriculumMapper;
	
	//Curriculum 리스트
	public List<CurriculumDTO> curriculumSelect(){
		return curriculumMapper.curriculumSelect();
	}
	
}
