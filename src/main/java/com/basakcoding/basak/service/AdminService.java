package com.basakcoding.basak.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.AdminMapper;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	public int createAdmin(AdminDTO admin) {
    	encodePassword(admin);
    	return adminMapper.createAdmin(admin);
		
	}
	
	private void encodePassword(AdminDTO admin) {
    	String encodedPassword = passwordEncoder.encode(admin.getPassword());
    	admin.setPassword(encodedPassword);
    }
}
