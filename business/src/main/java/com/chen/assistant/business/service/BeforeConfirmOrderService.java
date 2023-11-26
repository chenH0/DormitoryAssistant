package com.chen.assistant.business.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.chen.assistant.business.domain.ConfirmOrder;
import com.chen.assistant.business.enums.ConfirmOrderStatusEnum;
import com.chen.assistant.business.mapper.ConfirmOrderMapper;
import com.chen.assistant.business.req.ConfirmOrderSaveReq;
import com.chen.assistant.business.resp.BedTicketQueryResp;
import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.exception.BusinessException;
import com.chen.assistant.common.exception.BusinessExceptionEnum;
import com.chen.assistant.common.inteceptor.LoginInterceptor;
import com.chen.assistant.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BeforeConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeConfirmOrderService.class);
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private BedTicketService bedTicketService;
    @Resource
    private AfterConfirmOrderService afterConfirmOrderService;

    public void beforeDoConfirm(ConfirmOrderSaveReq req) {
        Long id = null;
        req.setMemberId(LoginMemberContext.getId());
        // 保存确认订单表，状态初始
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setMemberName(req.getMemberName());
        confirmOrder.setRoomName(req.getRoomName());
        confirmOrder.setFloorsCode(req.getFloorsCode());
        confirmOrder.setIndex(req.getIndex());
        confirmOrder.setDateRoomTicketId(req.getDateRoomTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setId(SnowUtil.getSnowflaskNextId());
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrder.setMemberId(req.getMemberId());
        confirmOrder.setDate(now);
        confirmOrderMapper.insert(confirmOrder);
        // 查出余票记录
        BedTicketQueryResp bedTicketQueryResp = bedTicketService.queryById(req.getDateRoomTicketId());
        LOG.info("{}", bedTicketQueryResp.toString());
        // 预校验库存
        reduceBed(req, bedTicketQueryResp);
        // 选中床位后事务处理
        afterConfirmOrderService.afterDoConfirm(req, bedTicketQueryResp);
    }

    private static void reduceBed(ConfirmOrderSaveReq req, BedTicketQueryResp bedTicketQueryResp) {
        switch (req.getIndex()){
            case "1" ->{
                if(bedTicketQueryResp.getOne()<=0){
                    throw new BusinessException(BusinessExceptionEnum.BED_COUNT_ERROR);
                }

            }
            case "2" ->{
                if(bedTicketQueryResp.getTwo()<=0){
                    throw new BusinessException(BusinessExceptionEnum.BED_COUNT_ERROR);
                }
            }
            case "3" ->{
                if(bedTicketQueryResp.getThree()<=0){
                    throw new BusinessException(BusinessExceptionEnum.BED_COUNT_ERROR);
                }
            }
            case "4" ->{
                if(bedTicketQueryResp.getFour()<=0){
                    throw new BusinessException(BusinessExceptionEnum.BED_COUNT_ERROR);
                }
            }
        }
    }
}
