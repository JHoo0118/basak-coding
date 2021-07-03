package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.FileDTO;
import com.basakcoding.basak.service.VideoDTO;

@Repository
@Mapper
public interface CourseMapper {
	// 카테고리 목록 받기
	List<CategoryDTO> categoryList();
	
	// 강의 전체 목록 받기
	List<Map<String, String>> selectList();
	
	// 강의 만들기
	int createCourse(Map map);

	// FAQ 만들기
	int createFaq(Map map);

	int createCurri(Map map);

	int createVideo(Map map);

	int createFile(Map map);
	
	int updateVideoCnt(Map map);

	List<Map> getFileList(String videoId);

	String getCourseId(String videoId);

	VideoDTO getVideo(String videoId);
}
