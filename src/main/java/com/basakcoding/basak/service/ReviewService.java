package com.basakcoding.basak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.AdminMapper;
import com.basakcoding.basak.mapper.PaymentMapper;
import com.basakcoding.basak.mapper.ReviewMapper;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewMapper reviewMapper;
    
    public List<Map> getReviewList() {
		return reviewMapper.getReviewList();
	}

	
    
   
	
}
