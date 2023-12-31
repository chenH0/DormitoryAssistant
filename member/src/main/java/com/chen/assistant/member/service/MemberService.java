package com.chen.assistant.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.chen.assistant.common.exception.BusinessException;
import com.chen.assistant.common.exception.BusinessExceptionEnum;
import com.chen.assistant.common.util.JwtUtil;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.member.domain.Household;
import com.chen.assistant.member.domain.Member;
import com.chen.assistant.member.domain.MemberExample;
import com.chen.assistant.member.mapper.HouseholdMapper;
import com.chen.assistant.member.req.MemberLoginReq;
import com.chen.assistant.member.req.MemberRegisterReq;
import com.chen.assistant.member.mapper.MemberMapper;
import com.chen.assistant.member.req.MemberSendCodeReq;
import com.chen.assistant.member.resp.MemberLoginResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;
    
    @Resource
    private HouseholdMapper householdMapper;
    
    public int count() {
        return (int) memberMapper.countByExample(null);
    }
    public long register(MemberRegisterReq req){
        String mobile = req.getMobile();
        Member list = selectMembers(mobile);
        if(ObjectUtil.isNotNull(list)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflaskNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
    public void sendCode(MemberSendCodeReq req){
        String mobile = req.getMobile();
        Member list = selectMembers(mobile);
        if(ObjectUtil.isNull(list)){
            LOG.info("手机号不存在，输入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflaskNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
            Household household = new Household();
            DateTime now = DateTime.now();
            household.setMemberId(member.getId());
            household.setCreateTime(now);
            household.setUpdateTime(now);
            household.setStatus(0);
            household.setRoom("未分配");
            householdMapper.insert(household);
        }else {
            LOG.info("手机号存在");
        }
        String code = RandomUtil.randomString(4);
        code = "8888";
        LOG.info("验证码:{}", code);
    }
    public MemberLoginResp login(MemberLoginReq req){
        String mobile = req.getMobile();
        Member memberDB = selectMembers(mobile);
        if(ObjectUtil.isNull(memberDB)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        if (!req.getCode().equals("8888")) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_CODE_NOT_EXIST);
        }
        LOG.info("登录成功");
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }

    private Member selectMembers(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
