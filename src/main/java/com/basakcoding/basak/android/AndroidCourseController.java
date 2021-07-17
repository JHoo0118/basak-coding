package com.basakcoding.basak.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.ReviewDTO;
import com.basakcoding.basak.service.VideoDTO;
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
	
	@GetMapping("/course/detail")
	public Map getCourseDetail(@Param("courseId") String courseId) {
		Map courseInfo = androidCourseService.getCourseDetail(courseId);
		String description = androidCourseService.getCourseDescription(courseId);
		courseInfo.put("DESCRIPTION", description);
		
		List<CurriculumDTO> curriculum = androidCourseService.getCurriculumList(courseId);
		List<FAQDTO> faq = androidCourseService.getFAQList(courseId);
		List<ReviewDTO> review = androidCourseService.getReviewList(courseId);
		
		for (CurriculumDTO c : curriculum) {
			for (VideoDTO v : c.getVideos()) {
				v.setCourseId(courseId);
			}
		}
		
		int totalRating = 0;
		for (ReviewDTO r : review) {
			totalRating += r.getRating();
		}
		
		courseInfo.put("AVG_RATING", String.format("%.2f", totalRating * 1.0 / review.size()));
		courseInfo.put("curriculum", curriculum);
		courseInfo.put("faq", faq);
		courseInfo.put("review", review);
		return courseInfo;
	}
}
