package com.chen.assistant.business.controller.admin;

import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.business.req.StorysQueryReq;
import com.chen.assistant.business.req.StorysSaveReq;
import com.chen.assistant.business.resp.StorysQueryResp;
import com.chen.assistant.business.service.StorysService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/storys")
public class StorysAdminController {

    @Resource
    private StorysService storysService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody StorysSaveReq req) {
        storysService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<StorysQueryResp>> queryList(@Valid StorysQueryReq req) {
        PageResp<StorysQueryResp> list = storysService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        storysService.delete(id);
        return new CommonResp<>();
    }

}
