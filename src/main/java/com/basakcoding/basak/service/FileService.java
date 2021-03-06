package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.FileMapper;

@Service
public class FileService {
	
	//맵퍼 주입
	@Autowired
	private FileMapper fileMapper;
	
	//비디오
	public List<FileDTO> fileSelect(){
		return fileMapper.fileSelect();
	}
	
}
