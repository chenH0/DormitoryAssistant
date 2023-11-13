package com.chen.assistant.business.mapper;

import com.chen.assistant.business.domain.Storys;
import com.chen.assistant.business.domain.StorysExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StorysMapper {
    long countByExample(StorysExample example);

    int deleteByExample(StorysExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Storys record);

    int insertSelective(Storys record);

    List<Storys> selectByExample(StorysExample example);

    Storys selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Storys record, @Param("example") StorysExample example);

    int updateByExample(@Param("record") Storys record, @Param("example") StorysExample example);

    int updateByPrimaryKeySelective(Storys record);

    int updateByPrimaryKey(Storys record);
}