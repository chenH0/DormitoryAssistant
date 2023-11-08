package com.chen.assistant.member.controller;

import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.member.req.HouseholdSaveReq;
import com.chen.assistant.member.service.HouseholdService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
