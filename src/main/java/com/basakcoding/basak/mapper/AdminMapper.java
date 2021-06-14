package com.basakcoding.basak.mapper;

import com.basakcoding.basak.service.AdminDTO;

public interface AdminMapper {
	AdminDTO getAdminByEmail(String email);

	int createAdmin(AdminDTO admin);
}
