package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CategoryDTO;

@Repository
@Mapper
public interface CategoryMapper {

	public List<CategoryDTO> selectList();

	public CategoryDTO getCategoryById(String categoryId);

	public int createCategory(Map map);

	public int updateCategory(Map map);

	public int deleteMultpleCategory(Map map);

}
