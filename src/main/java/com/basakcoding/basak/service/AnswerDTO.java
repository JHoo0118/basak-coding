package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
	private Integer answerId;
	private Integer inquiryId;
	private String adminName;
	private String inquiryTitle;
	private String answerTitle;
	private String classification;
	private String content;
	private Date createdAt;
}
