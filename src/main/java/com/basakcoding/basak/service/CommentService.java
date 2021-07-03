package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CommentMapper;
import com.basakcoding.basak.mapper.LikeMapper;

@Service
public class CommentService {
	
	//CommentMapper 주입
	@Autowired
	private CommentMapper commentMapper;
	
	//관리자 댓글
	public List<CommentDTO> adminSelect(){
		return commentMapper.adminSelect();
	}//
	
	//회원 댓글
	public List<CommentDTO> memberSelect(){
		return commentMapper.memberSelect();
	}//memberSelect
	
}//class
