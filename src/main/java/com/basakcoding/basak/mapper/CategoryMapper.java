package com.basakcoding.basak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CategoryDTO;

@Repository
@Mapper
public interface CategoryMapper {

	public List<CategoryDTO> selectList();

}
