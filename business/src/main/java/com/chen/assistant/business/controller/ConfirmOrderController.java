package com.chen.assistant.business.controller;

import com.chen.assistant.business.req.ConfirmOrderSaveReq;
import com.chen.assistant.business.service.BeforeConfirmOrderService;
import com.chen.assistant.common.resp.CommonResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    @Resource
    private BeforeConfirmOrderService beforeConfirmOrderService;

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderController.class);

    @PostMapping("/do")
    public CommonResp<Object> doConfirm(@Valid @RequestBody ConfirmOrderSaveReq req){
        Long id = beforeConfirmOrderService.beforeDoConfirm(req);
        return new CommonResp<>(String.valueOf(id));
    }

}
