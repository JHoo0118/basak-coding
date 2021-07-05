package com.basakcoding.basak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.basakcoding.basak.service.InquiryDTO;
import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.ReviewDTO;

@Repository
@Mapper
public interface ReviewMapper {

	//후기 리스트
	List<Map> AllReviewList();
	//후기 상세보기 가져오기 
	public ReviewDTO getReviewList(Map map);
		

	

}
