package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.InquiryDTO;

@Repository
@Mapper
public interface InquiryMapper {

	//문의 정보 가져오기
	public List<InquiryDTO> inquirySelect();


}
