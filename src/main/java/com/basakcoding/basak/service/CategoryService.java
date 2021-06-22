package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CategoryMapper;

@Service
public class CategoryService {

	@Autowired
	CategoryMapper categoryMapper;

	public List<CategoryDTO> selectList() {
		return categoryMapper.selectList();
	}

	public CategoryDTO getCategoryById(String categoryId) {
		return categoryMapper.getCategoryById(categoryId);
	}

	public int createCategory(Map map) {
		return categoryMapper.createCategory(map);
	}

	public int updateCategory(Map map) {
		return categoryMapper.updateCategory(map);
	}

	public int deleteMultpleCategory(Map map) {
		return categoryMapper.deleteMultpleCategory(map);
	}
}
