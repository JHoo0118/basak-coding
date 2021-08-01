package com.basakcoding.basak.service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

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
	private String curriculumName;
	
	List<FileDTO> files = new ArrayList<FileDTO>();
	// path용
	private String courseId;
	
	public char getSeen() {
		return seen;
	}
	
	public String getVideoPath() {
    	if (videoUri == null) return "/images/video.mp4";
    	return "/upload/course/" + this.courseId + "/video/" + this.videoUri;
    }
}
