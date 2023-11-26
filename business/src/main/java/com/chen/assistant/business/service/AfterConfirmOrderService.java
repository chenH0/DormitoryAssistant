package com.chen.assistant.business.service;

import cn.hutool.core.date.DateTime;
import com.chen.assistant.business.domain.BedSeat;
import com.chen.assistant.business.domain.BedTicket;
import com.chen.assistant.business.domain.ConfirmOrder;
import com.chen.assistant.business.enums.ConfirmOrderStatusEnum;
import com.chen.assistant.business.feign.HouseHoldFeign;
import com.chen.assistant.business.mapper.BedSeatMapper;
import com.chen.assistant.business.mapper.BedTicketMapper;
import com.chen.assistant.business.mapper.ConfirmOrderMapper;
import com.chen.assistant.business.req.ConfirmOrderSaveReq;
import com.chen.assistant.business.resp.BedTicketQueryResp;
import com.chen.assistant.common.exception.BusinessException;
import com.chen.assistant.common.exception.BusinessExceptionEnum;
import com.chen.assistant.common.req.HouseholdUpdateReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AfterConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeConfirmOrderService.class);
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private BedTicketMapper bedTicketMapper;
    @Resource
    private BedSeatMapper bedSeatMapper;
    @Resource
    private BedSeatService bedSeatService;
    @Resource
    private HouseHoldFeign houseHoldFeign;

    @Transactional
    public void afterDoConfirm(ConfirmOrderSaveReq req, BedTicketQueryResp bedTicketQueryResp) {
        // 1.扣减库存
        BedTicket bedTicket = new BedTicket();
        bedTicket.setId(bedTicketQueryResp.getId());
        switch (req.getIndex()){
            case "1" ->{
                bedTicket.setOne(bedTicketQueryResp.getOne()-1);
            }
            case "2" ->{
                bedTicket.setTwo(bedTicketQueryResp.getTwo()-1);
            }
            case "3" ->{
                bedTicket.setThree(bedTicketQueryResp.getThree()-1);
            }
            case "4" ->{
                bedTicket.setFour(bedTicketQueryResp.getFour()-1);
            }
        }
        bedTicket.setTotal(bedTicketQueryResp.getTotal()-1);
        bedTicketMapper.updateByPrimaryKeySelective(bedTicket);
        // 2.更新床位表写入用户信息
        BedSeat queryBedSeat = bedSeatService.selectBySeatType(req.getRoomName(), req.getIndex());
        BedSeat bedSeat = new BedSeat();
        bedSeat.setUserId(req.getMemberId());
        bedSeat.setUserName(req.getMemberName());
        bedSeat.setId(queryBedSeat.getId());
        bedSeatMapper.updateByPrimaryKeySelective(bedSeat);
        // 3.为学生表绑定床位信息
        HouseholdUpdateReq householdUpdateReq = new HouseholdUpdateReq();
        householdUpdateReq.setMemberId(req.getMemberId());
        householdUpdateReq.setRoom(req.getRoomName()+" "+req.getIndex()+"床");
        houseHoldFeign.update(householdUpdateReq);
        // 4.更新订单状态为已完成
        DateTime now = new DateTime();
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(req.getId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.SUCCESS.getCode());
        confirmOrder.setUpdateTime(now);
        confirmOrderMapper.updateByPrimaryKeySelective(confirmOrder);
        LOG.info("床位订单完成");
    }

    private static void updateBed(ConfirmOrderSaveReq req, BedTicketQueryResp bedTicketQueryResp) {
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
