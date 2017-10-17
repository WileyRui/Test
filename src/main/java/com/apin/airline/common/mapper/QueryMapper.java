package com.apin.airline.common.mapper;

import com.apin.airline.flight.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/16
 * @remark 查询相关DAL
 */
@Mapper
public interface QueryMapper extends Mapper {

    @Select("select min(b.adult_price) basePrice,c.dep_city depCity,c.arr_city arrCity, sum(b.seat_count) remainCount,min(b.flight_date) startDate,max(b.flight_date) endDate  ,sum(a.seat_count) total from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
        " join msd_airline c on a.airline_id=c.id where c.dep_city=#{depCity} and c.arr_city=#{arrCity} and (#{flightType} is null or c.flight_type=#{flightType}) group by c.voyage")
    FlightDetail selectFlight(CityList cityList);
    @Select("select img_url from msd_city where city_name=#{deatCity}")
    String selectCityImg(String destCity);
    @Select("select b.flight_date retDate,min(b.adult_price) basePrice from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
            " join msd_airline c on a.airline_id=c.id where c.dep_city=#{depCity} and c.arr_city=#{arrCity}  and (#{flightType} is null or c.flight_type=#{flightType}) and SUBSTRING_INDEX(b.flight_date,'-',2)=#{month} group by b.flight_date order by b.flight_date")
    List<DayPrice> selectFlightDates(CityList cityList);
    @SelectProvider(type = AspectSql.class,method = "selectFlightList")
    List<ResponseAirlineDto> selectFlightList(CityList cityList);
    @SelectProvider(type = AspectSql.class,method = "selectFlights")
    List<FlightDetail> selectFlights(CityList cityList);
    @Select("select c.id airlineId,sum(a.seat_count) AS total," +
            "sum(b.seat_count) remainCount, min(b.adult_price) basePrice,e.days+1 days,DATE_ADD(b.flight_date,INTERVAL e.days DAY) retDate,c.flight_type flightType FROM mbs_airline a  " +
            "JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
            "JOIN msd_airline c ON a.airline_id = c.id  " +
            "JOIN msd_airline_voyage e ON e.airline_id = c.id  " +
            "WHERE  " +
            "  c.dep_city=#{depCity} and c.arr_city=#{arrCity}  " +
            "AND b.flight_date = #{depDate}  " +
            "AND e.days = #{day}  " +
            "GROUP BY c.flight_number  " +
            "ORDER BY  " +
            "  b.adult_price;")
    List<ResponseAirlineDto> selectFlightDetail(CityList cityList);
    @Select("select a.flight_company compName,b.logo_ico logo,a.flight_arrtime_plan_date arrTime,a.flight_deptime_plan_date depTime,a.flight_dep_airport depAirport," +
            "a.flight_arr_airport arrAirport,a.flight_no num from msd_airline_info a  " +
            " JOIN msd_airway b ON a.flight_company = b.company_name  " +
            " join msd_airline_voyage c on  c.flight_info_id=a.id  " +
            " join msd_airline e on e.id=c.airline_id  " +
            " WHERE c.airline_id=#{flightNum}")
    List<AirlineInfo> selectByFlightNum(String flightNum);
    @Select("SELECT  " +
            "   SUBSTRING_INDEX(b.flight_date,'-',2) retDate FROM  " +
            "   mbs_airline a  " +
            "JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
            "JOIN msd_airline c ON a.airline_id = c.id  " +
            "WHERE c.dep_city =#{depCity} AND c.arr_city =#{arrCity} AND  (#{flightType} is null or c.flight_type=#{flightType}) " +
            " GROUP BY SUBSTRING_INDEX(b.flight_date,'-',2)  " +
            " ORDER BY b.flight_date")
    List<String> selectMonthQuery(CityList cityList);
}
