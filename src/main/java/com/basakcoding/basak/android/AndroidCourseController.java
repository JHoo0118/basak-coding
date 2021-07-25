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
			String lastVideoId = getLastVideoId(params);
			int seenCount = androidCourseService.getSeenCount(params);
			int videoCount = Integer.parseInt(myCourse.get("VIDEO_COUNT").toString());
			int progress = (int)(Math.ceil(seenCount*100.0/videoCount));
			myCourse.put("PROGRESS", progress);
			myCourse.put("LAST_VIDEO_ID", lastVideoId);
		}
		return myCourseList;
	}
	
	@GetMapping("/courses")
	public List<Map> getCoursesList() {
		List<Map> coursesList = new ArrayList();
		coursesList = androidCourseService.getCourseList();
		return coursesList;
	}
	
	@GetMapping("/course/detail")
	public Map getCourseDetail(@Param("memberId") String memberId, @Param("courseId") String courseId) {
		Map courseInfo = androidCourseService.getCourseDetail(courseId);
		String description = androidCourseService.getCourseDescription(courseId);
		courseInfo.put("DESCRIPTION", description);
		
		List<CurriculumDTO> curriculum = androidCourseService.getCurriculumList(courseId);
		List<FAQDTO> faq = androidCourseService.getFAQList(courseId);
		List<ReviewDTO> review = androidCourseService.getReviewList(courseId);
		
		Map params = new HashMap();
		params.put("memberId", memberId);
		params.put("courseId", courseId);
		
		String lastVideoId = getLastVideoId(params);
		
		for (CurriculumDTO c : curriculum) {
			for (VideoDTO v : c.getVideos()) {
				v.setCourseId(courseId);
			}
		}
		
		int totalRating = 0;
		for (ReviewDTO r : review) {
			totalRating += r.getRating();
		}
		courseInfo.put("LAST_VIDEO_ID", lastVideoId);
		courseInfo.put("AVG_RATING", String.format("%.2f", totalRating * 1.0 / review.size()));
		courseInfo.put("curriculum", curriculum);
		courseInfo.put("faq", faq);
		courseInfo.put("review", review);
		return courseInfo;
	}
	
	@GetMapping("/course/video")
	public Map getCourseVideo(@Param("memberId") String memberId, @Param("courseId") String courseId, @Param("videoId") String videoId) {
		Map result = new HashMap();
		Map params = new HashMap();
		params.put("memberId", memberId);
		params.put("courseId", courseId);
		params.put("videoId", videoId);
		
		VideoDTO video = androidCourseService.getCurrVideo(videoId);
		video.setCourseId(courseId);
		
		// 커리큘럼과 비디오 얻기
		List<CurriculumDTO> curriculumList = androidCourseService.getCurriculumList(courseId);

		for (int i=0; i<curriculumList.size(); i++) {
			for (VideoDTO v : curriculumList.get(i).getVideos()) {
				params.put("videoId", v.getVideoId());
				int videoLength = Integer.parseInt(v.getVideoLength());
				int mins = videoLength/60;
				int secs = videoLength%60;
				String timeFormat = "" + (mins < 10 ? "0" + mins : mins) + ":" + (secs < 10 ? "0" + secs : secs);
				v.setVideoLength(timeFormat);
				int affected = androidCourseService.isSeen(params);

				if (affected == 1) v.setSeen('Y');
				else v.setSeen('N');
			}
		}
		
		result.put("currVideo", video);
		result.put("curriculum", curriculumList);
		return result;
	}
	
	private String getLastVideoId(Map params) {
		
		String courseVideoId = null;
        List<String> curriculumIds = androidCourseService.getCurriculum(params.get("courseId").toString());
        
        // 마지막 커리큘럼의 아이디
        String lastCurriculumId = curriculumIds.get(curriculumIds.size() - 1);
        // 마지막 비디오 아이디
        String lastVideoId = androidCourseService.getLastVideo(lastCurriculumId);
        
        for (int i = 0; i < curriculumIds.size(); i++) {
            params.put("curriculumId", curriculumIds.get(i));
            courseVideoId = androidCourseService.getVideo(params);
            if (courseVideoId != null)
               break;
        }
        if (courseVideoId != null) {
    	 	return courseVideoId;
        } else {
        	return lastVideoId;
        }
	}
}
