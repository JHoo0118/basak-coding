package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.VideoMapper;

@Service
public class VideoService {
	
	//맵퍼 주입
	@Autowired
	private VideoMapper videoMapper;
	
	//비디오
	public List<VideoDTO> videoSelect(){
		return videoMapper.videoSelect();
	}
	
}
