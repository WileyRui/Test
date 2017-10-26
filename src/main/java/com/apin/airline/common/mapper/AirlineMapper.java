package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.*;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.line.dto.NewLine;
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
     * 新增航班信息数据
     *
     * @param lineDetails 航班信息数据集合
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
    Integer addFlightInfo(List<LineDetail> lineDetails);

    /**f
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
    LineDetail getFlightInfo(String id);

    /**
     * 查询指定航班号的航班信息
     *
     * @param flightNo 航班号
     * @return 航班信息集合
     */
    @Select("SELECT * FROM msd_airline_info WHERE flight_no = #{flightNo};")
    List<LineDetail> getFlightInfos(String flightNo);

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
     * 删除指定ID的航线班次数据
     *
     * @param id 航线ID
     * @return 受影响行数
     */
    @Delete("DELETE FROM mbs_airline_flight WHERE airline_id = #{id}")
    Integer deleteFlight(String id);

    /**
     * 删除指定ID的航线航程数据
     *
     * @param airlineId 航线基础数据ID
     * @return 受影响行数
     */
    @Delete("DELETE FROM msd_airline_voyage WHERE airline_id = #{airlineId}")
    Integer deleteVoyage(String airlineId);


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
    List<LineDetail> getVoyages(String id);

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
            "airline_id=#{airlineId},departure_start=#{departureStart},departure_end=#{departureEnd},supplier_name=#{supplierName}," +
            "adult_price=#{adultPrice},child_price=#{childPrice},free_bag=#{freeBag},weight_limit=#{weightLimit}," +
            "alert_advance=#{alertAdvance},alert_rate=#{alertRate},can_return=#{canReturn},can_change=#{canChange}," +
            "can_sign=#{canSign},manager=#{manager},manager_id=#{managerId} WHERE id=#{id};")
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
     * 自动更新过期航线
     *
     * @return 受影响行数
     */
    @Update("UPDATE mbs_airline SET airline_status = 3 WHERE departure_end < CURDATE()")
    Integer updateAirLineStatusByNow();

    /**
     * 查询指定ID的航线资源数据
     *
     * @param id 航线资源ID
     * @return 航线资源数据
     */
    @Select("SELECT * FROM mbs_airline WHERE id = #{id}")
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
            "<foreach collection = \"dates\" item = \"item\" index = \"index\" open=\"(\" separator=\",\" close=\")\"> " +
            "#{item}" +
            "</foreach></script>")
    Integer updatePrice(@Param("id") String airlineId, @Param("dates") List<Date> dates,
                        @Param("adultPrice") BigDecimal adultPrice, @Param("childPrice") BigDecimal childPrice);

    Integer updateAirlineFlight();

    /**
     * 查询指定账户ID及航线基础数据ID的全部航班资源的执飞日期
     *
     * @param accountId 供应商账户ID
     * @param airLineId 航线基础数据ID
     * @return 执飞日期集合
     */
    @Select("SELECT f.flight_date FROM mbs_airline a JOIN mbs_airline_flight f ON f.airline_id=a.id " +
            "WHERE a.is_invalid=0 AND a.account_id=#{accountId} AND a.airline_id=#{airLineId} ORDER BY f.flight_date")
    List<Date> getExistedflightDate(@Param("accountId") String accountId, @Param("airLineId") String airLineId);

    /**
     * 编辑时查询指定账户ID及航线基础数据ID的全部航班资源的执飞日期
     *
     * @param accountId 供应商账户ID
     * @param airLineId 航线基础数据ID
     * @param id        航线ID
     * @return 执飞日期集合
     */
    @Select("SELECT f.flight_date FROM mbs_airline a JOIN mbs_airline_flight f ON f.airline_id=a.id " +
            "WHERE a.is_invalid=0 AND a.account_id=#{accountId} AND a.airline_id=#{airLineId} AND a.id != #{id} ORDER BY f.flight_date")
    List<Date> getExistedflightDateByUpdate(@Param("accountId") String accountId, @Param("airLineId") String airLineId, @Param("id") String id);

    /**
     * 新增舱位资源
     *
     * @param accountId 账户ID
     * @param flightId  航班资源ID
     * @param owner     拥有者名称
     * @param count     舱位数量
     * @param divider   分配人
     * @param dividerId 分配人ID
     * @return 受影响行数
     */
    @Insert("INSERT mbs_airline_flight_seat(id,account_id,flight_id,owner,owner_id,divider,divider_id) " +
            "SELECT REPLACE(UUID(),'-',''),#{accountId},#{flightId},#{owner},#{accountId},#{divider},#{dividerId} " +
            "FROM msd_airline_info LIMIT #{count};")
    Integer addSeats(@Param("accountId") String accountId, @Param("flightId") String flightId,
                     @Param("owner") String owner, @Param("count") Integer count,
                     @Param("divider") String divider, @Param("dividerId") String dividerId);

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

    /**
     * 获取最新的航线信息
     *
     * @return
     */
    @Select("SELECT a.id AS lineId,d.dep_city,d.arr_city,d.flight_type,f.adult_price price,min(f.flight_date) AS departDate," +
            "sum(CONV(LEFT (f.id,1),16,10) % 9+1+a.seat_count-f.seat_count) saled " +
            "FROM mbs_airline a JOIN msd_airline d ON d.id=a.airline_id JOIN mbs_airline_flight f ON f.airline_id=a.id " +
            "WHERE a.airline_status=1 AND a.res_type=0 AND a.is_invalid=0 AND f.flight_date> CURDATE() " +
            "GROUP BY d.dep_city,d.arr_city,d.flight_type ORDER BY a.created_time DESC LIMIT #{pageIndex}, #{pageSize}")
    List<NewLine> newLineData(Line line);

    /**
     * 航线列表查询
     *
     * @return
     */
    @Select("<script>SELECT a.adult_price AS adultPrice, a.child_price as childPrice, a.manager," +
            "a.airline_status as airlineStatus, a.airline_no as airlineNo, a.supplier_name as supplierName," +
            "d.flight_type as flightType, a.departure_start as departureStart,a.departure_end departureEnd," +
            "f.airline_id as id, sum(f.seat_count) seatCount, v.days, d.voyage," +
            "d.flight_number as flightNumber, SUM(IFNULL(s.saled, 0)) AS saled," +
            "SUM(IFNULL(s.unAllot, 0)) AS unAllot " +
            "FROM mbs_airline_flight f JOIN mbs_airline a ON a.id = f.airline_id " +
            "JOIN msd_airline d ON d.id = a.airline_id JOIN msd_airline_voyage v ON v.airline_id = d.id " +
            "AND v.trip_index = 1 LEFT JOIN ( SELECT flight_id, SUM( CASE " +
            "WHEN seat_status > 0 THEN 1 ELSE 0 END) AS saled, SUM( CASE " +
            "WHEN account_id = owner_id AND seat_status = 0 THEN 1 ELSE 0 " +
            "END ) unAllot FROM mbs_airline_flight_seat WHERE " +
            "<if test=\"accountId != null and accountId != ''\"> account_id = #{accountId} " +
            "GROUP BY flight_id) s ON s.flight_id = f.id WHERE a.account_id = #{accountId} </if> " +
            "<if test=\"depCity != null and depCity != ''\"> AND d.voyage LIKE concat('%',#{depCity},'%') </if> " +
            "<if test=\"arrCity != null and arrCity != ''\"> AND SUBSTRING_INDEX(d.voyage,'-',-1) LIKE concat(#{arrCity},'%') </if> " +
            "<if test=\"flightNo != null and flightNo != ''\"> AND d.flight_number LIKE concat('%',#{flightNo},'%') </if> " +
            "<if test=\"departureEnd != null and departureEnd != ''\"><![CDATA[ AND a.departure_start <= #{departureEnd} ]]> </if> " +
            "<if test=\"departureStart != null and departureStart != ''\"><![CDATA[ AND a.departure_end >= #{departureStart} ]]> </if> " +
            "<if test=\"airlineStatus != null and airlineStatus != ''\"> AND a.airline_status = #{airlineStatus} </if> " +
            "<if test=\"manager != null and manager != ''\"> AND a.manager_id = #{manager} </if> " +
            "AND a.is_invalid = 0 GROUP BY a.id ORDER BY a.created_time DESC " +
            "LIMIT ${pageIndex} , ${pageSize}</script>")
    List<Line> queryLineList(Line line);

    /**
     * 航线是否分配
     *
     * @param airlineId
     * @param accountId
     * @return
     */
    @Select("SELECT COUNT(*) FROM mbs_airline_flight mf LEFT JOIN mbs_airline_flight_seat mfs ON mf.id = mfs.flight_id "
            + "LEFT JOIN mbs_airline mba ON mf.airline_id = mba.id  AND mba.airline_status = 1 WHERE mf.airline_id = #{airlineId} "
            + "AND mfs.account_id = #{accountId} AND mfs.account_id != mfs.owner_id AND mfs.seat_status > 0")
    Integer isAllot(@Param("airlineId") String airlineId, @Param("accountId") String accountId);

    /**
     * 航线是否已售
     *
     * @param airlineId
     * @param accountId
     * @return
     */
    @Select("SELECT COUNT(*) FROM mbs_airline_flight mf LEFT JOIN mbs_airline_flight_seat mfs ON mf.id = mfs.flight_id "
            + "LEFT JOIN mbs_airline mba ON mf.airline_id = mba.id AND mba.airline_status = 1 WHERE mf.airline_id = #{airlineId} "
            + "AND mfs.account_id = #{accountId} AND mfs.account_id = mfs.owner_id AND mfs.seat_status > 0")
    Integer isSaled(@Param("airlineId") String airlineId, @Param("accountId") String accountId);

    /**
     * 获取航线航线Flight
     *
     * @param airlineId
     * @return
     */
    @Select("SELECT * FROM mbs_airline_flight WHERE airline_id=#{airlineId}")
    List<Flight> getFlights(String airlineId);

    /**
     * 上架判断seat库中是否已存在
     *
     * @param airlineId
     * @return
     */
    @Select("SELECT 1 FROM mbs_airline_flight_seat " +
            "WHERE flight_id in " +
            "(SELECT id FROM mbs_airline_flight WHERE airline_id=#{airlineId}) LIMIT 1")
    Integer ifOnSale(String airlineId);

    /**
     * 下架删除seat库中记录
     *
     * @param airlineId
     * @return
     */
    @Delete("DELETE FROM mbs_airline_flight_seat " +
            "WHERE flight_id in " +
            "(SELECT id FROM mbs_airline_flight WHERE airline_id=#{airlineId})")
    Integer deleteSeats(String airlineId);

    /**
     * 更新每日航线资源
     *
     * @param priceTemplateBean
     */
    @UpdateProvider(type = AspectSql.class, method = "updatePrice")
    void updateDayPrice(PriceTemplateBean priceTemplateBean);

    /**
     * 查询过期航线
     *
     * @param start
     * @return
     */
    @Select("select tmp.airline_id from mbs_airline ma left join ( select airline_id, max(flight_date) as edate " +
            "from mbs_airline_flight group by airline_id) tmp on ma.id = tmp.airline_id where tmp.edate < #{start} " +
            "and ( ma.airline_status = 1 or ma.airline_status = 2)")
    List<String> queryExpireFlights(Date start);

    /**
     * 设置过期航线
     *
     * @param ids
     * @return
     */
    @Update("update mbs_airline set airline_status = 3 where id in (${ids})")
    Integer updateExpireFlights(String ids);

    /**
     * 根据 flightId 查询航线信息
     *
     * @param flightId
     * @return
     */
    @Select("select ma.airline_id, ma.supplier_name, maf.flight_date departureEnd from mbs_airline ma left join mbs_airline_flight " +
            "maf on ma.id = maf.airline_id where maf.id = #{flightId} ")
    Line getLineByFlightId(String flightId);

    /**
     * 查询分销商拥用的航线数
     *
     * @param accountId
     * @param ownerId
     * @return
     */
    @Select("select count(distinct(airline_id)) from mbs_airline_flight a right join (select distinct(flight_id) " +
            "from mbs_airline_flight_seat where account_id = #{accountId} and owner_id = #{ownerId} union all select " +
            "distinct(flight_id) from mbc_assign_record where account_id = #{accountId} and dealer_id = #{ownerId} and " +
            "flight_id not in (select distinct(flight_id) from mbs_airline_flight_seat where account_id = #{accountId} " +
            "and owner_id= #{ownerId})) b on a.id = b.flight_id ")
    Integer getEnableFlights(@Param("accountId") String accountId, @Param("ownerId") String ownerId);
}
