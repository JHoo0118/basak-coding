package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CourseMapper;

@Service
public class CourseService {
	
	@Autowired
	private CourseMapper coureseMapper;
	
	public List<Map<String, String>> selectList() {
		return coureseMapper.selectList();
	}
}
