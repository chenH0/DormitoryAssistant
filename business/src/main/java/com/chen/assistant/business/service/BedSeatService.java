package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.domain.BedSeat;
import com.chen.assistant.business.domain.BedSeatExample;
import com.chen.assistant.business.mapper.BedSeatMapper;
import com.chen.assistant.business.req.BedSeatQueryReq;
import com.chen.assistant.business.req.BedSeatSaveReq;
import com.chen.assistant.business.resp.BedSeatQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(BedSeatService.class);

    @Resource
    private BedSeatMapper bedSeatMapper;

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
        bedSeatExample.setOrderByClause("id desc");
        BedSeatExample.Criteria criteria = bedSeatExample.createCriteria();

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
}
