package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.AdminRoleMapper;


@Service
public class AdminRoleService {
	
	@Autowired
	AdminRoleMapper adminRoleMapper;
	
	public List<RoleDTO> getRoles() {
		return adminRoleMapper.getRoles();
	}
	
	public List<RoleDTO> selectList() {
		return adminRoleMapper.selectList();
	}
	
	public int insertRelation(Map map) {
		return adminRoleMapper.insertRelation(map);
	}
}
