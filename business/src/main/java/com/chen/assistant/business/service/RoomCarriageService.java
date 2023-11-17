package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.chen.assistant.business.enums.RoomTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.domain.RoomCarriage;
import com.chen.assistant.business.domain.RoomCarriageExample;
import com.chen.assistant.business.mapper.RoomCarriageMapper;
import com.chen.assistant.business.req.RoomCarriageQueryReq;
import com.chen.assistant.business.req.RoomCarriageSaveReq;
import com.chen.assistant.business.resp.RoomCarriageQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(RoomCarriageService.class);

    @Resource
    private RoomCarriageMapper roomCarriageMapper;

    public void save(RoomCarriageSaveReq req) {
        DateTime now = DateTime.now();
        RoomCarriage roomCarriage = BeanUtil.copyProperties(req, RoomCarriage.class);
        if (ObjectUtil.isNull(roomCarriage.getId())) {
            roomCarriage.setId(SnowUtil.getSnowflaskNextId());
            roomCarriage.setCreateTime(now);
            roomCarriage.setUpdateTime(now);
            roomCarriage.setName(roomCarriage.getFloorsCode()+"层"+"-"+roomCarriage.getIndex()+"房"+"-"+RoomTypeEnum.findEnumByCode(roomCarriage.getBedType()));
            roomCarriageMapper.insert(roomCarriage);
        } else {
            roomCarriage.setUpdateTime(now);
            roomCarriageMapper.updateByPrimaryKey(roomCarriage);
        }
    }

    public PageResp<RoomCarriageQueryResp> queryList(RoomCarriageQueryReq req) {
        RoomCarriageExample roomCarriageExample = new RoomCarriageExample();
        roomCarriageExample.setOrderByClause("floors_code asc, 'index' asc, bed_type asc");
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
}
