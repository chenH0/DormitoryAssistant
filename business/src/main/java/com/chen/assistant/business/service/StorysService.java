package com.chen.assistant.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.chen.assistant.business.domain.Parti;
import com.chen.assistant.business.domain.PartiExample;
import com.chen.assistant.business.resp.PartiQueryResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.assistant.common.resp.PageResp;
import com.chen.assistant.common.util.SnowUtil;
import com.chen.assistant.business.domain.Storys;
import com.chen.assistant.business.domain.StorysExample;
import com.chen.assistant.business.mapper.StorysMapper;
import com.chen.assistant.business.req.StorysQueryReq;
import com.chen.assistant.business.req.StorysSaveReq;
import com.chen.assistant.business.resp.StorysQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorysService {

    private static final Logger LOG = LoggerFactory.getLogger(StorysService.class);

    @Resource
    private StorysMapper storysMapper;

    public void save(StorysSaveReq req) {
        DateTime now = DateTime.now();
        Storys storys = BeanUtil.copyProperties(req, Storys.class);
        if (ObjectUtil.isNull(storys.getId())) {
            storys.setId(SnowUtil.getSnowflaskNextId());
            storys.setUpdateTime(now);
            storys.setCode(storys.getName()+"楼-"+storys.getFloors());
            storys.setStatus(1);
            storysMapper.insert(storys);
        } else {
            storys.setUpdateTime(now);
            storysMapper.updateByPrimaryKey(storys);
        }
    }

    public PageResp<StorysQueryResp> queryList(StorysQueryReq req) {
        StorysExample storysExample = new StorysExample();
        storysExample.setOrderByClause("id desc");
        StorysExample.Criteria criteria = storysExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Storys> storysList = storysMapper.selectByExample(storysExample);

        PageInfo<Storys> pageInfo = new PageInfo<>(storysList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<StorysQueryResp> list = BeanUtil.copyToList(storysList, StorysQueryResp.class);

        PageResp<StorysQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        storysMapper.deleteByPrimaryKey(id);
    }

    public List<StorysQueryResp> queryAll() {
        StorysExample storysExample = new StorysExample();
        storysExample.setOrderByClause("code asc");
        List<Storys> partis = storysMapper.selectByExample(storysExample);
        return BeanUtil.copyToList(partis, StorysQueryResp.class);
    }

    public Storys selectByFloorCode(String floorCode){
        StorysExample storysExample = new StorysExample();
        StorysExample.Criteria criteria = storysExample.createCriteria();
        criteria.andCodeEqualTo(floorCode);
        return storysMapper.selectByExample(storysExample).get(0);
    }
}
