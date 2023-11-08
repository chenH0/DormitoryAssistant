package com.chen.assistant.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.member.domain.Household;
import com.chen.assistant.member.mapper.HouseholdMapper;
import com.chen.assistant.member.req.HouseholdSaveReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HouseholdService {
    @Resource
    private HouseholdMapper householdMapper;
    public void save(HouseholdSaveReq req){
        DateTime now = DateTime.now();
        Household household = BeanUtil.copyProperties(req, Household.class);
        household.setId(SnowUtil.getSnowflaskNextId());
        household.setCreateTime(now);
        household.setUpdateTime(now);
        householdMapper.insert(household);
    }
}
