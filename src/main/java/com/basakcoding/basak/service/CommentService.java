package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CommentMapper;
import com.basakcoding.basak.mapper.LikeMapper;

@Service
public class CommentService {
	
	//라이크맵퍼 주입
	@Autowired
	private CommentMapper commentMapper;
	
	//강의 좋아요
	public List<CommentDTO> adminSelect(){
		return commentMapper.adminSelect();
	}
	
}
