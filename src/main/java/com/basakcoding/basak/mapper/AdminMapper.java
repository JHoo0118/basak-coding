package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.AdminDTO;

@Repository
@Mapper
public interface AdminMapper {
	
	List<AdminDTO> getAdminList();
	AdminDTO getAdminByEmail(String email);
	int createAdmin(AdminDTO admin);
	int createAdminByMap(Map map);
	int loginAdmin(Map map);
	int passwordEncoding(AdminDTO admin);
	AdminDTO getAdminById(String adminId);
	AdminDTO getAdminByName(String name);

	
	//관리자페이지- 결제관리  
	List<AdminDTO> getPriceList();

	//xml 아이디 일치
}
