package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.AdminRoleMapper;
import com.basakcoding.basak.mapper.PriceRoleMapper;


@Service
public class PriceRoleService {
	
	@Autowired
	PriceRoleMapper priceRoleMapper;
	
	public List<RoleDTO> getRoles() {
		return priceRoleMapper.getRoles();
	}
	
	public List<RoleDTO> selectList() {
		return priceRoleMapper.selectList();
	}
	
	public int insertRelation(Map map) {
		return priceRoleMapper.insertRelation(map);
	}
}
