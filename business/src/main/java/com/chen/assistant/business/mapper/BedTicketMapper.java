package com.chen.assistant.business.mapper;

import com.chen.assistant.business.domain.BedTicket;
import com.chen.assistant.business.domain.BedTicketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BedTicketMapper {
    long countByExample(BedTicketExample example);

    int deleteByExample(BedTicketExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BedTicket record);

    int insertSelective(BedTicket record);

    List<BedTicket> selectByExample(BedTicketExample example);

    BedTicket selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BedTicket record, @Param("example") BedTicketExample example);

    int updateByExample(@Param("record") BedTicket record, @Param("example") BedTicketExample example);

    int updateByPrimaryKeySelective(BedTicket record);

    int updateByPrimaryKey(BedTicket record);
}