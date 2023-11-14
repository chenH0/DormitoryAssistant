package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.domain.Parti;
import com.chen.assistant.business.domain.PartiExample;
import com.chen.assistant.business.mapper.PartiMapper;
import com.chen.assistant.business.req.PartiQueryReq;
import com.chen.assistant.business.req.PartiSaveReq;
import com.chen.assistant.business.resp.PartiQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartiService {

    private static final Logger LOG = LoggerFactory.getLogger(PartiService.class);

    @Resource
    private PartiMapper partiMapper;

    public void save(PartiSaveReq req) {
        DateTime now = DateTime.now();
        Parti parti = BeanUtil.copyProperties(req, Parti.class);
        if (ObjectUtil.isNull(parti.getId())) {
            parti.setId(SnowUtil.getSnowflaskNextId());
            parti.setCreateTime(now);
            parti.setUpdateTime(now);
            partiMapper.insert(parti);
        } else {
            parti.setUpdateTime(now);
            partiMapper.updateByPrimaryKey(parti);
        }
    }

    public PageResp<PartiQueryResp> queryList(PartiQueryReq req) {
        PartiExample partiExample = new PartiExample();
        partiExample.setOrderByClause("id desc");
        PartiExample.Criteria criteria = partiExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Parti> partiList = partiMapper.selectByExample(partiExample);

        PageInfo<Parti> pageInfo = new PageInfo<>(partiList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<PartiQueryResp> list = BeanUtil.copyToList(partiList, PartiQueryResp.class);

        PageResp<PartiQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        partiMapper.deleteByPrimaryKey(id);
    }

    public List<PartiQueryResp> queryAll() {
        PartiExample partiExample = new PartiExample();
        partiExample.setOrderByClause("name asc");
        List<Parti> partis = partiMapper.selectByExample(partiExample);
        return BeanUtil.copyToList(partis, PartiQueryResp.class);
    }
}
