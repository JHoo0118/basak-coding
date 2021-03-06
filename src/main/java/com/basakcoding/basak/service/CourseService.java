package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.CourseMapper;
import com.basakcoding.basak.util.ListPagingData;
import com.basakcoding.basak.util.CoursePagingUtil;

@Service
public class CourseService {

	private int pageSize = 5;
	private int blockPage = 5;

	@Autowired
	private CourseMapper courseMapper;

	// 카테고리 목록
	public List<CategoryDTO> categoryList() {
		return courseMapper.categoryList();
	}

	// 강의 목록
	public ListPagingData<CourseDTO> selectList(Map map, int nowPage) {
		int totalCourseCount = courseMapper.totalCount(map);
		System.out.println(totalCourseCount);
		// 전체 페이지수
		int totalPage = (int) Math.ceil((double) totalCourseCount / pageSize);
		// 시작 및 끝 ROWNUM구하기
		int start = (nowPage - 1) * pageSize + 1;
		int end = nowPage * pageSize;
		// 페이징을 위한 로직 끝]
		map.put("start", start);
		map.put("end", end);
		List lists = courseMapper.selectList(map);
		String page = "/admin/course/management";
		String pagingString = CoursePagingUtil.pagingBootStrapStyle(totalCourseCount, pageSize, blockPage, nowPage,
				page, map);
		ListPagingData<CourseDTO> listPagingData = ListPagingData.builder().lists(lists).nowPage(nowPage)
				.pageSize(pageSize).pagingString(pagingString).TotalCourseCount(totalCourseCount).build();
		return listPagingData;
	}

	// 강의 생성
	public int createCourse(Map map) {
		return courseMapper.createCourse(map);
	}

	// FAQ 생성
	public int createFaq(Map map) {
		return courseMapper.createFaq(map);
	}

	// 커리큘럼 생성
	public int createCurri(Map map) {
		return courseMapper.createCurri(map);

	}

	// 비디오 생성
	public int createVideo(Map map) {
		return courseMapper.createVideo(map);
	}

	// 파일 생성
	public int createFile(Map map) {
		return courseMapper.createFile(map);
	}

	// 강의 비디오 총 개수 및 총 길이 업데이트
	public int updateVideoCntAndLength(Map map) {
		return courseMapper.updateVideoCntAndLength(map);
	}

	// 파일 목록 가져오기
	public List<Map> getFileList(String videoId) {
		return courseMapper.getFileList(videoId);
	}

	public String getCourseId(String videoId) {
		return courseMapper.getCourseId(videoId);
	}

	public VideoDTO getVideo(String videoId) {
		return courseMapper.getVideo(videoId);
	}

	public List<CurriculumDTO> getCurriculumList(String courseId) {
		return courseMapper.getCurriculumList(courseId);
	}

	public int isSeen(Map params) {
		return courseMapper.isSeen(params);
	}

	// 강의 상세보기 - 강의정보
	public CourseDTO getCourseOne(Map map) {
		return courseMapper.getCourseOne(map);
	}

	// 강의 상세보기 - FAQ 목록 가져오기
	public List<FAQDTO> getFAQList(Map map) {
		return courseMapper.getFAQList(map);
	}

	// 강의 상세보기 - 커리큘럼
	public List<CurriculumDTO> courseCurriculumList(Map map) {
		return courseMapper.courseCurriculumList(map);
	}

	// 결제했는지 확인
	public int alreadyPayment(Map paymentCheck) {
		return courseMapper.alreadyPayment(paymentCheck);

	}

	// seen 업데이트
	public int updateSeen(Map params) {
		return courseMapper.updateSeen(params);
	}

	// 강의별 질문 가져오기
	public List<QuestionDTO> questionList(String courseId) {
		return courseMapper.questionList(courseId);
	}


	// 질문 등록하기
	public int newQuestion(Map map) {
		return courseMapper.newQuestion(map);
	}

	// 답변 등록하기
	public int newComment(Map map) {
		return courseMapper.newComment(map);
	}

	// 질문 수정하기 전 값 받아오기
	public QuestionDTO clickUpdate(String questionId) {
		return courseMapper.clickUpdate(questionId);
	}

	// 질문 수정하기
	public int updateQuestion(Map map) {
		return courseMapper.updateQuestion(map);
	}
	//질문 상세보기
	public Map questionDetails(String questionId) {
		return courseMapper.questionDetails(questionId);
	}
	
	//좋아요
	public int likeCheck(Map map) {
		return courseMapper.likeCheck(map);
	}
	
	public int like(Map map) {
		return courseMapper.like(map);
	}
	
	public int unLike(Map map) {
		return courseMapper.unLike(map);
	}
	public int likeCount(Map map) {
		return courseMapper.likeCount(map);
	}

	public int commentCount(String questionId) {

		return courseMapper.commentCount(questionId);
	}

	public String totalCommentCount(String questionId) {
		
		return courseMapper.totalCommentCount(questionId);
	}

	public MyCommentDTO getComment(Map map) {
		return courseMapper.getComment(map);
	}
	
	
	
	

}
