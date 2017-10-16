package com.apin.airline.common.mapper;

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

    @Select("select min(b.adult_price) price,sum(b.seat_count) seat from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
        "join msd_airline c on a.airline_id=c.id where c.voyage=#{voyage} group by c.voyage")
    FlightDetail selectFlight(String voyage);
    @Select("select img_url from msd_city where city_name=#{deatCity}")
    String selectCityImg(String destCity);
    @Select("select b.flight_date from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
            "join msd_airline c on a.airline_id=c.id where c.voyage=#{voyage} group by b.flight_date order by b.flight_date")
    List<String> selectFlightDates(String voyage);
    @SelectProvider(type = AspectSql.class,method = "selectFlightList")
    ResponseAirlineDto selectFlightList(CityList cityList);
    @Select(" SELECT min(b.adult_price) price, sum(b.seat_count) seat, b.flight_date, e.days FROM mbs_airline a  " +
            " JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
            " JOIN msd_airline c ON a.airline_id = c.id  " +
            " JOIN msd_airline_voyage e ON e.airline_id = c.id  " +
            " WHERE c.dep_city=#{depCity} and c.arr_city=#{arrCity}" +
            " AND b.flight_date = #{depDate}" +
            " AND e.trip_index = 1 GROUP BY e.days;")
    void selectFlights(CityList cityList);
    @Select("")
    void selectFlightDetail(CityList cityList);
}
