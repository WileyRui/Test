package com.apin.airline.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AirportMapper extends Mapper {
    /**
     * 机场三字码查询城市三字码
     *
     * @param iataCode
     * @return
     */
    @Select("SELECT ma.city_code cityCode FROM msd_airport ma WHERE ma.iata_code = #{iataCode}")
    String findCityCode(@Param(value = "iataCode") String iataCode);
}
