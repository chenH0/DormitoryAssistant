package com.chen.assistant.business.controller.admin;

import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.business.req.RoomCarriageQueryReq;
import com.chen.assistant.business.req.RoomCarriageSaveReq;
import com.chen.assistant.business.resp.RoomCarriageQueryResp;
import com.chen.assistant.business.service.RoomCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/room-carriage")
public class RoomCarriageAdminController {

    @Resource
    private RoomCarriageService roomCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody RoomCarriageSaveReq req) {
        roomCarriageService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<RoomCarriageQueryResp>> queryList(@Valid RoomCarriageQueryReq req) {
        PageResp<RoomCarriageQueryResp> list = roomCarriageService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        roomCarriageService.delete(id);
        return new CommonResp<>();
    }

}
