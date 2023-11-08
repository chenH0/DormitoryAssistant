package com.chen.assistant.member.mapper;

import com.chen.assistant.member.domain.Household;
import com.chen.assistant.member.domain.HouseholdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HouseholdMapper {
    long countByExample(HouseholdExample example);

    int deleteByExample(HouseholdExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Household record);

    int insertSelective(Household record);

    List<Household> selectByExample(HouseholdExample example);

    Household selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Household record, @Param("example") HouseholdExample example);

    int updateByExample(@Param("record") Household record, @Param("example") HouseholdExample example);

    int updateByPrimaryKeySelective(Household record);

    int updateByPrimaryKey(Household record);
}