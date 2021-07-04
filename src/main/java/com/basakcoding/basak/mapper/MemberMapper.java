package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.MemberDTO;

@Repository
@Mapper
public interface MemberMapper {

	// 테스트용
	List<Map> selectListWithReview();
	
	// 사용자 전체 리스트 가져오기
	List<MemberDTO> selectList();
	
	// 사용자 생성
	int createMember(Map map);
	int registerMember(Map map);
	
	// 사용자 이메일로 얻기(One)
	MemberDTO getMemberByEmail(String email);
	
	// 사용자 수정
	int updateMember(Map map);
	
	// 사용자 Id로 얻기(One)
	MemberDTO getMemberById(String memberId);
	
	// 사용자 여러 행 한 번에 삭제
	int deleteMultpleMember(Map map);
	
	//파일 업로드
	int fileUpdate(Map map);

	// 내정보 가져오기
	Map selectMyInfo(int userId);
	
	//결제 강의개수가져오기
	int paymentCount(int userId);
	
	//내 댓글 개수
	int commentsCount(int userId);
	
	//내 질문 개수
	int questionCount(int userId);

	//내 닉네임 변경
	int userNameEdit(Map map);


	//비밀번호 변경
	int passwordEdit(Map map);

	//내 강의 가져오기 
	List<Map> myCourses(int userId);

	//내 결제 정보가져오기
	List<Map> myPayment(int userId);

	//내 문의 제목,시간
	List<Map> myInquiry(int userId);
	
	//내 결제 상세
	Map viewDetails(String userId,String pay_code);

	//내 문의 상세
	Map inquDetails(String userId, String inquiry_id);

	//내 댓글 제목,시간
	List<Map> myComments(int userId);

	//내 댓글 상세보기
	Map commentsDetails(String userId, String commenTitle);
		
	//사용자 emailvalidate 값 업데이트
	int updateEmailValidate(Map map);
	
	//내 문의 상세답변없을때
	Map inquDetailNotExist(String userId, String inquiry_id);

	//내 질문
	List<Map> myQuestion(int userId);

	//내 강의 안본 비디오
	String courseVideo(String courseId);
	
	//본 동영상 개수
	int videoCount(String courseId);
	
	//마지막 동영상 아이디
	String lastVideoId(String courseId);

}
