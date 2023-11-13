package com.chen.assistant.business.controller.admin;

import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.business.req.PartiQueryReq;
import com.chen.assistant.business.req.PartiSaveReq;
import com.chen.assistant.business.resp.PartiQueryResp;
import com.chen.assistant.business.service.PartiService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/parti")
public class PartiAdminController {

    @Resource
    private PartiService partiService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PartiSaveReq req) {
        partiService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<PartiQueryResp>> queryList(@Valid PartiQueryReq req) {
        PageResp<PartiQueryResp> list = partiService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        partiService.delete(id);
        return new CommonResp<>();
    }

}
