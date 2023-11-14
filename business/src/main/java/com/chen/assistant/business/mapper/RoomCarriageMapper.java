package com.chen.assistant.business.mapper;

import com.chen.assistant.business.domain.RoomCarriage;
import com.chen.assistant.business.domain.RoomCarriageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomCarriageMapper {
    long countByExample(RoomCarriageExample example);

    int deleteByExample(RoomCarriageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoomCarriage record);

    int insertSelective(RoomCarriage record);

    List<RoomCarriage> selectByExample(RoomCarriageExample example);

    RoomCarriage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoomCarriage record, @Param("example") RoomCarriageExample example);

    int updateByExample(@Param("record") RoomCarriage record, @Param("example") RoomCarriageExample example);

    int updateByPrimaryKeySelective(RoomCarriage record);

    int updateByPrimaryKey(RoomCarriage record);
}