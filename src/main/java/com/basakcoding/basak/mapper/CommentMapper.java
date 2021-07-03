package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CommentDTO;

@Repository
@Mapper
public interface CommentMapper {

	//관리자 댓글 정보 가져오기
	public List<CommentDTO> adminSelect();
	//회원 댓글 정보 가져오기
	public List<CommentDTO> memberSelect();


}
