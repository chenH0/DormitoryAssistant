package com.chen.assistant.member.service;

import cn.hutool.core.collection.CollUtil;
import com.chen.assistant.member.domain.Member;
import com.chen.assistant.member.domain.MemberExample;
import com.chen.assistant.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Resource
    private MemberMapper memberMapper;
    public int count() {
        return (int) memberMapper.countByExample(null);
    }
    public long register(String mobilePhone){
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobilePhone);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isNotEmpty(list)){
            throw new RuntimeException("手机号已注册");
        }
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobilePhone);
        memberMapper.insert(member);
        return member.getId();
    }
}
