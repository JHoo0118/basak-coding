package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	private Integer commentId;
	private Integer questionId;
	private Integer adminId;
	private Integer memberId;
	private String title;
	private String name;
	private String username;
	private String content;
	private Date createdAt;
	
	
	
	
}
