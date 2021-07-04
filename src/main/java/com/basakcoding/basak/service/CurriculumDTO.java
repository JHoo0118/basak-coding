package com.basakcoding.basak.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDTO {
	private Integer curriculumId;
	private Integer courseId;
	private String courseTitle;
	private String name;
}
