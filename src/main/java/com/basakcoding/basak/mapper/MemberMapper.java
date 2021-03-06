package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MyCommentDTO;


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
   
   //내 문의 개수
   int inquiryCount(int userId);

   //내 닉네임 변경
   int userNameEdit(Map map);

   //비밀번호 변경
   int passwordEdit(Map map);

   //내 강의 가져오기 
   List<Map> myCourses(int userId);

   //내 결제 정보가져오기
   List<Map> myPayment(Map map);

   //내 문의 제목,시간
   List<Map> myInquiry(Map map);
   
   //내 결제 상세
   Map viewDetails(String userId,String pay_code);

   //내 문의 상세
   Map inquDetails(String userId, String inquiry_id);

   //내 댓글 제목,시간
   List<Map> myComments(Map map);

   //내 댓글 질문아이디가져오기
   String commentsDetails(String commentId);
      
   //사용자 emailvalidate 값 업데이트
   int updateEmailValidate(Map map);
   
   //내 문의 상세답변없을때
   Map inquDetailNotExist(String userId, String inquiry_id);

   //내 질문
   List<Map> myQuestion(Map map);

   //내 강의 안본 비디오
   // String courseVideo(String courseId);
   
   //본 동영상 개수
   int videoCount(Map params);

   //커리큘럼 아이디 얻기
   List<String> getCurriculum(String courseId);

   //비디오 얻기
   String getVideo(Map map);

   //마지막 동영상 아이디
   String getLastVideo(String lastCurriculumId);

   //내 질문
   Map questionDetails(String userId, String questionId);

   //내 질문 댓글
   List<MyCommentDTO> commentList(String questionId);

   //내 질문 좋아요
   int likeCheck(int userId, int questionId);

   //좋아요 등록
   int like(int userId, int questionId);

   //좋아요 취소
   int unLike(int userId, int questionId);

   //질문별 좋아요
   int likeCount(int questionId);

   //내 질문 답변없을때
   Map questionDetailNotExist(String userId, String questionId);

   //질문 수정
   int questionUpdate(String title, String content,String questionId);

   //답변 추가
   int newComment(Map map);

   //질문별 댓글수 증가
   int commentCountUpdate(Map map);

   int commentCountUpdateAtQuestion(Map map);

   //임시비밀번호
   int updatepassword(Map map);

   //댓글의 질문가져오기
   Map commentQuestion(String questionId);

   // 가입 방식 업데이트
   int updateAuthenticationType(Map map);

   // OAuth 가입
   int addNewMemberByOAuthLogin(MemberDTO member);
   
}