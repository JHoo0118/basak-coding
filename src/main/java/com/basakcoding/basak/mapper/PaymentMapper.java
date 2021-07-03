package com.basakcoding.basak.mapper;

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

}
