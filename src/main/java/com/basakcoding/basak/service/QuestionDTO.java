package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
	private Integer questionId;
	private String name;
	private String courseTitle;
	private String questionTitle;
	private Date createdAt;
}
