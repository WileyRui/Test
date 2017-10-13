package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.*;
import org.apache.ibatis.annotations.*;

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
            "#{item.flightDepAirport},#{item.flightArrAirport},#{item.flightDepcode},#{item.flightArrcode},#{item.flightDeptimePlanDate}," +
            "#{item.flightArrtimePlanDate},#{item.stopFlag},#{item.flights},#{item.updateTime},#{item.createdTime}) " +
            "</foreach></script>")
    Integer addFlightInfo(List<FlightInfo> flightInfos);

    /**
     * 删除指定航班号的航班信息数据
     *
     * @param flightNo 航班号
     * @return 受影响行数
     */
    @Delete("DELETE FROM msd_airline_info WHERE flight_no=#{flightNo};")
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

    /**
     * 查询指定航线基础数据ID的航程明细数据
     *
     * @param id 航线基础数据ID
     * @return 航程明细数据集合
     */
    @Select("SELECT v.id,v.airline_id,v.trip_index,i.flight_company,i.flight_no,i.flight_dep_airport," +
            "i.flight_arr_airport,i.flight_deptime_plan_date,i.flight_arrtime_plan_date,i.stop_flag,i.flights " +
            "FROM msd_airline_voyage v JOIN msd_airline_info i ON i.id=v.airline_id WHERE v.airline_id=#{id}")
    List<AirlineDetail> getVoyages(String id);

    /**
     * 分配舱位
     *
     * @param seat      舱位数据
     * @param flightId  航班资源ID
     * @param count     分配数量
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight_seat " +
            "SET `owner`=#{owner},owner_id=#{ownerId},divider=#{divider},divider_id=#{dividerId},assigned_time=now() " +
            "WHERE flight_id=#{flightId} AND owner_id=account_id AND seat_status=0 LIMIT #{count};")
    Integer assignSeat(Seat seat, String flightId, Integer count);

    /**
     * 回收舱位
     *
     * @param flightId 航班资源ID
     * @param ownerId  资源原拥有者ID
     * @param count    回收数量
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight_seat s JOIN mbs_airline a ON a.account_id=s.account_id " +
            "SET s.`owner`=a.supplier_name,s.owner_id=s.account_id,s.seat_status=0 " +
            "WHERE s.flight_id=#{flightId} AND s.owner_id=#{ownerId} AND s.seat_status=0 LIMIT #{count};")
    Integer recoverSeat(String flightId, String ownerId, Integer count);

    /**
     * 强制回收舱位
     *
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline a JOIN mbs_airline_flight f ON f.airline_id=a.id AND f.recovery_date<now() " +
            "JOIN mbs_airline_flight_seat s ON s.flight_id=f.id AND s.seat_status<2" +
            "SET s.`owner`=a.supplier_name,s.owner_id=s.account_id,s.seat_status=0")
    Integer recoverSeatForced();

    /**
     * 售出舱位
     *
     * @param flightId 航班资源ID
     * @param ownerId  资源原拥有者ID
     * @param count    销售数量
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight_seat SET seat_status=0 " +
            "WHERE flight_id=#{flightId} AND owner_id=#{ownerId} AND seat_status=1;" +
            "UPDATE mbs_airline_flight_seat SET seat_status=1 " +
            "WHERE flight_id=#{flightId} AND owner_id=#{ownerId} AND seat_status=0 LIMIT #{count};")
    Integer sellSeat(String flightId, String ownerId, Integer count);

    /**
     * 新增航线数据
     *
     * @param line 航线数据
     * @return 受影响行数
     */
    @Insert("INSERT mbs_airline(id, account_id, airline_no, airline_id, airway_id, seat_type," +
            " departure_start, departure_end, seat_count, adult_price, child_price, ticket_advance, " +
            " recovery_advance, free_bag, weight_limit, alert_advance, alert_rate, can_return, can_change," +
            " can_sign, airline_status, is_invalid, manager, manager_id, creator_user, creator_user_id, created_time)"+
            " VALUES(#{id}, #{accountId}, #{airlineNo}, #{airlineId}, #{airwayId}, #{seatType}, #{departureStart}," +
            " #{departureEnd}, #{seatCount}, #{adultPrice}, #{childPrice}, #{ticketAdvance}, #{recoveryAdvance}, #{freeBag}," +
            " #{weightLimit}, #{alertAdvance}, #{alertRate}, #{canReturn}, #{canChange}, #{canSign}, #{airlineStatus}," +
            " #{isInvalid}, #{manager}, #{managerId}, #{creatorUser}, #{creatorUser_id}, #{createdTime})")
    Integer addline(Line line);

    /**
     * 查询最新的一条数据
     *
     * @return
     */
    @Select("select airline_no from mbs_airline ORDER BY created_time DESC limit 0,1")
    String findNew();
}
