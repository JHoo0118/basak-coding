package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.LikeMapper;

@Service
public class LikeService {
	
	//라이크맵퍼 주입
	@Autowired
	private LikeMapper likeMapper;
	
	//강의 좋아요
	public List<LikeDTO> courseSelect(){
		return likeMapper.courseSelect();
	}
	//강의 좋아요
	public List<LikeDTO> questionSelect(){
		return likeMapper.questionSelect();
	}
}
