package com.apin.airline.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 航司表数据操作接口
 */
@Mapper
public interface AirWayMapper extends Mapper {

    /**
     * 根据航司简称查询航司id
     *
     * @param iataCode
     * @return
     */
    @Select("select id from msd_airway where iata_code = #{iataCode}")
    String findByIataCode(@Param("iataCode") String iataCode);
}
