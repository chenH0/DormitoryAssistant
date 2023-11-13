package com.chen.assistant.business.mapper;

import com.chen.assistant.business.domain.Parti;
import com.chen.assistant.business.domain.PartiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PartiMapper {
    long countByExample(PartiExample example);

    int deleteByExample(PartiExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Parti record);

    int insertSelective(Parti record);

    List<Parti> selectByExample(PartiExample example);

    Parti selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Parti record, @Param("example") PartiExample example);

    int updateByExample(@Param("record") Parti record, @Param("example") PartiExample example);

    int updateByPrimaryKeySelective(Parti record);

    int updateByPrimaryKey(Parti record);
}