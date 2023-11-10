package com.chen.assistant.member.service;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.chen.assistant.common.context.LoginMemberContext;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.member.domain.Household;
import com.chen.assistant.member.domain.HouseholdExample;
import com.chen.assistant.member.mapper.HouseholdMapper;
import com.chen.assistant.member.req.HouseholdQueryReq;
import com.chen.assistant.member.req.HouseholdSaveReq;
import com.chen.assistant.member.resp.HouseholdResp;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdService {
    @Resource
    private HouseholdMapper householdMapper;
    public void save(HouseholdSaveReq req){
        DateTime now = DateTime.now();
        Household household = BeanUtil.copyProperties(req, Household.class);
        household.setMemberId(LoginMemberContext.getId());
        household.setCreateTime(now);
        household.setUpdateTime(now);
        householdMapper.insert(household);
    }

    public List<HouseholdResp> show(HouseholdQueryReq req) {
        HouseholdExample householdExample = new HouseholdExample();
        householdExample.createCriteria().andMemberIdEqualTo(req.getMemberId());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Household> households = householdMapper.selectByExample(householdExample);
        return BeanUtil.copyToList(households, HouseholdResp.class);
    }
}
