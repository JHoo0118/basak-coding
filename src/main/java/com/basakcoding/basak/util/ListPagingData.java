package com.basakcoding.basak.util;

import java.sql.Date;
import java.util.List;

import com.basakcoding.basak.service.CourseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListPagingData<T> {
	private List<T> lists;
	private int TotalCourseCount;
	private int pageSize;
	private int nowPage;
	private String pagingString;

}
