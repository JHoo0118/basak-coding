package com.basakcoding.basak.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

	private Integer questionId;
	private Integer memberId;
	private Integer courseId;
	private String name;
	private String title;
	private String content;
	private Date createdAt;
	private int likeCount;
	private int commentCount;
	
	private String userName;
	private String courseTitle;
	private String questionTitle;
	
	
	List<CommentDTO> comments = new ArrayList<CommentDTO>();
	
}
