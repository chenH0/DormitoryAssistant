package com.chen.assistant.business.mapper;

import com.chen.assistant.business.domain.BedSeat;
import com.chen.assistant.business.domain.BedSeatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BedSeatMapper {
    long countByExample(BedSeatExample example);

    int deleteByExample(BedSeatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BedSeat record);

    int insertSelective(BedSeat record);

    List<BedSeat> selectByExample(BedSeatExample example);

    BedSeat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BedSeat record, @Param("example") BedSeatExample example);

    int updateByExample(@Param("record") BedSeat record, @Param("example") BedSeatExample example);

    int updateByPrimaryKeySelective(BedSeat record);

    int updateByPrimaryKey(BedSeat record);
}