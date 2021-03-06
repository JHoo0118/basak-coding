package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.MemberDTO;

@Repository
@Mapper
public interface PaymentMapper {

	//강의 내용
	Map listAll(String courseId);
	
	//결제 내역
	Map priceList(Map map);
	
	//구매자 아이디로 이메일 얻기
	MemberDTO getMemberById(String memberId);

	int insertPayment(Map map);

	int alreadyPayment(Map map);
	
	//관리자페이지- 결제관리  
	List<Map> getPriceList();

	// 강의 아이디로 모든 비디오 아이디 가져오기
	List<String> getAllVideoIds(String courseId);

	// 비디오 기록용
	int insertVideoRecord(Map params);

	List<String> getFilenameList(String videoId);
		

}
