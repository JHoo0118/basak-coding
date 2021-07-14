package com.basakcoding.basak.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.android.AndroidCourseService;

@RestController
@RequestMapping("/api/1.0")
public class AndroidCourseController {
	
	@Autowired
	AndroidCourseService androidCourseService;
	
	@GetMapping("/my-course")
	public List<Map> getMyCourseList(@Param("memberId") String memberId) {
		List<Map> myCourseList = new ArrayList();
		myCourseList = androidCourseService.getMyCourseList(memberId);
		Map params = new HashMap();
		params.put("memberId", memberId);
		for (Map myCourse : myCourseList) {
			// 해당 강의 본 비디오의 개수
			params.put("courseId", myCourse.get("COURSE_ID").toString());
			int seenCount = androidCourseService.getSeenCount(params);
			int videoCount = Integer.parseInt(myCourse.get("VIDEO_COUNT").toString());
			// map1.put("progress",(int)(Math.ceil(videoCount*100/Double.parseDouble((map1.get("VIDEO_COUNT").toString())))));
			int progress = (int)(Math.ceil(seenCount*100.0/videoCount));
			myCourse.put("PROGRESS", progress);
		}
		
		return myCourseList;
	}
	
	@GetMapping("/courses")
	public List<Map> getCoursesList() {
		List<Map> coursesList = new ArrayList();
		coursesList = androidCourseService.getCourseList();
		System.out.println(coursesList.size());
		return coursesList;
	}
	
	
	
}
