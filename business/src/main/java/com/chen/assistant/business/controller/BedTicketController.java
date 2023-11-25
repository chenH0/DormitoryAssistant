package com.chen.assistant.business.controller;

import com.chen.assistant.business.req.BedTicketQueryReq;
import com.chen.assistant.business.req.BedTicketSaveReq;
import com.chen.assistant.business.resp.BedTicketQueryResp;
import com.chen.assistant.business.service.BedTicketService;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bed-ticket")
public class BedTicketController {

    @Resource
    private BedTicketService bedTicketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<BedTicketQueryResp>> queryList(@Valid BedTicketQueryReq req) {
        PageResp<BedTicketQueryResp> list = bedTicketService.queryList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/query")
    public CommonResp<BedTicketQueryResp> queryById(Long id) {
        BedTicketQueryResp bedTicketQueryResp = bedTicketService.queryById(id);
        return new CommonResp<>(bedTicketQueryResp);
    }
}
