package com.basakcoding.basak.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.PaymentService;
import com.basakcoding.basak.service.ReviewDTO;
import com.basakcoding.basak.service.VideoDTO;
import com.basakcoding.basak.service.android.AndroidCourseService;
import com.basakcoding.basak.util.FileUploadUtil;

@RestController
@RequestMapping("/api/1.0")
public class AndroidCourseController {
	
	@Autowired
	AndroidCourseService androidCourseService;
	
	@Autowired
	PaymentService paymentService;
	
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
		
		int result = androidCourseService.alreadyPayment(params);
		
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
		courseInfo.put("ALREADY_PAYMENT", result);
		return courseInfo;
	}
	
	@GetMapping("/course/video")
	public Map getCourseVideo(@Param("memberId") String memberId, @Param("courseId") String courseId) {
		Map result = new HashMap();
		Map params = new HashMap();
		params.put("memberId", memberId);
		params.put("courseId", courseId);
		String lastVideoId = getLastVideoId(params);
		
		VideoDTO video = androidCourseService.getCurrVideo(lastVideoId);
		System.out.println(video.getVideoId());
		video.setCourseId(courseId);
		
		// 커리큘럼과 비디오 얻기
		List<CurriculumDTO> curriculumList = androidCourseService.getCurriculumList(courseId);

		for (int i=0; i<curriculumList.size(); i++) {
			for (VideoDTO v : curriculumList.get(i).getVideos()) {
				params.put("videoId", v.getVideoId());
				int videoLength = (int)(Math.ceil(Double.parseDouble(v.getVideoLength())));
				int mins = videoLength/60;
				int secs = videoLength%60;
				String timeFormat = "" + (mins < 10 ? "0" + mins : mins) + ":" + (secs < 10 ? "0" + secs : secs);
				v.setVideoLength(timeFormat);
				int affected = androidCourseService.isSeen(params);

				if (affected == 1) v.setSeen('Y');
				else v.setSeen('N');
			}
		}
		
		result.put("LAST_VIDEO_ID", lastVideoId);
		result.put("currVideo", video);
		result.put("curriculum", curriculumList);
		return result;
	}
	
	@PostMapping("/seen")
	public String updateSeen(@Param("memberId") String memberId, @Param("videoId") String videoId) {
		Map params = new HashMap();
		params.put("memberId", memberId);
		params.put("videoId", videoId);
		int result = androidCourseService.updateSeen(params);
		return Integer.toString(result);
	}

	@CrossOrigin
	@PostMapping("/course/payment")
	public Map payment(@RequestBody Map<String, String> map) throws IOException {
		int result = androidCourseService.insertPayment(map);
		if (result == 1) {
			String courseId = map.get("courseId");
			List<String> videoIds = paymentService.getAllVideoIds(courseId);
			Map params = new HashMap();
			params.put("memberId", map.get("memberId"));
			for (int i=0; i<videoIds.size(); i++) {
				params.put("videoId", videoIds.get(i));
				int res = paymentService.insertVideoRecord(params);
				List<String> filenameList = paymentService.getFilenameList(videoIds.get(i));
				for (String filename : filenameList) {
					String copyFileDirName = "upload/course/" + courseId + "/file/copy-" + map.get("memberId") + "-" + filename;
					String originFileDirName =  "upload/course/" + courseId + "/file/" + filename;
					FileUploadUtil.copyFile(originFileDirName, copyFileDirName);
				}
			}
		}
		Map resMap = androidCourseService.paymentResult(map);
		return resMap;
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
