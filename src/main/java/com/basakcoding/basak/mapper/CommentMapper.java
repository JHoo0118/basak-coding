package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CommentDTO;

@Repository
@Mapper
public interface CommentMapper {

	public List<CommentDTO> adminSelect();

	public List<CommentDTO> memberSelect();


}
