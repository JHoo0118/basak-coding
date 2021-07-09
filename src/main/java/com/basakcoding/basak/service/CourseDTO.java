package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

	//강의 목록
	private Integer courseId;
	private Integer adminId;
	private Integer categoryId;
	private String title;
	private String shortDescription;
	private String description;
	private Integer price;
	private String difficulty;
	private Integer courseLength;
	private String thumbNail;
	private Date createdAT;
	private Date updateAT;
	private Integer likeCount;
	private String period;
	//강의 상세보기
	private String categoryName;
	private String adminName;
	private String videoTitle;
	private String videoContent;
	private Integer videoLength;
	private String filename;
	private String faqTitle;
	private String faqContent;
	
}
