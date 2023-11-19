package com.chen.assistant.business.controller.admin;

import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.business.req.BedSeatQueryReq;
import com.chen.assistant.business.req.BedSeatSaveReq;
import com.chen.assistant.business.resp.BedSeatQueryResp;
import com.chen.assistant.business.service.BedSeatService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/bed-seat")
public class BedSeatAdminController {

    @Resource
    private BedSeatService bedSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody BedSeatSaveReq req) {
        bedSeatService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<BedSeatQueryResp>> queryList(@Valid BedSeatQueryReq req) {
        PageResp<BedSeatQueryResp> list = bedSeatService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        bedSeatService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-seat/{floorCode}")
    public CommonResp<Object> genRoomSeat(@PathVariable String floorCode){
        bedSeatService.genRoomSeat(floorCode);
        return new CommonResp<>();
    }
}
