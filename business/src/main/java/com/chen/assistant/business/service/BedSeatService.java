package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.chen.assistant.business.domain.*;
import com.chen.assistant.business.mapper.RoomCarriageMapper;
import com.chen.assistant.business.req.RoomCarriageSaveReq;
import com.chen.assistant.common.context.LoginMemberContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.mapper.BedSeatMapper;
import com.chen.assistant.business.req.BedSeatQueryReq;
import com.chen.assistant.business.req.BedSeatSaveReq;
import com.chen.assistant.business.resp.BedSeatQueryResp;
import jakarta.annotation.Resource;
import org.bouncycastle.pqc.legacy.math.linearalgebra.IntUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BedSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(BedSeatService.class);

    @Resource
    private BedSeatMapper bedSeatMapper;
    @Resource
    private StorysService storysService;
    @Resource
    private RoomCarriageService roomCarriageService;
    @Resource
    private RoomCarriageMapper roomCarriageMapper;

    public void save(BedSeatSaveReq req) {
        DateTime now = DateTime.now();
        BedSeat bedSeat = BeanUtil.copyProperties(req, BedSeat.class);
        if (ObjectUtil.isNull(bedSeat.getId())) {
            bedSeat.setId(SnowUtil.getSnowflaskNextId());
            bedSeat.setCreateTime(now);
            bedSeat.setUpdateTime(now);
            bedSeatMapper.insert(bedSeat);
        } else {
            bedSeat.setUpdateTime(now);
            bedSeatMapper.updateByPrimaryKey(bedSeat);
        }
    }

    public PageResp<BedSeatQueryResp> queryList(BedSeatQueryReq req) {
        BedSeatExample bedSeatExample = new BedSeatExample();
        bedSeatExample.setOrderByClause("id asc");
        BedSeatExample.Criteria criteria = bedSeatExample.createCriteria();
        try {
            Long id = LoginMemberContext.getId();
            if(!Objects.isNull(id)){
                criteria.andUserIdEqualTo(LoginMemberContext.getId());
            }
        }catch (Exception e){
            LOG.info(e.getMessage());
        }

        if(StrUtil.isNotBlank(req.getFloorsCode())){
            criteria.andFloorsCodeEqualTo(req.getFloorsCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<BedSeat> bedSeatList = bedSeatMapper.selectByExample(bedSeatExample);

        PageInfo<BedSeat> pageInfo = new PageInfo<>(bedSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<BedSeatQueryResp> list = BeanUtil.copyToList(bedSeatList, BedSeatQueryResp.class);

        PageResp<BedSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        bedSeatMapper.deleteByPrimaryKey(id);
    }

    public BedSeat selectBySeatType (String roomName, String index) {
        BedSeatExample bedSeatExample = new BedSeatExample();
        bedSeatExample.createCriteria().andRoomNameEqualTo(roomName).andIndexEqualTo(index);
        return bedSeatMapper.selectByExample(bedSeatExample).get(0);
    }

    @Transactional
    public void genRoomSeat(String floorCode){
        DateTime dateTime = DateTime.now();
        BedSeatExample bedSeatExample = new BedSeatExample();
        BedSeatExample.Criteria criteria = bedSeatExample.createCriteria();
        criteria.andFloorsCodeEqualTo(floorCode);
        LOG.info("删除已存在的床位，然后重新生成");
        bedSeatMapper.deleteByExample(bedSeatExample);
        RoomCarriageExample roomCarriageExample = new RoomCarriageExample();
        RoomCarriageExample.Criteria roomCriteria = roomCarriageExample.createCriteria();
        roomCriteria.andFloorsCodeEqualTo(floorCode);
        List<RoomCarriage> roomCarriages = roomCarriageMapper.selectByExample(roomCarriageExample);
        LOG.info("删除已存在的楼层，然后重新生成");
        for (RoomCarriage roomCarriage: roomCarriages){
            roomCarriageService.delete(roomCarriage.getId());
        }

        Storys storys = storysService.selectByFloorCode(floorCode);
        LOG.info("{}层床位正在生成", storys.getFloors());
        for (int i = 1; i <= storys.getTotal(); i++) {
            for (int j = 0; j < 3; j++) {
                RoomCarriageSaveReq roomCarriageSaveReq = new RoomCarriageSaveReq();
                roomCarriageSaveReq.setBedCount(4);
                roomCarriageSaveReq.setFloorsCode(floorCode);
                roomCarriageSaveReq.setId(SnowUtil.getSnowflaskNextId());
                roomCarriageSaveReq.setBedType(String.valueOf(j));
                roomCarriageSaveReq.setStatus(0);
                roomCarriageSaveReq.setIndex(Integer.valueOf(storys.getFloors()+StrUtil.fillBefore(String.valueOf(i), '0', 2)));
                roomCarriageService.save(roomCarriageSaveReq);
                RoomCarriage roomCarriage = roomCarriageService.selectByRoomCode(roomCarriageSaveReq.getId());
                LOG.info("生成宿舍-{}", roomCarriage.getName());
                for (int k = 1; k <= roomCarriage.getBedCount(); k++) {
                    BedSeat bedSeat = new BedSeat();
                    bedSeat.setUpdateTime(dateTime);
                    bedSeat.setCreateTime(dateTime);
                    bedSeat.setId(SnowUtil.getSnowflaskNextId());
                    bedSeat.setIndex(String.valueOf(k));
                    bedSeat.setFloorsCode(roomCarriage.getFloorsCode());
                    bedSeat.setRoomCode(roomCarriage.getId());
                    bedSeat.setRoomName(roomCarriage.getName());
                    bedSeat.setStatus(1);
                    bedSeat.setCanSelect(0);
                    bedSeatMapper.insert(bedSeat);
                }
            }
        }
    }
}
