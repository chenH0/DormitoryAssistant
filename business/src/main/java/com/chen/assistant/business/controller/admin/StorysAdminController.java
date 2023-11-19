package com.chen.assistant.business.controller.admin;

import com.chen.assistant.business.domain.Storys;
import com.chen.assistant.business.resp.PartiQueryResp;
import com.chen.assistant.common.resp.CommonResp;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.business.req.StorysQueryReq;
import com.chen.assistant.business.req.StorysSaveReq;
import com.chen.assistant.business.resp.StorysQueryResp;
import com.chen.assistant.business.service.StorysService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/query-all")
    public CommonResp<List<StorysQueryResp>> queryList() {
        List<StorysQueryResp> list = storysService.queryAll();
        return new CommonResp<>(list);
    }

    @GetMapping("/selectBy-floorCode")
    public Storys selectByFloorCode(String floorCode){
        return storysService.selectByFloorCode(floorCode);
    }
}
