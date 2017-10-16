package com.apin.airline.common.mapper;


import com.apin.airline.common.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/16
 */
@Mapper
public interface LogMapper  {
    @Insert("")
    Integer  insert(Log log);
}
