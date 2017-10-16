package com.apin.airline.common.mapper;

import com.apin.airline.flight.dto.AirlineInfo;
import com.apin.airline.flight.dto.CityList;
import com.apin.airline.flight.dto.FlightDetail;
import com.apin.airline.flight.dto.ResponseAirlineDto;
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

    @Select("select min(b.adult_price) basePrice,c.dep_city depCity,c.arr_city arrCity, sum(b.seat_count)  remainCount,sum(a.seat_count) total from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
        " join msd_airline c on a.airline_id=c.id where c.voyage=#{voyage} group by c.voyage")
    FlightDetail selectFlight(String voyage);
    @Select("select img_url from msd_city where city_name=#{deatCity}")
    String selectCityImg(String destCity);
    @Select("select b.flight_date from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
            " join msd_airline c on a.airline_id=c.id where c.voyage=#{voyage} group by b.flight_date order by b.flight_date")
    List<String> selectFlightDates(String voyage);
    @SelectProvider(type = AspectSql.class,method = "selectFlightList")
    List<ResponseAirlineDto> selectFlightList(CityList cityList);
    @SelectProvider(type = AspectSql.class,method = "selectFlights")
    List<FlightDetail> selectFlights(CityList cityList);
    @Select("SELECT SUBSTRING_INDEX(c.flight_number, '<', 1) depNum," +
            "SUBSTRING_INDEX(c.flight_number, '>', - 1) arrNum,sum(a.seat_count) AS total," +
            "sum(b.seat_count) remainCount, min(b.adult_price) basePrice FROM mbs_airline a  " +
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
            "a.flight_arr_airport arrAirport from msd_airline_info a join msd_airway b on a.flight_company=b.company_name where flight_no=#{flightNum}")
    AirlineInfo selectByFlightNum(String flightNum);
}
