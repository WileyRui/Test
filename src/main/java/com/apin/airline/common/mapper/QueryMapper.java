package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.entity.Seat;
import com.apin.airline.flight.dto.*;
import com.apin.airline.ticket.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 宣炳刚
 * @date 2017/10/16
 * @remark 查询相关DAL
 */
@Mapper
public interface QueryMapper extends Mapper {

    @Select("select min(b.adult_price) basePrice,c.dep_city depCity,c.arr_city arrCity, sum(b.seat_count) remainCount,min(b.flight_date) startDate,max(b.flight_date) endDate  ,sum(a.seat_count) total,c.flight_type flightType,sum(CONV(left(b.id,1),16,10)%9+1+a.seat_count-b.seat_count ) soldCount from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
        " join msd_airline c on a.airline_id=c.id where  c.dep_city=#{depCity} and c.arr_city=#{arrCity} and (#{flightType} is null or c.flight_type=#{flightType}) and   a.res_type=0 and a.airline_status=1 and a.is_invalid=0  group by c.voyage")
    FlightDetail selectFlight(CityList cityList);
    @Select("select img_url from msd_city where city_name=#{deatCity}")
    String selectCityImg(String destCity);
    @Select("select b.flight_date retDate,min(b.adult_price) basePrice,sum(b.seat_count) remainCount from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
            " join msd_airline c on a.airline_id=c.id where c.dep_city=#{depCity} and c.arr_city=#{arrCity}  and (#{flightType} is null or c.flight_type=#{flightType}) and SUBSTRING_INDEX(b.flight_date,'-',2)=#{month} and b.flight_date>=NOW()  and   a.res_type=0 and a.airline_status=1 and a.is_invalid=0   group by b.flight_date order  by b.flight_date")
    List<DayPrice> selectFlightDates(CityList cityList);
    @SelectProvider(type = AspectSql.class,method = "selectFlightList")
    List<ResponseAirlineDto> selectFlightList(CityList cityList);

    /**
     * 根据城市对聚合查询所有航程
     * @param cityList
     * @return
     */
    @SelectProvider(type = AspectSql.class,method = "selectFlights")
    List<FlightDetail> selectFlights(CityList cityList);

    /**
     * 根据城市对出发日期返程日期查询所有航班
     * @param cityList
     * @return
     */
    @Select("select c.id airlineId,sum(a.seat_count) AS total," +
            "sum(b.seat_count) remainCount, min(b.adult_price) basePrice,e.days+1 days,DATE_ADD(b.flight_date,INTERVAL e.days DAY) retDate,c.flight_type flightType,sum(CONV(left(b.id,1),16,10)%9+1+a.seat_count-b.seat_count ) soldCount FROM mbs_airline a  " +
            "JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
            "JOIN msd_airline c ON a.airline_id = c.id  " +
            "JOIN msd_airline_voyage e ON e.airline_id = c.id " +
            "join msd_airline_info i on i.id=e.flight_info_id  " +
            "WHERE  " +
            "  c.dep_city=#{depCity} and c.arr_city=#{arrCity}  " +
            "AND b.flight_date = #{depDate}  " +
            "AND e.days = #{day} and c.flight_type=#{flightType} " +
            " and   a.res_type=0 and a.airline_status=1 and a.is_invalid=0 " +
            "GROUP BY c.flight_number  " +
            "ORDER BY i.flight_deptime_plan_date")
    List<ResponseAirlineDto> selectFlightDetail(CityList cityList);

    /**
     * 根据航线id查询信息
     * @param flightNum
     * @return
     */
    @Select("select a.flight_company compName,b.logo_ico logo,a.flight_arrtime_plan_date arrTime,a.flight_deptime_plan_date depTime,a.flight_dep_airport depAirport,e.flight_type flightType," +
            "a.flight_arr_airport arrAirport,a.flight_no num from msd_airline_info a  " +
            " LEFT JOIN msd_airway b ON a.flight_company = b.company_name  " +
            " join msd_airline_voyage c on  c.flight_info_id=a.id  " +
            " join msd_airline e on e.id=c.airline_id  " +
            " WHERE c.airline_id=#{flightNum} order by c.trip_index")
    List<AirlineInfo> selectByFlightNum(String flightNum);

