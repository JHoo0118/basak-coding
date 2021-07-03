package com.basakcoding.basak.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
	private Integer videoId;
	private Integer curriculumId;
	private String videoUri;
	private String videoLength;
	private String videoAnswer;
	private String videoTitle;
	private char seen;
	private String videoContent;
	
	// path용
	private String courseId;
	
	public String getVideoPath() {
    	if (videoUri == null) return "/images/video.mp4";
    	return "/upload/course/" + this.courseId + "/video/" + this.videoUri;
    }
}
