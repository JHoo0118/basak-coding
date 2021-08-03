package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.CommentDTO;
import com.basakcoding.basak.service.CourseDTO;
import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FAQDTO;
import com.basakcoding.basak.service.FileDTO;
import com.basakcoding.basak.service.QuestionDTO;
import com.basakcoding.basak.service.VideoDTO;
import com.basakcoding.basak.util.ListPagingData;

@Repository
@Mapper
public interface CourseMapper {
	// 카테고리 목록 받기
	List<CategoryDTO> categoryList();
	
	// 강의 전체 목록 받기
	List<CourseDTO> selectList(Map map);
	
	// 강의 총 갯수
	int totalCount(Map map);
	// 강의 만들기
	int createCourse(Map map);

	// FAQ 만들기
	int createFaq(Map map);

	// 커리큘럼 만들기
	int createCurri(Map map);

	// 비디오 만들기
	int createVideo(Map map);

	// 파일 만들기
	int createFile(Map map);
	
	// 비디오 개수 및 비디오 총 길이 업데이트
	int updateVideoCntAndLength(Map map);

	// 파일 목록 얻기
	List<Map> getFileList(String videoId);

	// 강의 아이디 얻기
	String getCourseId(String videoId);

	// 비디오 하나 얻기
	VideoDTO getVideo(String videoId);

	// 커리큘럼 및 비디오 목록 얻기
	List<CurriculumDTO> getCurriculumList(String courseId);

	// 봤는지 여부 파악
	int isSeen(Map params);
	
	// 강의 상세보기 - 강의정보
	CourseDTO getCourseOne(Map map);
	
	// 강의 상세보기 - FAQ 목록 가져오기
	List<FAQDTO> getFAQList(Map map);

	//강의 살세보기 - 커리큘럼 
	List<CurriculumDTO> courseCurriculumList(Map map);
	
	int alreadyPayment(Map paymentCheck);

	int updateSeen(Map params);

	
	//강의 질문 리스트 가져오기
	List<QuestionDTO> questionList(String courseId);

	
	
	//강의 별 질문 가져오기
	Map questionOne(String questionId);
	
	
	//강의 질문 등록하기
	int newQuestion(Map map);
	//질문 답변 등록하기
	int newComment(Map map);
	
	//강의 질문 수정하기 기존 값 받아오기
	QuestionDTO clickUpdate(String questionId);
	//강의 질문 수정하기
	int updateQuestion(Map map);

	//강의 질문 상세보기
	Map questionDetails(String questionId);
	
	
	//좋아요
	int likeCheck(Map map);
	int like(Map map);
	int unLike(Map map);
	int likeCount(Map map);
	

}