    /**
     * 查询去程月份
     * @param cityList
     * @return
     */
    @Select("SELECT  " +
            "   SUBSTRING_INDEX(b.flight_date,'-',2) retDate FROM  " +
            "   mbs_airline a  " +
            "JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
            "JOIN msd_airline c ON a.airline_id = c.id  " +
            "WHERE c.dep_city =#{depCity} AND c.arr_city =#{arrCity} AND  (#{flightType} is null or c.flight_type=#{flightType}) " +
            "  and   a.res_type=0 and a.airline_status=1 and a.is_invalid=0  and b.flight_date>=NOW() " +
            " GROUP BY SUBSTRING_INDEX(b.flight_date,'-',2)  " +
            " ORDER BY b.flight_date")
    List<String> selectMonthQuery(CityList cityList);

    /**
     * 查询返程月份
     * @param cityList
     * @return
     */
    @Select(" select SUBSTRING_INDEX(a.retDate,'-',2) month from (SELECT e.days,DATE_ADD(b.flight_date,INTERVAL e.days DAY) retDate   " +
            " FROM mbs_airline a   " +
            " JOIN mbs_airline_flight b ON a.id = b.airline_id   " +
            " JOIN msd_airline c ON a.airline_id = c.id   " +
            " JOIN msd_airline_voyage e ON e.airline_id = c.id " +
            "WHERE c.dep_city =#{depCity} AND c.arr_city =#{arrCity} and b.flight_date=#{depDate} and e.trip_index = 1 " +
            " and   a.res_type=0 and a.airline_status=1 and a.is_invalid=0 " +
            "GROUP BY e.days) a GROUP BY SUBSTRING_INDEX(a.retDate,'-',2)")
    List<String> selectFlightsMonth(CityList cityList);

    /**
     * 根据mbs_airline_flight、mbs_airline_flight_seat、msd_airline_voyage获取库存日历和价格日历信息
     * @param calendarInfo
     * @return
     */
    @SelectProvider(type = AspectSql.class,method = "findElementList")
    List<ApinCalendarElement> findElementList(CalendarInfo calendarInfo);

    /**
     * 判断是否有进行分配
     * @param stock
     * @return
     */
    @Select("select CASE WHEN count(*)>0 THEN TRUE ELSE FALSE END from mbs_airline_flight_seat where flight_id in " +
            "(select id from mbs_airline_flight where airline_id=#{airlineId}"+
            " and flight_date=#{currentDate}"+
            " and owner_id !=#{accountId})")
    boolean ifAllocated(Stock stock);

    /**
     * 根据航线id与日期查询航班信息
     * @param airlineId
     * @param currentDate
     * @return
     */
    @Select("select * from mbs_airline_flight where airline_id=#{airlineId} and flight_date=#{flightDate}")
    Flight findByAirlineIdAndFlightDate(String airlineId, String currentDate);

    /**
     * 获取包机商或分销商的已售、未售及库存值
     * @param stock
     * @return
     */
    @SelectProvider(type = AspectSql.class,method = "findStock")
    ApinCalendarElement findStock(Stock stock);

    /**
     * 删除库存（减少库存）
     * @param stock
     * @return
     */
    @DeleteProvider(type = AspectSql.class,method = "deleteStock")
    Integer deleteStock(Stock stock);

    /**
     * 根据航班id拥有者id查找到一个样本
     * @param id
     * @param accountId
     * @return
     */
    @SelectProvider(type = AspectSql.class,method = "findByFlightIdAndOwnerId")
    Seat findByFlightIdAndOwnerId(String id, String accountId);

