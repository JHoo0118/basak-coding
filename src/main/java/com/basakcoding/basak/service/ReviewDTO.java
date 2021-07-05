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
public class ReviewDTO {
	
	//강의 후기 (리뷰관련)
	private Integer reviewId ;
	private Integer courseId;
	private Integer memberId;
	private String content;
	private Integer rating;
	private Date postdate;
	private String title;
	private String username;
	
	
	
	
	
}
