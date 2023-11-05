package com.chen.assistant.member.controller;

import com.chen.assistant.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public int hello(){
        return memberService.count();
    }
    @PostMapping("/register")
    public long register(String mobile){
        return memberService.register(mobile);
    }
}
