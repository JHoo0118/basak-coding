package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.AdminMapper;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    
    public List<AdminDTO> getAdminList() {
    	return adminMapper.getAdminList();
    }

    // DTO를 통한 관리자 생성
    public int createAdmin(AdminDTO admin) {
//    	encodePassword(admin);
    	return adminMapper.createAdmin(admin);
	}
    
    // Map을 통한 관리자 생성
	public int createAdminByMap(Map map) {
//		encodePassword(map);
		return adminMapper.createAdminByMap(map);
	}
	public int loginAdmin(Map map) {
//		encodePassword(map);
		return adminMapper.loginAdmin(map);
	}
	
//	private void encodePassword(AdminDTO admin) {
//    	String encodedPassword = passwordEncoder.encode(admin.getPassword());
//    	admin.setPassword(encodedPassword);
//    }
//	
//	private void encodePassword(Map<String, String> map) {
//    	String encodedPassword = passwordEncoder.encode(map.get("password"));
//    	map.put("password", encodedPassword);
//    }

	public int passwordEncoding(AdminDTO admin) {
//		encodePassword(admin);
		return adminMapper.passwordEncoding(admin);
	}

	// 이메일로 관리자 얻기
	public AdminDTO getAdminByEmail(String email) {
		return adminMapper.getAdminByEmail(email);
	}

	// 아이디로 관리자 얻기
	public AdminDTO getAdminById(String adminId) {
		return adminMapper.getAdminById(adminId);
	}
	
	// 이름으로 관리자 얻기
	public AdminDTO getAdminByName(String name) {
		return adminMapper.getAdminByName(name);
	}



}
