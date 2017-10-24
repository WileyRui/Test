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
    @Insert("")
    Integer  insert(Log log);
    @Select("select * from mbs_airline_log where airline_id = #{airlineId} order by created_time desc")
    public List<Log> searchLogs(String airlineId);
}
