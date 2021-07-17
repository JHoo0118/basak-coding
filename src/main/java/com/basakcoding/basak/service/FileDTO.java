package com.basakcoding.basak.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
	private Integer FileId;
	private Integer videoId;
	private String filename;
	private String fileUri;
	private String videoTitle;
	private String curriculumName;
}
