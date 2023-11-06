package com.chen.assistant.member.controller;

import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.member.req.MemberLoginReq;
import com.chen.assistant.member.req.MemberRegisterReq;
import com.chen.assistant.member.req.MemberSendCodeReq;
import com.chen.assistant.member.resp.MemberLoginResp;
import com.chen.assistant.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> hello(){
        int count = memberService.count();
        return new CommonResp<>(count);
    }
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req){
        Long register = memberService.register(req);
        return new CommonResp<>(register);
    }
    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq req){
        memberService.sendCode(req);
        return new CommonResp<>();
    }
    @PostMapping("/login")
    public CommonResp<MemberLoginResp> sendCode(@Valid @RequestBody MemberLoginReq req){
        MemberLoginResp memberLoginResp = memberService.login(req);
        return new CommonResp<>(memberLoginResp);
    }
}
