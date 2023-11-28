package com.chen.assistant.business.feign;

import com.chen.assistant.common.req.HouseholdUpdateReq;
import com.chen.assistant.common.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name="member", url = "http://127.0.0.1:8001")
@FeignClient("member")
public interface HouseHoldFeign {
    @GetMapping("/member/hello")
    String hello();
    @GetMapping("/member/feign/household/update")
    CommonResp<Object> update(@RequestBody HouseholdUpdateReq req);
}
