package com.basakcoding.basak.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.MemberMapper;
import com.basakcoding.basak.util.ListPagingData;
import com.basakcoding.basak.util.CommentPagingUtil;
import com.basakcoding.basak.util.SHA256;
import com.basakcoding.basak.util.myListPagingData;

@Service
public class MemberService {
	
	private int pageSize = 5;
	private int blockPage = 5;
	
   @Autowired
   private MemberMapper memberMapper;
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 전체 리스트 가져오기
    public List<MemberDTO> selectList() {
        return memberMapper.selectList();
    }
    
    // 사용자 생성
    public int createMember(Map map) {
       encodePassword(map);
       return memberMapper.createMember(map);
    }
    
    public int registerMember(Map map) {
       encodePassword(map);
       map.put("emailSecret", SHA256.getSHA256(map.get("email").toString()));  
       return memberMapper.registerMember(map);
    }
    
   
    
    
    // 비밀번호 해싱
    private void encodePassword(Map map) {
       String encodedPassword = passwordEncoder.encode(map.get("password").toString());
       map.put("password", encodedPassword);
    }
    
    // 사용자 이메일 중복 체크
    public boolean isEmailUnique(String email) {
       MemberDTO memberByEmail = memberMapper.getMemberByEmail(email);
       return memberByEmail == null;
    }

    // 사용자 수정
   public int updateMember(Map map) {
      return memberMapper.updateMember(map);
   }

   // 사용자 Id로 얻기(One)
   public MemberDTO getMemberById(String memberId) {
      return memberMapper.getMemberById(memberId);
   }

   // 사용자 여러 행 한 번에 삭제
   public int deleteMultpleMember(Map map) {
      
      return memberMapper.deleteMultpleMember(map);
   }

   // 테스트용
   public List<Map> selectListWithReview() {
      return memberMapper.selectListWithReview();
   }

    //파일 업로드
    public int fileUpdate(Map map) {
       return memberMapper.fileUpdate(map);
    }
    
    //회원정보 가져오기
    public Map selectMyInfo(int userId) {
       return memberMapper.selectMyInfo(userId);
    }
    //사용자 아이디로 결제강의 개수가져오기
    public int paymentCount(int userId) {
       return memberMapper.paymentCount(userId);
    }
    //사용자 아이디로 댓글 개수가져오기
    public int commentsCount(int userId) {
       return memberMapper.commentsCount(userId);
    }
    //사용자 아이디로 질문 개수가져오기
    public int questionCount(int userId) {
       return memberMapper.questionCount(userId);
    }
    //사용자 아이디로 닉네임 변경하기
    public int userNameEdit(Map map) {
       return memberMapper.userNameEdit(map);
    }
    //비밀번호 변경하기
    public int passwordEdit(Map map) {
       encodePassword(map);
       return memberMapper.passwordEdit(map);
    }
    //내 강의 가져오기
   public List<Map> myCourses(int userId) {
      return memberMapper.myCourses(userId);
   }
   //내 결제 정보
   public List<Map> myPayment(int userId) {
      return memberMapper.myPayment(userId);
   }
   //내 문의 제목,시간
   public List<Map> myInquiry(int userId) {
      return memberMapper.myInquiry(userId);
   }
   //내 결제 상세보기
   public Map viewDetails(String userId,String pay_code) {
      return memberMapper.viewDetails(userId,pay_code);
   }
   //내 문의 상세보기
   public Map inquDetails(String userId, String inquiry_id) {
      return memberMapper.inquDetails(userId,inquiry_id);
   }
   //내 댓글 제목,시간
   public myListPagingData myComments(int userId, int nowPage) {
	   	int totalCourseCount = memberMapper.commentsCount(userId);
		//전체 페이지수
		int totalPage =(int)Math.ceil((double)totalCourseCount/pageSize);		
		//시작 및 끝 ROWNUM구하기
		int start = (nowPage -1)*pageSize+1;
		int end = nowPage * pageSize;	
		//페이징을 위한 로직 끝]
		Map map=new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("userId",userId);
		List lists = memberMapper.myComments(map);
		String page = "/personal/qAndA/questions";
		String pagingString=CommentPagingUtil.pagingBootStrapStyle(totalCourseCount,pageSize, blockPage, nowPage, page);		
		myListPagingData<Map> listPagingData = 
				myListPagingData.builder()
				.lists(lists)
				.nowPage(nowPage)
				.pageSize(pageSize)
				.pagingString(pagingString)
				.TotalCourseCount(totalCourseCount)
				.build(); 
		return listPagingData;
   }
   //내 댓글 상세보기
   public Map commentsDetails(String userId, String commenTitle) {
      return memberMapper.commentsDetails(userId,commenTitle);
   }
   
    //사용자의 이메일벨리데이트 값 업데이트
    public int updateEmailValidate(Map map) {
       return memberMapper.updateEmailValidate(map);
    }

    //내 문의 상세보기에서 답변없을때
   public Map inquDetailNotExist(String userId, String inquiry_id) {
      return memberMapper.inquDetailNotExist(userId, inquiry_id);
   }
   //내 질문
   public List<Map> myQuestion(int userId) {
      return memberMapper.myQuestion(userId);
   }
   //내 강의 안본 비디오가져오기
//   public String courseVideo(String courseId) {
//      return memberMapper.courseVideo(courseId);
//   }
   //비디오 개수
   public int videoCount(Map params) {
      return memberMapper.videoCount(params);
   }

   //커리큘럼 아이디 얻기
   public List<String> getCurriculum(String courseId) {
      return memberMapper.getCurriculum(courseId);
   }

   //비디오 얻기
   public String getVideo(Map map) {
      return memberMapper.getVideo(map);
   }

   //마지막 동영상 아이디
   public String getLastVideo(String lastCurriculumId) {
      return memberMapper.getLastVideo(lastCurriculumId);
   }
   //내 질문 
   public Map questionDetails(String userId, String questionId) {
      return memberMapper.questionDetails(userId,questionId);
   }
   //내 질문 댓글
   public List<MyCommentDTO> commentList(String questionId) {
      return memberMapper.commentList(questionId);
   }
   //내 질문 좋아요
   public int likeCheck(int userId, int questionId) {
      return memberMapper.likeCheck(userId,questionId);
   }
   //좋아요 등록
   public int like(int userId, int questionId) {
      return memberMapper.like(userId,questionId);
   }
   //좋아요 취소
   public int unLike(int userId, int questionId) {
      return memberMapper.unLike(userId,questionId);
   }
   //질문별 좋아요
   public int likeCount(int questionId) {
      return memberMapper.likeCount(questionId);
   }
   //질문수정
   public int questionUpdate(String title, String content,String questionId) {
	   return memberMapper.questionUpdate(title,content,questionId);
   }
   //댓글 작성
   public int newComment(Map map) {
	   return memberMapper.newComment(map);
   }
   //질문별 댓글수 증가
   public int commentCountUpdate(Map map) {
	   return memberMapper.commentCountUpdate(map);
}

   

}