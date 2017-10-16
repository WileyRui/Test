package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.*;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线资源管理相关DAL
 */
@Mapper
public interface AirlineMapper extends Mapper {

    /**
     * 新增操作日志
     *
     * @param log 操作日志数据实体
     * @return 受影响行数
     */
    @Insert("INSERT mbs_airline_log(id,airline_id,event_source,event_code,event_name,message,operator_user,operator_id) " +
            "VALUES (#{id},#{airlineId},#{eventSource},#{eventCode},#{eventName},#{message},#{operatorUser},#{operatorId});")
    Integer addLog(Log log);

    /**
     * 新增航班信息数据
     *
     * @param flightInfos 航班信息数据集合
     * @return 受影响行数
     */
    @Insert("<script>INSERT msd_airline_info(id,flight_no,flight_company,fcategory,flight_dep,flight_arr," +
            "flight_dep_airport,flight_arr_airport,flight_depcode,flight_arrcode,flight_deptime_plan_date," +
            "flight_arrtime_plan_date,stop_flag,flights) VALUES " +
            "<foreach collection = \"list\" item = \"item\" index = \"index\" separator = \",\"> " +
            "(#{item.id},#{item.flightNo},#{item.flightCompany},#{item.fcategory},#{item.flightDep},#{item.flightArr}," +
            "#{item.flightDepAirport},#{item.flightArrAirport},#{item.flightDepcode},#{item.flightArrcode}," +
            "#{item.flightDeptimePlanDate},#{item.flightArrtimePlanDate},#{item.stopFlag},#{item.flights}) " +
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
     * 查询指定ID的航班信息
     *
     * @param id 航班信息ID
     * @return 航班信息
     */
    @Select("SELECT * FROM msd_airline_info WHERE id=#{id};")
    FlightInfo getFlightInfo(String id);

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
    @Insert("INSERT msd_airline(id,hash_key,flight_type,dep_city,arr_city,days,voyage,flight_number,flight_time,week_flights,creator_user,creator_user_id) " +
            "VALUES (#{id},#{hashKey},#{flightType},#{depCity},#{arrCity},#{days},#{voyage},#{flightNumber},#{flightTime},#{weekFlights},#{creatorUser},#{creatorUserId});")
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
    Integer addVoyages(List<Voyage> voyages);

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
     * 查询指定航班号的航司ID
     *
     * @param flightNo 航班号
     * @return 航司ID
     */
    @Select("SELECT id FROM msd_airway WHERE LEFT(#{flightNo},2)=iata_code")
    String getAirwayIdByFlightNo(String flightNo);

    /**
     * 机场三字码查询城市三字码
     *
     * @param iataCode
     * @return
     */
    @Select("SELECT city_code FROM msd_airport WHERE iata_code = #{iataCode};")
    String findCityCode(String iataCode);

    /**
     * 根据机场三字码查询城市名称
     *
     * @param iataCode
     * @return cityName
     */
    @Select("SELECT c.city_name FROM msd_airport a LEFT JOIN msd_city c ON a.city_code=c.city_code " +
            "WHERE a.iata_code=#{iataCode};")
    String findCityNameByIataCode(String iataCode);

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
            "FROM msd_airline_voyage v JOIN msd_airline_info i ON i.id=v.flight_info_id WHERE v.airline_id=#{id}")
    List<AirlineDetail> getVoyages(String id);

    /**
     * 新增航线资源
     *
     * @param line 航线资源数据
     * @return 受影响行数
     */
    @Insert("INSERT mbs_airline(id,account_id,airline_no,supplier_name,airway_id,airline_id,res_type,seat_type," +
            "departure_start,departure_end,seat_count,deposit_amount,adult_price,child_price,pay_advance,ticket_advance," +
            "recovery_advance,free_bag,weight_limit,alert_advance,alert_rate,can_return,can_change,can_sign,airline_status," +
            "manager,manager_id,creator_user,creator_user_id) " +
            "SELECT #{id},#{accountId},(select airline_no + 1 from mbs_airline ORDER BY created_time DESC limit 1)," +
            "#{supplierName},#{airwayId},#{airlineId},#{resType},#{seatType},#{departureStart},#{departureEnd}," +
            "#{seatCount},#{depositAmount},#{adultPrice},#{childPrice},#{payAdvance},#{ticketAdvance},#{recoveryAdvance}," +
            "#{freeBag},#{weightLimit},#{alertAdvance},#{alertRate},#{canReturn},#{canChange},#{canSign},#{airlineStatus}," +
            "#{manager},#{managerId},#{creatorUser},#{creatorUserId}")
    Integer addLine(Line line);

