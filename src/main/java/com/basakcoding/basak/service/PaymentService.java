package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.PaymentMapper;

@Service
public class PaymentService {
	@Autowired
	private PaymentMapper paymentMapper;
	
	//강의 내용
	public Map listAll(String courseId) {
		return paymentMapper.listAll(courseId);
	}

	//결제 내역
	public Map priceList(Map map) {
		return paymentMapper.priceList(map);
	}
	
	//구매자 아이디로 이메일 얻기
	public MemberDTO getMemberById(String memberId) {
		return paymentMapper.getMemberById(memberId);
	}

	public int insertPayment(Map map) {
		return paymentMapper.insertPayment(map);
		
	}

	public int alreadyPayment(Map map) {
		return paymentMapper.alreadyPayment(map);
	}

	public List<String> getAllVideoIds(String courseId) {
		return paymentMapper.getAllVideoIds(courseId);
	}

	public int insertVideoRecord(Map params) {
		return paymentMapper.insertVideoRecord(params);
	}

	public List<String> getFilenameList(String videoId) {
		return paymentMapper.getFilenameList(videoId);
	}
}
