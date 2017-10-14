package com.apin.airline.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper extends Mapper {
    /**
     * 城市三字码获取城市名称
     *
     * @param iataCode
     * @return cityName
     */
    @Select("SELECT mc.city_name as cityName FROM msd_airport ma LEFT JOIN msd_city mc ON ma.city_code = mc.city_code " +
            "WHERE ma.iata_code =#{iataCode}")
    String findCityNameByIataCode(@Param("iataCode") String iataCode);
}
