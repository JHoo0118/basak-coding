package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.LikeDTO;

@Repository
@Mapper
public interface LikeMapper {

	public List<LikeDTO> courseSelect();
	public List<LikeDTO> questionSelect();


}
