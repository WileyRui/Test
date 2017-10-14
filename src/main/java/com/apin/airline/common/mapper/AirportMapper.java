package com.apin.airline.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AirportMapper extends Mapper {
    /**
     * 机场三字码查询城市三字码
     *
     * @param iataCode
     * @return
     */
    @Select("SELECT city_code cityCode FROM msd_airport WHERE iata_code = #{iataCode}")
    String findCityCode(String iataCode);
}
