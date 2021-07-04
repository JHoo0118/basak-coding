package com.basakcoding.basak.service;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDTO {
	private Integer curriculumId;
	private Integer courseId;
	private String name;
	
	List<VideoDTO> videos = new ArrayList<VideoDTO>();
}