    /**
     * 更新航线每日价格
     * @param priceTemplateBean  航线价格更新模板
     * @return
     */
    @UpdateProvider(type = AspectSql.class, method = "updatePrice")
    Integer priceImport(PriceTemplateBean priceTemplateBean);

    /**
     * 根据用户id跟拥有者查询相关信息
     * @param accountId
     * @param ownerId
     * @return
     */
    @Select("select e.flight_type flightType,e.voyage , e.flight_number flightNum,f.airId airlineId,f.recovery_advance recoveryDay,f.departure_start startTime,f.departure_end endTime,v.days days,f.ssolded solded, f.sremain remain from msd_airline e right join  " +
            "(select airline_id,id airId,recovery_advance,departure_start,departure_end,d.ssolded,d.sremain from mbs_airline c right join (select airline_id aaid,sum(b.solded) ssolded,sum(b.remain) sremain from mbs_airline_flight a right join  " +
            "(select flight_id,1 as fg,count(flight_id),count( CASE WHEN seat_status > 0 THEN 1 ELSE NULL END) AS solded, " +
            " count(CASE WHEN seat_status = 0 THEN 1 ELSE NULL END) AS remain from mbs_airline_flight_seat where account_id = #{accountId} and owner_id=#{ownerId} group by flight_id " +
            "union all select flight_id,0 as fg,count,0,0 from mbc_assign_record where account_id =#{accountId} and dealer_id = #{ownerId}  " +
            "and flight_id not in (select flight_id from mbs_airline_flight_seat where account_id = #{accountId} and owner_id=#{ownerId} group by flight_id) " +
            "group by flight_id) b on a.id = b.flight_id group by aaid) d on c.id = d.aaid) f on e.id = f.airline_id LEFT  JOIN msd_airline_voyage v on e.id=v.airline_id where v.trip_index=1")
    List<FlightBo> selectAirsByAccountId(String accountId, String ownerId);

    /**
     * 根据航线id查询航班日期列表
     * @param airlineId
     * @return
     */
    @Select("select flight_date from mbs_airline_flight where airline_id=#{airlineId} order by flight_date")
    List<String> selectByAirlineId(String airlineId);

    /**
     * 根据拥有者id查询收位记录
     * @param listDto
     * @param auto
     * @param airlineId
     * @return
     */
    @SelectProvider(type = AspectSql.class, method = "selectAssign")
    Integer selectAssign(DealerListDto listDto, Integer auto, String airlineId);

    /**
     * arm指定分销商库存日历查询
     * @param listDto
     * @return
     */
    @Select("SELECT a.adult_price AS adultPrice,a.child_price AS childPrice,count(CASE WHEN b.seat_status > 0 THEN 1 ELSE NULL END) AS solded,count(CASE WHEN b.seat_status = 0 THEN 1 ELSE NULL END) AS remain,a.flight_date AS flightDate, a.recovery_date AS recoveryDate,a.id as flightId from " +
            "mbs_airline_flight a left JOIN mbs_airline_flight_seat b ON a.id = b.flight_id WHERE" +
            " a.airline_id = #{airlineId}" +
            " and b.account_id = #{accountId}" +
            " and b.owner_id = #{ownerId}" +
            " and a.flight_date>=#{startDate}" +
            " and a.flight_date<=#{endDate}" +
            " group by a.id" +
            " order by a.flight_date")
    List<StockBo> selectStockInfo(DealerListDto listDto);

    /**
     * 根据状态航班id拥有者id查询seat表信息
     * @param ownerId
     * @param flightId
     * @param passenger
     * @return
     */
    @SelectProvider(type = AspectSql.class, method = "selectByStatus")
    List<Seat> selectByStatus(String ownerId, String flightId, Integer passenger);

    /**
     * 根据航班id,用户id,分配者id,类型统计收位数量
     * @param accountId
     * @param ownerId
     * @param type
     * @param flightId
     * @return
     */
    @Select("select sum(count) from mbc_assign_record where flight_id=#{flightId} and account_id=##{accountId}"+
            " and dealer_id=#{ownerId} and type=#{type}")
    Integer selectAssignCount(String accountId,String ownerId, Integer type, String flightId);

