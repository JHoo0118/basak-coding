package com.basakcoding.basak.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FAQDTO {
	private Integer faqId;
	private Integer courseId;
	private String courseTitle;
	private String faqTitle;
}
