package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.chen.assistant.business.domain.*;
import com.chen.assistant.business.enums.RoomTypeEnum;
import com.chen.assistant.business.mapper.BedSeatMapper;
import com.chen.assistant.business.mapper.BedTicketMapper;
import com.chen.assistant.business.req.RoomCarriageUpdateReq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.mapper.RoomCarriageMapper;
import com.chen.assistant.business.req.RoomCarriageQueryReq;
import com.chen.assistant.business.req.RoomCarriageSaveReq;
import com.chen.assistant.business.resp.RoomCarriageQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class RoomCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(RoomCarriageService.class);

    @Resource
    private RoomCarriageMapper roomCarriageMapper;

    @Resource
    private BedSeatMapper bedSeatMapper;

    @Resource
    private BedTicketMapper bedTicketMapper;

    public void save(RoomCarriageSaveReq req) {
        DateTime now = DateTime.now();
        RoomCarriage roomCarriage = BeanUtil.copyProperties(req, RoomCarriage.class);
        if (ObjectUtil.isNull(roomCarriage.getCreateTime())) {
            if(ObjectUtil.isNull(roomCarriage.getId())){
                roomCarriage.setId(SnowUtil.getSnowflaskNextId());
            }
            roomCarriage.setCreateTime(now);
            roomCarriage.setUpdateTime(now);
            roomCarriage.setName(roomCarriage.getFloorsCode()+"层"+"-"+roomCarriage.getIndex()+"房"+"-"+RoomTypeEnum.findEnumByCode(roomCarriage.getBedType()));
            roomCarriageMapper.insert(roomCarriage);
        } else {
            roomCarriage.setUpdateTime(now);
            roomCarriage.setName(roomCarriage.getFloorsCode()+"层"+"-"+roomCarriage.getIndex()+"房"+"-"+RoomTypeEnum.findEnumByCode(roomCarriage.getBedType()));
            roomCarriageMapper.updateByPrimaryKey(roomCarriage);
        }
    }

    public PageResp<RoomCarriageQueryResp> queryList(RoomCarriageQueryReq req) {
        RoomCarriageExample roomCarriageExample = new RoomCarriageExample();
        roomCarriageExample.setOrderByClause("id asc");
        RoomCarriageExample.Criteria criteria = roomCarriageExample.createCriteria();
        if(ObjectUtil.isNotNull(req.getFloorCode())){
            criteria.andFloorsCodeEqualTo(req.getFloorCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<RoomCarriage> roomCarriageList = roomCarriageMapper.selectByExample(roomCarriageExample);

        PageInfo<RoomCarriage> pageInfo = new PageInfo<>(roomCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<RoomCarriageQueryResp> list = BeanUtil.copyToList(roomCarriageList, RoomCarriageQueryResp.class);

        PageResp<RoomCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        roomCarriageMapper.deleteByPrimaryKey(id);
    }

    public RoomCarriage selectByRoomCode(Long id){
        RoomCarriageExample roomCarriageExample = new RoomCarriageExample();
        RoomCarriageExample.Criteria criteria = roomCarriageExample.createCriteria();
        criteria.andIdEqualTo(id);
        return roomCarriageMapper.selectByExample(roomCarriageExample).get(0);
    }

    @Transactional
    public void updateStatus(RoomCarriageUpdateReq req) {
        DateTime now = DateTime.now();
        RoomCarriage roomCarriage = new RoomCarriage();
        roomCarriage.setId(req.getId());
        roomCarriage.setStatus(req.getStatus());
        roomCarriageMapper.updateByPrimaryKeySelective(roomCarriage);

        BedTicketExample bedTicketExample = new BedTicketExample();
        BedTicketExample.Criteria bedcriteria = bedTicketExample.createCriteria();
        bedcriteria.andRoomCodeEqualTo(req.getId());
        bedTicketMapper.deleteByExample(bedTicketExample);

        if(req.getStatus()==1){
            BedSeatExample bedSeatExample = new BedSeatExample();
            BedSeatExample.Criteria criteria = bedSeatExample.createCriteria();
            criteria.andRoomCodeEqualTo(req.getId());
            List<BedSeat> bedSeats = bedSeatMapper.selectByExample(bedSeatExample);
            BedTicket bedTicket = new BedTicket();
            bedTicket.setTotal(0);
            for (BedSeat bedSeat: bedSeats) {
                switch (bedSeat.getIndex()){
                    case "1":
                        bedTicket.setOne(bedSeat.getStatus());
                        bedTicket.setTotal(bedTicket.getTotal()+bedSeat.getStatus());
                        break;
                    case "2":
                        bedTicket.setTwo(bedSeat.getStatus());
                        bedTicket.setTotal(bedTicket.getTotal()+bedSeat.getStatus());
                        break;
                    case "3":
                        bedTicket.setThree(bedSeat.getStatus());
                        bedTicket.setTotal(bedTicket.getTotal()+bedSeat.getStatus());
                        break;
                    case "4":
                        bedTicket.setFour(bedSeat.getStatus());
                        bedTicket.setTotal(bedTicket.getTotal()+bedSeat.getStatus());
                        break;
                }
            }
            bedTicket.setBedType(req.getBedType());
            bedTicket.setFloorsCode(req.getFloorsCode());
            bedTicket.setId(req.getId());
            bedTicket.setCreateTime(now);
            bedTicket.setUpdateTime(now);
            bedTicket.setRoomCode(req.getId());
            bedTicket.setRoomName(req.getName());
            bedTicket.setDate(now);
            bedTicketMapper.insert(bedTicket);
        }
    }

    @Transactional
    public void closeAll() {
        RoomCarriageExample roomCarriageExample = new RoomCarriageExample();
        RoomCarriageExample.Criteria criteria = roomCarriageExample.createCriteria();
        criteria.andStatusEqualTo(1);
        List<RoomCarriage> roomCarriages = roomCarriageMapper.selectByExample(roomCarriageExample);
        RoomCarriage updateRoom = new RoomCarriage();
        for(RoomCarriage roomCarriage: roomCarriages){
            updateRoom.setId(roomCarriage.getId());
            updateRoom.setStatus(0);
            roomCarriageMapper.updateByPrimaryKeySelective(updateRoom);
        }
        BedTicketExample bedTicketExample = new BedTicketExample();
        BedTicketExample.Criteria bedTicketCriteria = bedTicketExample.createCriteria();
        bedTicketCriteria.andIdIsNotNull();
        bedTicketMapper.deleteByExample(bedTicketExample);
    }
}
