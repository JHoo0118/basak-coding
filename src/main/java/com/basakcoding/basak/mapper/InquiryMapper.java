package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.InquiryDTO;

@Repository
@Mapper
public interface InquiryMapper {

	//문의 리스트 가져오기
	public List<InquiryDTO> inquirySelect();
	//문의 상세보기 가져오기 
	public InquiryDTO selectOne(Map map);


}