    /**
     *根据条件查询收位数量，价格等信息
     * @param listDto
     * @return
     */
    @Select("SELECT sum(CASE WHEN a.type = 2 THEN a.count ELSE 0 END) AS autoCount," +
            "sum(CASE WHEN a.type = 3 THEN a.count ELSE 0 END) AS handCount,b.adult_price as adultPrice,b.child_price as childPrice,b.flight_date as flightDate,b.recovery_date as recoveryDate,a.flight_id " +
            "FROM mbc_assign_record a " +
            "JOIN mbs_airline_flight b ON a.flight_id = b.id" +
            " WHERE" +
            " a.account_id = #{accountId}" +
            " AND a.dealer_id = #{ownerId}" +
            " and b.flight_date>=#{startDate}" +
            " and b.flight_date<=#{endDate} and b.airline_id=#{airlineId} GROUP BY a.flight_id order by b.flight_date")
    List<StockBo> selectRecoverySeat(DealerListDto listDto);

    /**
     * seat表修改状态
     * @param seat
     * @param oldSeat
     * @return
     */
    @UpdateProvider(type = AspectSql.class, method = "modifySeatStatus")
    Integer modifySeatStatus(Ticket seat, Ticket oldSeat);

    /**
     * 分销商判断有无数据接口
     * @param accountId
     * @param ownerId
     * @return
     */
    @Select("select * from mbs_airline_flight_seat where account_id=#{accountId} and owner_id=#{ownerId}")
    List<Seat> hasList(String accountId, String ownerId);

    /**
     * 添加乘机人
     * @param dto
     * @return
     */
    @Update("update mbs_airline_flight_seat set" +
            " passenger_type=#{passengerType},passenger_name=#{passengerName} ,cred_type=#{credType} ,cred_number=#{credNumber}" +
            " ,gender=#{gender},birthday=#{birthday} ,nation=#{nation} ,expire_time=#{expireTime}"+
            " ,issue_place=#{issuePlace},seat_status=2 where id=#{id}")
    Integer updatePassengerInfo(PassengerInfoDto dto);

    /**
     * 查询是否有重复证件号
     * @param credNumber
     * @param flightId
     * @param ownerId
     * @return
     */
    @Select("select * from mbs_airline_flight_seat where cred_number=#{credNumber} and flight_id=#{flightId} and owner_id=#{ownerId}")
    List<Seat> findByCredNumber(String credNumber, String flightId, String ownerId);

    /**
     * 修改seat为0
     * @param dto
     * @return
     */
    @UpdateProvider(type = AspectSql.class, method = "updateToRemain")
    Integer updateToRemain(DealerListDto dto);

    /**
     * 修改seat为1
     * @param dto
     * @return
     */
    @UpdateProvider(type = AspectSql.class, method = "updateStatus")
    Integer updateStatus(DealerListDto dto);

    /**
     * 获取包机商或分销商的已售、未售及库存值(多航班)
     * @param stock
     * @return
     */
    @SelectProvider(type = AspectSql.class, method = "findStockList")
    List<ApinCalendarElement> findStockList(Stock stock);
    @SelectProvider(type = AspectSql.class,method = "findModifySeatOwnerInBatchIdList")
    List<String> findModifySeatOwnerInBatchIdList(Deal deal, OwnerElement e);

    void modifySeatOwnerInBatch(Deal deal, OwnerElement e, String s);

    /**
     * 查询首页日历每天在飞的航班的统计信息
     *
     * @param request 查询请求参数
     * @return 每天的在飞的航班统计信息
     */
    @SelectProvider(type = AspectSql.class, method = "queryHomeCalendarInfo")
    List<HomeCalendarInfoDto> queryHomeCalendarInfo(HomeCalendarInfoQueryRequest request);

