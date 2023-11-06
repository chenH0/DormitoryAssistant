package com.chen.assistant.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.chen.assistant.common.exception.BusinessException;
import com.chen.assistant.common.exception.BusinessExceptionEnum;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.member.domain.Member;
import com.chen.assistant.member.domain.MemberExample;
import com.chen.assistant.member.req.MemberRegisterReq;
import com.chen.assistant.member.mapper.MemberMapper;
import com.chen.assistant.member.req.MemberSendCodeReq;
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
    public int count() {
        return (int) memberMapper.countByExample(null);
    }
    public long register(MemberRegisterReq req){
        MemberExample memberExample = new MemberExample();
        String mobile = req.getMobile();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isNotEmpty(list)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflaskNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
    public void sendCode(MemberSendCodeReq req){
        MemberExample memberExample = new MemberExample();
        String mobile = req.getMobile();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isEmpty(list)){
            LOG.info("手机号不存在，输入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflaskNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }else {
            LOG.info("手机号存在");
        }
        String code = RandomUtil.randomString(4);
        code = "8888";
        LOG.info("验证码:{}", code);
    }
}
