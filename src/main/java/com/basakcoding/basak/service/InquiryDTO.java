package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
	private Integer inquiryId;
	private String userName;
	private String Title;
	private String classification;
	private Date createdAt;
}