    /**
     * 删除航线资源
     *
     * @param id 航线资源ID
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline SET is_invalid=1 WHERE id=#{id};")
    Integer deleteLine(String id);

    /**
     * 更新航线资源
     *
     * @param line 航线资源数据
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline SET " +
            "res_type=#{resType},seat_type=#{seatType},seat_count=#{seatCount},deposit_amount=#{depositAmount}," +
            "adult_price=#{adultPrice},child_price=#{childPrice},free_bag=#{freeBag},weight_limit=#{weightLimit}," +
            "alert_advance=#{alertAdvance},alert_rate=#{alertRate},can_return=#{canReturn},can_change=#{canChange}," +
            "can_sign=#{canSign},manager=#{manager},manager_id=#{managerId} WHEREid=#{id};")
    Integer updateLine(Line line);

    /**
     * 更新航线资源状态
     *
     * @param id     航线资源ID
     * @param status 航线资源状态
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline SET airline_status=#{status} WHERE id=#{id}")
    Integer updateAirLineStatus(@Param("id") String id, @Param("status") Byte status);

    /**
     * 查询指定ID的航线资源数据
     *
     * @param id 航线资源ID
     * @return 航线资源数据
     */
    @Select("SELECT * FROM mbs_airline WHERE id=#{id};")
    Line getLine(String id);

    /**
     * 新增航班资源
     *
     * @param flights 航班资源数据集合
     * @return 受影响行数
     */
    @Insert("<script>INSERT mbs_airline_flight(id,airline_id,sell_type,flight_date,seat_count,adult_price," +
            "child_price,alert_threshold,alert_date,recovery_date,ticket_date) VALUES " +
            "<foreach collection = \"list\" item = \"item\" index = \"index\" separator = \",\"> " +
            "(#{item.id},#{item.airlineId},#{item.sellType},#{item.flightDate},#{item.seatCount},#{item.adultPrice}," +
            "#{item.childPrice},#{item.alertThreshold},#{item.alertDate},#{item.recoveryDate},#{item.ticketDate}) " +
            "</foreach></script>")
    Integer addLineFlights(List<Flight> flights);

    /**
     * 修改库存
     *
     * @param flightId 航班资源ID
     * @param count    库存数量
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight SET seat_count=#{count} WHERE id=#{id};")
    Integer updateSeatCount(@Param("id") String flightId, @Param("count") Integer count);

    /**
     * 修改价格
     *
     * @param airlineId  航线资源ID
     * @param dates      航班日期集合
     * @param adultPrice 成人票价
     * @param childPrice 儿童票价
     * @return 受影响行数
     */
    @Update("<script>UPDATE mbs_airline_flight SET adult_price=#{adultPrice},child_price=#{childPrice} " +
            "WHERE airline_id=#{id} AND flight_date in" +
            "<foreach collection = \"dates\" item = \"item\" index = \"index\" pen=\"(\" separator=\",\" close=\")\"> " +
            "#{item}" +
            "</foreach></script>")
    Integer updatePrice(@Param("id") String airlineId, @Param("dates") List<Date> dates,
                        @Param("adultPrice") BigDecimal adultPrice, @Param("childPrice") BigDecimal childPrice);

    /**
     * 新增舱位资源
     *
     * @param accountId 账户ID
     * @param flightId  航班资源ID
     * @param owner     拥有者名称
     * @param count     舱位数量
     * @return 受影响行数
     */
    @Insert("INSERT mbs_airline_flight_seat(id,account_id,flight_id,owner,owner_id) " +
            "SELECT (REPLACE(UUID(),'-',''),#{accountId},#{flightId},#{owner},#{accountId} " +
            "FROM msd_airline_info LIMIT #{count};")
    Integer addSeats(@Param("accountId") String accountId, @Param("flightId") String flightId,
                     @Param("owner") String owner, @Param("count") Integer count);

    /**
     * 分配舱位
     *
     * @param seat     舱位数据
     * @param flightId 航班资源ID
     * @param count    分配数量
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight_seat " +
            "SETowner`=#{owner},owner_id=#{ownerId},divider=#{divider},divider_id=#{dividerId},assigned_time=now() " +
            "WHERE flight_id=#{flightId} AND owner_id=account_id AND seat_status=0 LIMIT #{count};")
    Integer assignSeat(@Param("seat") Seat seat, @Param("flightId") String flightId, @Param("count") Integer count);

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
    Integer recoverSeat(@Param("flightId") String flightId, @Param("ownerId") String ownerId, @Param("count") Integer count);

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
     * 更新乘客信息
     *
     * @param seats 舱位数据实体集合
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight_seat " +
            "SET parent_id=#{parentId},passenger_type=#{passengerType},passenger_name=#{passengerName},cred_type=#{credType},cred_number=#{credNumber}," +
            "gender=#{gender},birthday=#{birthday},nation=#{nation},expire_time=#{expireTime},issue_place=#{issuePlace} WHERE id=#{id};")
    Integer updatePassenger(List<Seat> seats);

    /**
     * 更新票号信息
     *
     * @param seats 舱位数据实体集合
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight_seat SET grade=#{grade},ticket=#{ticket} WHERE id=#{id};")
    Integer updateTicket(List<Seat> seats);

    /**
     * 获取要更新乘客信息的舱位数据
     *
     * @param flightId 航班资源ID
     * @param ownerId  资源原拥有者ID
     * @param count    销售数量
     * @return 舱位数据集合
     */
    @Select("SELECT * FROM mbs_airline_flight_seat " +
            "WHERE flight_id=#{flightId} AND owner_id=#{ownerId} AND seat_status<2 " +
            "ORDER BY seat_status DESC LIMIT #{count};")
    List<Seat> getSelledSeats(String flightId, String ownerId, Integer count);
}
