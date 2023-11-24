package com.chen.assistant.member.controller;

import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.member.req.HouseholdQueryReq;
import com.chen.assistant.member.req.HouseholdSaveReq;
import com.chen.assistant.member.resp.HouseholdResp;
import com.chen.assistant.member.service.HouseholdService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/household")
public class HouseHoldController {
    @Resource
    private HouseholdService householdService;

    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody HouseholdSaveReq req){
        householdService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<HouseholdResp>> queryList(@Valid HouseholdQueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        PageResp<HouseholdResp> householdResp = householdService.show(req);
        return new CommonResp<>(householdResp);
    }

    @GetMapping("/getUser")
    public CommonResp<HouseholdResp> getUser(){
        return new CommonResp<>(householdService.getUser(LoginMemberContext.getId()));
    }
}
