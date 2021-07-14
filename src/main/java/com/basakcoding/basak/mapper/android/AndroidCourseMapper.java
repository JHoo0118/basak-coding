package com.basakcoding.basak.mapper.android;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AndroidCourseMapper {

	List<Map> getCoursesList();
		
	List<Map> getMyCourseList(String memberId);
	
	int getSeenCount(Map params);

}
