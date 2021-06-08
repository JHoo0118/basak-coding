package com.basakcoding.basak.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.basakcoding.basak.mapper.MemberMapper;

@Service
public class MemberService {
	private MemberMapper memberMapper;
    public  MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public List<MemberDTO> selectList() {
        return memberMapper.selectList();
    }
}