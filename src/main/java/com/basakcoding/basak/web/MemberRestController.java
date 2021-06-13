package com.basakcoding.basak.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basakcoding.basak.service.MemberService;

@RestController
@RequestMapping()
public class MemberRestController {
	@Autowired
    private MemberService memberService;

    @GetMapping("/api/1.0/members")
    public List<Map> list() {
        return memberService.selectListWithReview();
    }
}