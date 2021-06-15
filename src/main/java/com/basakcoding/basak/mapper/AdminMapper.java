package com.basakcoding.basak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.AdminDTO;

@Repository
@Mapper
public interface AdminMapper {
	AdminDTO getAdminByEmail(String email);
	int createAdmin(AdminDTO admin);
	int passwordEncoding(AdminDTO admin);
}
