package com.apin.airline.common.mapper;


import com.apin.airline.common.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/16
 */
@Mapper
public interface LogMapper  {
    @Insert("insert into mbs_airline_log (id,airline_id,event_source,event_code,event_name,message,operator_user,operator_id,created_time)" +
            " values (#{id},#{airlineId},#{eventSource},#{eventCode},#{eventName},#{message},#{operatorUser},#{operatorId},now())")
    Integer  insert(Log log);
    @Select("select * from mbs_airline_log where airline_id = #{airlineId} order by created_time desc")
    public List<Log> searchLogs(String airlineId);
}
