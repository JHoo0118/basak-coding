package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.RoleDTO;

@Mapper
@Repository
public interface AdminRoleMapper {
	public List<RoleDTO> getRoles();
	public List<RoleDTO> selectList();
	public int insertRelation(Map map);
}
