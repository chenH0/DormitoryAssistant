package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.domain.BedTicket;
import com.chen.assistant.business.domain.BedTicketExample;
import com.chen.assistant.business.mapper.BedTicketMapper;
import com.chen.assistant.business.req.BedTicketQueryReq;
import com.chen.assistant.business.req.BedTicketSaveReq;
import com.chen.assistant.business.resp.BedTicketQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedTicketService {

    private static final Logger LOG = LoggerFactory.getLogger(BedTicketService.class);

    @Resource
    private BedTicketMapper bedTicketMapper;

    public void save(BedTicketSaveReq req) {
        DateTime now = DateTime.now();
        BedTicket bedTicket = BeanUtil.copyProperties(req, BedTicket.class);
        if (ObjectUtil.isNull(bedTicket.getId())) {
            bedTicket.setId(SnowUtil.getSnowflaskNextId());
            bedTicket.setCreateTime(now);
            bedTicket.setUpdateTime(now);
            bedTicketMapper.insert(bedTicket);
        } else {
            bedTicket.setUpdateTime(now);
            bedTicketMapper.updateByPrimaryKey(bedTicket);
        }
    }

    public PageResp<BedTicketQueryResp> queryList(BedTicketQueryReq req) {
        BedTicketExample bedTicketExample = new BedTicketExample();
        bedTicketExample.setOrderByClause("id asc");
        BedTicketExample.Criteria criteria = bedTicketExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<BedTicket> bedTicketList = bedTicketMapper.selectByExample(bedTicketExample);

        PageInfo<BedTicket> pageInfo = new PageInfo<>(bedTicketList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<BedTicketQueryResp> list = BeanUtil.copyToList(bedTicketList, BedTicketQueryResp.class);

        PageResp<BedTicketQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        bedTicketMapper.deleteByPrimaryKey(id);
    }
}
