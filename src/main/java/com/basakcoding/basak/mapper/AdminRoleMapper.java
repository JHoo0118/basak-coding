package com.basakcoding.basak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.RoleDTO;

@Mapper
@Repository
public interface AdminRoleMapper {
	public List<RoleDTO> getRoles();
}
