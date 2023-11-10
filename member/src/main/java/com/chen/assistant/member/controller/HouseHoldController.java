package com.chen.assistant.member.controller;

import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.CommonResp;
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
    public CommonResp<List<HouseholdResp>> queryList(@Valid HouseholdQueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        List<HouseholdResp> householdResp = householdService.show(req);
        return new CommonResp<>(householdResp);
    }
}
