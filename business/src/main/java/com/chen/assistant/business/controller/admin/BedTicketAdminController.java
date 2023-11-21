package com.chen.assistant.business.controller.admin;

import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.business.req.BedTicketQueryReq;
import com.chen.assistant.business.req.BedTicketSaveReq;
import com.chen.assistant.business.resp.BedTicketQueryResp;
import com.chen.assistant.business.service.BedTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/bed-ticket")
public class BedTicketAdminController {

    @Resource
    private BedTicketService bedTicketService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody BedTicketSaveReq req) {
        bedTicketService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<BedTicketQueryResp>> queryList(@Valid BedTicketQueryReq req) {
        PageResp<BedTicketQueryResp> list = bedTicketService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        bedTicketService.delete(id);
        return new CommonResp<>();
    }

}
