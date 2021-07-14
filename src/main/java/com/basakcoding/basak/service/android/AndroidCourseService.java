package com.basakcoding.basak.service.android;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.android.AndroidCourseMapper;

@Service
public class AndroidCourseService {

	@Autowired
	AndroidCourseMapper androidCourseMapper;
	
	public List<Map> getCourseList() {
		return androidCourseMapper.getCoursesList();
	}
	
	
	public List<Map> getMyCourseList(String memberId) {
		return androidCourseMapper.getMyCourseList(memberId);
	}

	public int getSeenCount(Map params) {
		return androidCourseMapper.getSeenCount(params);
	}
	
}
