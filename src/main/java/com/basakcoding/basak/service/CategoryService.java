package com.basakcoding.basak.service;

import java.util.List;

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
}
