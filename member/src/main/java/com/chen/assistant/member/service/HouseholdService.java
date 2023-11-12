package com.chen.assistant.member.service;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.member.domain.Household;
import com.chen.assistant.member.domain.HouseholdExample;
import com.chen.assistant.member.enums.HouseholdStatusEnum;
import com.chen.assistant.member.mapper.HouseholdMapper;
import com.chen.assistant.member.req.HouseholdQueryReq;
import com.chen.assistant.member.req.HouseholdSaveReq;
import com.chen.assistant.member.resp.HouseholdResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdService {
    private static final Logger LOG = LoggerFactory.getLogger(HouseholdService.class);
    @Resource
    private HouseholdMapper householdMapper;
    public void save(HouseholdSaveReq req){
        DateTime now = DateTime.now();
        Household household = BeanUtil.copyProperties(req, Household.class);
        household.setMemberId(LoginMemberContext.getId());
        household.setUpdateTime(now);
        if(true){
            household.setStatus(1); // 自动审核通过
        }
        householdMapper.updateByPrimaryKeySelective(household);
    }

    public PageResp<HouseholdResp> show(HouseholdQueryReq req) {
        HouseholdExample householdExample = new HouseholdExample();
        householdExample.setOrderByClause("member_id desc");
        householdExample.createCriteria().andMemberIdEqualTo(req.getMemberId());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Household> households = householdMapper.selectByExample(householdExample);
        PageInfo<Household> pageInfo = new PageInfo<>(households);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<HouseholdResp> list = BeanUtil.copyToList(households, HouseholdResp.class);

        PageResp<HouseholdResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
}
