package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.MemberMapper;

@Service
public class MemberService {
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
	public List<Map> myComments(int userId) {
		return memberMapper.myComments(userId);
	}
	//내 댓글 상세보기
	public Map commentsDetails(String userId, String commenTitle) {
		return memberMapper.commentsDetails(userId,commenTitle);
	}

	public Map inquDetailNotExist(String userId, String inquiry_id) {
		return memberMapper.inquDetailNotExist(userId, inquiry_id);
	}

}