    /**
     * 查询包机商某天的所有在飞的航班信息
     *
     * @param request 查询请求
     * @return
     */
    @SelectProvider(type = AspectSql.class, method = "queryAllAirlinesByDay")
    List<HomeAirlineResponse> queryAllAirlinesByDay(HomeAirlineQueryRequest request);

    /**
     * 更新seat表
     * @param owner
     * @param accountId
     * @param dealerId
     * @param flightId
     * @param recoveryNum
     */
    @Update("update mbs_airline_flight_seat set owner = #{owner}, owner_id = #{accountId},seat_status = 0 where account_id = #{accountId}" +
            " and owner_id = #{ownerId} and flight_id = #{flightId} and seat_status < 2 order by seat_status limit #{num} ")
    Integer updateSeatsOwner(String owner, String accountId, String dealerId, String flightId, int recoveryNum);

    /**
     * 根据航班id查询flight信息
     * @param flightId
     * @return
     */
    @Select("select * from mbs_airline_flight where id =#{flightId}")
    Flight selectByFlightId(String flightId);

    /**
     * 查根据id查询seat信息
     * @param ids
     * @return
     */
    @Select("select account_id as accountId, owner_id as dealerId, count(account_id) as count, flight_id as flightId from mbs_airline_flight_seat " +
            "where id in (${ids}) group by account_id,owner_id,flight_id ")
    List<QueryForceRecoveryDto> queryForceRecovery(String ids);

    /**
     * 根据id更新seat表
     * @param owner
     * @param ownerId
     * @param id
     * @return
     */
    @Update("update mbs_airline_flight_seat set owner = #{owner}, owner_id = #{ownerId},seat_status = 0 where id = #{id} ")
    Integer updateSeatsOwnerById(String owner, String ownerId, String id);

    /**
     * 根据id查询该航班是否可回收
     * @param flightId
     * @return
     */
    @Select("select count(*) from mbs_airline_flight where id = #{flightId} and recovery_date > NOW() ")
    Integer getRecoveryDateByFlightId(String flightId);

    /**
     * 查询seat表状态
     * @param accountId
     * @param dealerId
     * @param flightId
     * @param status
     * @return
     */
    @Select("select count(1) from mbs_airline_flight_seat where account_id = #{accountId} and owner_id = #{ownerId}" +
            " and flight_id = #{flightId} and seat_status < ${status} ")
    Integer getEnableSeat(String accountId, String dealerId, String flightId, int status);

    /**
     * 查询当前日期可以回收的航线
     * @param time
     * @return
     */
    @Select("SELECT  ms.id,  ms.account_id AS accountId,  ms.flight_id AS flightId,  mf.airline_id AS airlineId  " +
            "FROM (select * from mbs_airline_flight_seat where account_id != owner_id and seat_status < 2) AS ms  " +
            "inner JOIN mbs_airline_flight AS mf ON ms.flight_id = mf.id  " +
            "WHERE mf.recovery_date <= #{time}")
    List<QueryFlightsToRecoveryDto> queryFlightsToRecovery(Date time);

    @SelectProvider(type = AspectSql.class, method = "getPassengerList")
    List<PassengerInfo> getPassengerList(String accountId, PassengerParam dto);

    /**
     * 包机商航司合作线路数接口
     * @param accountId
     * @param compName
     * @return
     */
    @Select("select count(*) from mbs_airline a join msd_airway b on a.airway_id=b.id where a.account_id=#{accountId} and b.company_name=#{compName} and a.is_invalid=0")
    Integer count(String accountId, String compName);

    /**
     * 查询航司名称相关数据
     * @return
     */
    @Select("select company_name as compName,iata_code as iataCode,logo_ico as logIco,id as airwayId from msd_airway")
    List<Map<String,String>> findCompName();
    /**
     * 查询航线每日库存情况
     * @param searchDayAirlinesDto 查询条件Dto
     * @return
     */
    @SelectProvider(type=AspectSql.class,method = "searchDayAirlines")
    public List<AirlineInfoDTO> searchDayAirlines(SearchDayAirlinesDto searchDayAirlinesDto);
}
