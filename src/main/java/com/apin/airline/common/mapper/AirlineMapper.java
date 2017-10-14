package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.*;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线资源管理相关DAL
 */
@Mapper
public interface AirlineMapper extends Mapper {

    /**
     * 查询国家基础数据(分页,按国家代码排序)
     *
     * @param offset 偏移量
     * @param count  记录数量
     * @return 国家基础数据集合
     */
    @Select("SELECT * FROM msd_country WHERE is_invalid=0 ORDER BY country_code LIMIT #{offset},#{count};")
    List<Country> getCountries(Integer offset, Integer count);

    /**
     * 新增国家基础数据
     *
     * @param country 国家基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_country(id,zone_code,country_code,country_name) " +
            "VALUES (#{id},#{zoneCode},#{countryCode},#{countryName});")
    Integer addCountry(Country country);

    /**
     * 删除国家基础数据(逻辑删除)
     *
     * @param id 国家基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_country SET is_invalid=1 WHERE id=#{id};")
    Integer deleteCountry(String id);

    /**
     * 编辑国家基础数据
     *
     * @param country 国家基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_country SET zone_code=#{zoneCode},country_code=#{countryCode},country_name=#{countryName} WHERE id=#{id};")
    Integer updateCountry(Country country);

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param offset 偏移量
     * @param count  记录数量
     * @return 城市基础数据集合
     */
    @Select("SELECT * FROM msd_city WHERE is_invalid=0 ORDER BY en_name LIMIT #{offset},#{count};")
    List<City> getCities(Integer offset, Integer count);

    /**
     * 新增城市基础数据
     *
     * @param city 城市基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_city(id,country_id,city_code,city_name,en_name,pinyin_first,img_url,description,longitude,latitude) " +
            "VALUES (#{id},#{countryId},#{cityCode},#{cityName},#{enName},#{pinyinFirst},#{imgUrl},#{description},#{longitude},#{latitude});")
    Integer addCity(City city);

    /**
     * 删除城市基础数据(逻辑删除)
     *
     * @param id 城市基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_city SET is_invalid=1 WHERE id=#{id};")
    Integer deleteCity(String id);

    /**
     * 编辑城市基础数据
     *
     * @param city 城市基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_city SET country_id=#{countryId},city_code=#{cityCode},city_name=#{cityName},en_name=#{enName},pinyin_first=#{pinyinFirst}," +
            "img_url=#{imgUrl},description=#{description},longitude=#{longitude},latitude=#{latitude} WHERE id=#{id};")
    Integer updateCity(City city);

    /**
     * 查询机场基础数据(分页,按三字码排序)
     *
     * @param offset 偏移量
     * @param count  记录数量
     * @return 机场基础数据集合
     */
    @Select("SELECT * FROM msd_airport WHERE is_invalid=0 ORDER BY iata_code LIMIT #{offset},#{count};")
    List<Airport> getAirports(Integer offset, Integer count);

    /**
     * 新增机场基础数据
     *
     * @param airport 机场基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_airport(id,city_code,iata_code,icao_code,airport_name,longitude,latitude,time_zone) " +
            "VALUES (#{id},#{cityCode},#{iataCode},#{icaoCode},#{airportName},#{longitude},#{latitude},#{timeZone});")
    Integer addAirport(Airport airport);

    /**
     * 删除机场基础数据(逻辑删除)
     *
     * @param id 机场基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_airport SET is_invalid=1 WHERE id=#{id};")
    Integer deleteAirport(String id);

    /**
     * 编辑机场基础数据
     *
     * @param airport 机场基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_airport SET city_code=#{cityCode},iata_code=#{iataCode},icao_code=#{icaoCode},airport_name=#{airportName}," +
            "longitude=#{longitude},latitude=#{latitude},time_zone=#{timeZone} WHEREid=#{id};")
    Integer updateAirport(Airport airport);

    /**
     * 查询航司基础数据(分页,按航司代码排序)
     *
     * @param offset 偏移量
     * @param count  记录数量
     * @return 航司基础数据集合
     */
    @Select("SELECT * FROM msd_airway WHERE is_invalid=0 ORDER BY iata_code LIMIT #{offset},#{count};")
    List<Airway> getAirwaies(Integer offset, Integer count);

    /**
     * 查询指定航班号的航司ID
     *
     * @param flightNo 航班号
     * @return 航司ID
     */
    @Select("SELECT id FROM msd_airway WHERE LEFT(#{flightNo},2)=iata_code")
    String getAirwayIdByFlightNo(String flightNo);

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
    @Insert("INSERT msd_airline(id,hash_key,flight_type,voyage,flight_number,flight_time,week_flights,creator_user,creator_user_id) " +
            "VALUES (#{id},#{hashKey},#{flightype},#{voyage},#{flightNumber},#{flightTime},#{weekFlights},#{creatorUser},#{creatorUserId});")
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
    Integer addSeats(String accountId, String flightId, String owner, Integer count);

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
    Integer updateAirLine(Line line);

    /**
     * 更新航线资源状态
     *
     * @param id     航线资源ID
     * @param status 航线资源状态
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline SET airlineStatus=#{status} WHERE id=#{id}")
    Integer updateAirLineStatus(String id, Byte status);

    /**
     * 修改库存
     *
     * @param flightId 航班资源ID
     * @param count    库存数量
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight SET seat_count=#{count} WHERE id=#{flightId};")
    Integer updateSeatCount(String flightId, Integer count);

    /**
     * 修改价格
     *
     * @param flightId   航班资源ID
     * @param adultPrice 成人票价
     * @param childPrice 儿童票价
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline_flight SET adult_price=#{adultPrice},child_price=#{childPrice} WHERE id=#{flightId};")
    Integer updatePrice(String flightId, BigDecimal adultPrice, BigDecimal childPrice);

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
}
