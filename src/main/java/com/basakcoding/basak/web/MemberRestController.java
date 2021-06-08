package com.basakcoding.basak.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.MemberDTO;
import com.basakcoding.basak.service.MemberService;

@RestController
@RequestMapping("/api/1.0/members")
public class MemberRestController {
	@Autowired
    private MemberService memberService;

    @GetMapping
    public List<MemberDTO> list() {
        return memberService.selectList();
    }
}