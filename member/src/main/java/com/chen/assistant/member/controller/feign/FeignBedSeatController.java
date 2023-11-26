package com.chen.assistant.member.controller.feign;

import com.chen.assistant.common.req.HouseholdSaveReq;
import com.chen.assistant.common.req.HouseholdUpdateReq;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.member.service.HouseholdService;
import com.chen.assistant.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/household")
public class FeignBedSeatController {
    @Resource
    private HouseholdService householdService;
    @PostMapping("/update")
    public CommonResp<Object> update(@Valid @RequestBody HouseholdUpdateReq req) throws Exception{
        householdService.update(req);
        return new CommonResp<>();
    }
}
