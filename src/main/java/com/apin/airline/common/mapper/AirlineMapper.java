package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.Airline;
import com.apin.airline.common.entity.AirlineDetail;
import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.common.entity.Voyage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线资源管理相关DAL
 */
@Mapper
public interface AirlineMapper extends Mapper {

    /**
     * 新增航班信息数据
     *
     * @param flightInfos 航班信息数据集合
     * @return 受影响行数
     */
    @Insert("<script>INSERT msd_airline_info(id,flight_no,flight_company,fcategory,flight_dep,flight_arr," +
            "flight_dep_airport,flight_arr_airport,flight_depcode,flight_arrcode,flight_deptime_plan_date," +
            "flight_arrtime_plan_date,stop_flag,flights,update_time,created_time) VALUES " +
            "<foreach collection = \"list\" item = \"item\" index = \"index\" separator = \",\"> " +
            "(#{item.id},#{item.flightNo},#{item.flightCompany},#{item.fcategory},#{item.flightDep},#{item.flightArr}," +
            "#{item.flightDepAirport},#{item.flightArrAirport},#{item.flightDepcode},#{item.flightArrcode},#{item.flightDeptime}," +
            "#{item.flightArrtime},#{item.stopFlag},#{item.flights},#{item.updateTime},#{item.createdTime}) " +
            "</foreach></script>")
    Integer addFlightInfo(List<FlightInfo> flightInfos);

    /**
     * 删除指定航班号的航班信息数据
     *
     * @param flightNo 航班号
     * @return 受影响行数
     */
    @Delete("DELETE from msd_airline_info WHERE flight_no=#{flightNo};")
    Integer deleteFlightInfo(String flightNo);

    /**
     * 查询指定航班号的航班信息
     *
     * @param flightNo 航班号
     * @return 航班信息集合
     */
    @Select("SELECT * FROM msd_airline_info WHERE flight_no=#{flightNo};")
    List<FlightInfo> getFlightInfos(String flightNo);

    /**
     * 新增航线基础数据
     *
     * @param airline 航线基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_airline(id,hash_key,flight_type,voyage,flight_number,flight_time,week_flights,is_invalid," +
            "creator_user,creator_user_id,update_time,created_time) " +
            "VALUES (#{id},#{hashKey},#{flightype},#{voyage},#{flightNumber},#{flightTime},#{weekFlights},#{invalid}," +
            "#{creatorUser},#{creatorUserId},#{updateTime},#{createdTime});")
    Integer addAirline(Airline airline);

    /**
     * 新增航程明细
     *
     * @param voyages 航程明细数据集合
     * @return 受影响行数
     */
    @Insert("<script>INSERT msd_airline_voyage(id,airline_id,flight_info_id,trip_index,days) VALUES " +
            "<foreach collection = \"list\" item = \"item\" index = \"index\" separator = \",\"> " +
            "(#{item.id},#{item.airlineId},#{item.flightInfoId},#{item.tripIndex},#{item.days}) " +
            "</foreach></script>")
    Integer addVoyage(List<Voyage> voyages);

    /**
     * 删除指定ID的航线基础数据
     *
     * @param id 航线基础数据ID
     * @return 受影响行数
     */
    @Delete("DELETE a,v FROM msd_airline a LEFT JOIN msd_airline_voyage v ON v.airline_id=a.id WHERE a.id=#{id};")
    Integer deleteAirline(String id);

    /**
     * 查询符合条件的航线基础数据
     *
     * @param hashKey 航程摘要字符串
     * @return 航线基础数据ID
     */
    @Select("SELECT id FROM msd_airline WHERE hash_key=#{hashKey} ORDER BY created_time LIMIT 1;")
    String getExistedAirline(String hashKey);

    /**
     * 查询指定ID的航线基础数据
     *
     * @param id 航线基础数据ID
     * @return 航线基础数据对象实体
     */
    @Select("SELECT * FROM msd_airline WHERE id=#{id}")
    Airline getAirlineById(String id);

    @Select("SELECT v.id,v.airline_id,v.trip_index,i.flight_company,i.flight_no,i.flight_dep_airport," +
            "i.flight_arr_airport,i.flight_deptime_plan_date,i.flight_arrtime_plan_date,i.stop_flag,i.flights " +
            "FROM msd_airline_voyage v JOIN msd_airline_info i ON i.id=v.airline_id WHERE v.airline_id=#{id}")
    List<AirlineDetail> getVoyages(String id);
}
