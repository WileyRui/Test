package com.apin.airline.common.mapper;

import com.apin.airline.flight.dto.CityList;
import com.apin.airline.flight.dto.DealerListDto;
import com.apin.airline.flight.dto.HomeCalendarInfoQueryRequest;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.ticket.dto.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huanglei
 * @remark
 * @date 2017/10/16
 */
public class AspectSql {
    /**
     * 首页日历查询
     * @param request
     * @return
     */
    public String queryHomeCalendarInfo(HomeCalendarInfoQueryRequest request) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT f.flight_date as flightDate, (DAYOFWEEK(f.flight_date)-1) as week, "
                + " sum(case when s.seat_status > 0 then 1 else 0 end) as 'sold', " + " sum(case when s.owner_id != '"
                + request.getAccountId() + "' and s.seat_status = 0 then 1 else 0 end) as 'surplus', "
                + " sum(case when s.owner_id = '" + request.getAccountId()
                + "' and s.seat_status = 0 then 1 else 0 end) as 'unallocated' " + " from mbs_airline a "
                + " join mbs_airline_flight f on f.airline_id = a.id " + " and f.flight_date >= '"
                + request.getStartDate() + "' and f.flight_date <= '" + request.getEndDate() + "' "
                + " join mbs_airline_flight_seat s on s.flight_id = f.id " + " where a.account_id = '"
                + request.getAccountId() + "' " + " and a.is_invalid = 0 " + " and a.airline_status = 1 "
                + " group by f.flight_date ");
        //
        return sql.toString();
    }
    public String selectFlightList(CityList cityList) {
        String sql = "SELECT min(b.adult_price) basePrice,sum(a.seat_count) total,min(b.flight_date) startDate,max(b.flight_date) endDate," +
                "c.dep_city depCity,c.arr_city arrCity,c.flight_type ,e.img_url arrCityImgUrl,sum(b.seat_count) remainCount,sum(CONV(left(b.id,1),16,10)%9+1+a.seat_count-b.seat_count ) soldCount FROM mbs_airline a" +
                " JOIN mbs_airline_flight b ON a.id = b.airline_id" +
                " JOIN msd_airline c ON a.airline_id = c.id" +
                " JOIN msd_city e on c.arr_city=e.city_name JOIN msd_city f on c.dep_city=f.city_name " +
                " WHERE   c.flight_type=" + cityList.getFlightType();
        if (StringUtils.isNotBlank(cityList.getArrCity())) {
            sql += " and c.arr_city='" + cityList.getArrCity() + "'";
        }
        if (StringUtils.isNotBlank(cityList.getDepCity())) {
            sql += " and c.dep_city='" + cityList.getDepCity() + "'";
        }
        sql += " and a.res_type=0 and a.airline_status=1 and a.is_invalid=0   GROUP BY c.voyage order by f.en_name, e.en_name";
        return sql;
    }

    public String selectFlights(CityList cityList) {
        String sql = " SELECT min(b.adult_price) basePrice, sum(b.seat_count) remainCount, b.flight_date, e.days ,DATE_ADD(b.flight_date,INTERVAL e.days DAY) retDate FROM mbs_airline a  " +
                " JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
                " JOIN msd_airline c ON a.airline_id = c.id  " +
                " JOIN msd_airline_voyage e ON e.airline_id = c.id  " +
                " WHERE c.dep_city='" + cityList.getDepCity() + "' and c.arr_city='" + cityList.getArrCity() + "'";
        if (cityList.getFlightType() == null) {
            sql += " and c.flight_type=" + cityList.getFlightType() + " ";
        }
        sql += "  and a.res_type=0 and a.airline_status=1 and a.is_invalid=0  AND b.flight_date = '" + cityList.getDepDate() + "' AND e.trip_index = 1 and SUBSTRING_INDEX(DATE_ADD(b.flight_date,INTERVAL e.days DAY),'-',2)='" + cityList.getMonth() + "' GROUP BY e.days";
        return sql;
    }

    /**
     * 获取包机商或分销商的已售、未售及库存值
     */
    public String findStock(Stock stock) {
        String accountId = stock.getAccountId();
        StringBuffer sql = new StringBuffer(
                "SELECT COUNT(CASE WHEN ftst.seat_status > 0 THEN 1 ELSE NULL END) sold," +
                        "COUNT(CASE WHEN ftst.seat_status = 0 AND ftst.owner_id != '" + accountId + "' THEN 1 ELSE NULL END) surplus," +
                        "COUNT(CASE WHEN ftst.seat_status = 0 AND ftst.owner_id = '" + accountId + "' THEN 1 ELSE NULL END) unallocated " +
                        "FROM mbs_airline_flight ft,mbs_airline_flight_seat ftst " +
                        "WHERE ft.id = ftst.flight_id AND ftst.account_id = '" + accountId +
                        "' AND ft.flight_date='" + stock.getCurrentDate() + "' AND ft.airline_id='" + stock.getAirlineId() + "'");
        return sql.toString();
    }

    /**
     * 删除库存（减少库存）
     */
    public String deleteStock(Stock stock) {
        StringBuffer sql = new StringBuffer(
                " delete from mbs_airline_flight_seat " +
                        "where flight_id in (select id from mbs_airline_flight where airline_id='" + stock.getAirlineId() + "'" +
                        " and flight_date='" + stock.getCurrentDate() + "') and owner_id ='" + stock.getAccountId() + "' and seat_status=0 limit " + stock.getStockNumber());
        return sql.toString();
    }

    /**
     * 查找一个样本
     */
    public String findByFlightIdAndOwnerId(String flightId, String ownerId) {
        StringBuffer sql = new StringBuffer(
                " select * from mbs_airline_flight_seat " +
                        "where flight_id ='" + flightId + "' and owner_id ='" + ownerId + "' limit 1");
        return sql.toString();
    }

    /**
     * 更新价格
     *
     * @param priceTemplateBean
     * @return
     */
    public String updatePrice(PriceTemplateBean priceTemplateBean) {
        StringBuffer updateSqlBuf = new StringBuffer("update mbs_airline_flight set ");
        if (priceTemplateBean.getAdultPrice() != null)
            updateSqlBuf.append(" adult_price = " + priceTemplateBean.getAdultPrice() + ",");
        if (priceTemplateBean.getChildPrice() != null)
            updateSqlBuf.append("child_price=" + priceTemplateBean.getChildPrice() + ",");
        if (priceTemplateBean.getRecycleDay() != null)
            updateSqlBuf.append("recovery_date= DATE_ADD(flight_date,INTERVAL " + -priceTemplateBean.getRecycleDay() + " DAY),");
        if (priceTemplateBean.getStoreType() != null)
            updateSqlBuf.append("sell_type=" + priceTemplateBean.getStoreType() + ",");
        if (priceTemplateBean.getStoreCount() != null)
            updateSqlBuf.append("seat_count=" + priceTemplateBean.getStoreCount() + ",");
        updateSqlBuf = new StringBuffer(updateSqlBuf.substring(0, updateSqlBuf.length() - 1));
        updateSqlBuf.append(" where airline_id ='" + priceTemplateBean.getAirlineId() + "' and flight_date='" + priceTemplateBean.getFlightDate() + "'");
        return updateSqlBuf.toString();
    }

    /**
     * 根据拥有者id查询收位记录
     *
     * @param listBo
     * @param type
     * @param airlineId
     * @return
     */
    public String selectAssign(DealerListDto listBo, Integer type,
                               String airlineId) {
        String sql = "select sum(count) from mbc_assign_record a join mbs_airline_flight b on a.flight_id=b.id where b.airline_id='" + airlineId + "' and a.account_id='" + listBo.getAccountId() + "'" +
                " and a.dealer_id='" + listBo.getOwnerId() + "'" +
                " and a.type=" + type;

        return sql;
    }

    /**
     * 根据mbs_airline_flight、mbs_airline_flight_seat、msd_airline_voyage获取库存日历和价格日历信息
     *
     * @param calendarInfo
     * @return
     */
    public String findElementList(CalendarInfo calendarInfo) {
        String accountId = calendarInfo.getAccountId();
        StringBuffer sql = new StringBuffer(
                "SELECT left(NOW(),10)>temp.departDate isExpire,temp.departDate,temp.backDate,temp.sold,temp.surplus,temp.unallocated," +
                        "(CASE WHEN temp.alert_date <= NOW() AND temp.alert_threshold is not null AND temp.surplus + temp.unallocated >= " +
                        "temp.alert_threshold THEN" +
                        " TRUE ELSE FALSE END) alertStatus," +
                        "(CASE WHEN temp.allocated>0 OR temp.sold>0 THEN TRUE ELSE FALSE END) ifAllocated," +
                        "temp.seatCount,temp.recoveryDate," +
                        "NULL freeStatus,temp.adult_price adultPrice,temp.child_price childPrice " +
                        "FROM(" +
                        "SELECT ft.flight_date departDate,ft.seat_count seatCount,ft.recovery_date recoveryDate," +
                        "DATE_ADD(ft.flight_date,INTERVAL (select MAX(days) from msd_airline_voyage where airline_id=f.airline_id) DAY) backDate," +
                        "COUNT(CASE WHEN ftst.seat_status > 0 THEN 1 ELSE NULL END) sold," +
                        "COUNT(CASE WHEN ftst.seat_status = 0 AND ftst.owner_id != '" + accountId +
                        "' THEN 1 ELSE NULL END) surplus," +
                        "COUNT(CASE WHEN ftst.seat_status = 0 AND ftst.owner_id = '" + accountId +
                        "' THEN 1 ELSE NULL END) unallocated," +
                        "COUNT(CASE WHEN ftst.owner_id !='" + accountId + "' THEN 1 ELSE NULL END) allocated," +
                        "ft.alert_date,ft.alert_threshold,ft.adult_price,ft.child_price " +
                        "FROM  mbs_airline f," +
                        "mbs_airline_flight ft,mbs_airline_flight_seat ftst " +
                        "WHERE f.id=ft.airline_id AND ft.id = ftst.flight_id " +
                        "AND ftst.account_id = '" + accountId + "' AND ft.airline_id = '" + calendarInfo.getAirlineId() +
                        "' AND ft.flight_date BETWEEN '" + calendarInfo.getStartDate() + "' and '" + calendarInfo.getEndDate() +
                        "' GROUP BY departDate" +
                        ") temp");
        return sql.toString();
    }

    /**
     * 根据航班id,用户id,收位类型统计数量
     *
     * @param listBo
     * @param type
     * @param flightId
     * @return
     */
    public String selectAssignCount(DealerListDto listBo, Integer type, String flightId) {
        String sql = "select sum(count) from mbc_assign_record where flight_id='" + flightId + "'" + "  and account_id='" + listBo.getAccountId() + "'" +
                " and dealer_id='" + listBo.getOwnerId() + "'" +
                " and type=" + type;
        return sql;
    }

    /**
     * 根据状态航班id拥有者id查询seat表信息
     * @param ownerId
     * @param flightId
     * @param status
     * @return
     */
    public String selectByStatus(String ownerId,String flightId,Integer status){
        String sql="SELECT * " +
                "FROM " +
                "mbs_airline_flight_seat " +
                "WHERE owner_id ='"+ownerId+"' ";
        if(status!=null) {
            sql=sql+ "AND seat_status =" + status;
        }else {
            sql=sql+"AND seat_status <2";
        }
        sql=sql+" AND flight_id='"+flightId+"' order by seat_status desc";
        return sql;
    }

    /**
     *修改库存状态
     */
    public String modifySeatStatus(Ticket seat, Ticket oldSeat){
        StringBuffer sql = new StringBuffer(
                " update mbs_airline_flight_seat set seat_status=" +seat.getStatus()+
                        " where flight_id in (select id from mbs_airline_flight where airline_id='"+oldSeat.getAirlineId()+
                        "' and flight_date='"+oldSeat.getCurrentDate()+"') and seat_status="+oldSeat.getStatus()+
                        " and owner_id='"+oldSeat.getOwnerId()+"' limit "+seat.getSeatNumber());
        return sql.toString();
    }

    /**
     * 修改seat状态全为0
     * @param listBo
     * @return
     */
    public String updateToRemain(DealerListDto listBo) {
        String sql="UPDATE mbs_airline_flight_seat " +
                "SET seat_status = 0 " +
                "WHERE " +
                "owner_id = '"+listBo.getOwnerId()+"' " +
                "AND flight_id = '"+listBo.getFlightId()+"' and seat_status<2";
        return sql;
    }

    /**
     * 修改seat状态为1
     * @param listBo
     * @return
     */
    public String updateStatus(DealerListDto listBo) {
        String sql="UPDATE mbs_airline_flight_seat " +
                "SET seat_status = 1 " +
                "WHERE " +
                "owner_id = '"+listBo.getOwnerId()+"' " +
                "AND flight_id = '"+listBo.getFlightId()+"' " +
                "AND seat_status = 0 " +
                "LIMIT "+listBo.getNumber();
        return sql;
    }
    /**
     * 获取包机商或分销商的已售、未售及库存值（多航班）
     */
    public String findStockList(Stock stock){
        String accountId = stock.getAccountId();
        List<String> dateString = stock.getDateList().stream().map(d->"'"+d+"'").collect(Collectors.toList());
        String s = dateString.size()==0?"''":dateString.toString().replace("[","").replace("]","");
        StringBuffer sql = new StringBuffer(
                "SELECT COUNT(CASE WHEN ftst.seat_status > 0 THEN 1 ELSE NULL END) sold," +
                        "COUNT(CASE WHEN ftst.seat_status = 0 AND ftst.owner_id != '"+accountId+"' THEN 1 ELSE NULL END) surplus," +
                        "COUNT(CASE WHEN ftst.seat_status = 0 AND ftst.owner_id = '"+accountId+"' THEN 1 ELSE NULL END) unallocated " +
                        "FROM mbs_airline_flight ft,mbs_airline_flight_seat ftst " +
                        "WHERE ft.id = ftst.flight_id AND ftst.account_id = '"+accountId+
                        "' AND ft.flight_date in ("+s+") AND ft.airline_id='"+stock.getAirlineId()+"' group by ft.flight_date");
        return sql.toString();
    }
    /**
     * 批量修改前查询IdList
     */
    public String findModifySeatOwnerInBatchIdList(Deal deal, OwnerElement ownerElement){
        List<String> dateList = deal.getDateList();
        if(dateList.size()==0)
            return "";
        Integer number = ownerElement.getSeatNumber();
        String airlineId = deal.getAirlineId();
        String s="(select id from mbs_airline_flight_seat where owner_id='"+deal.getAccountId()+"' and seat_status=0 and flight_id in " +
                "(select id from mbs_airline_flight where airline_id='"+airlineId+"' and flight_date='xx') limit "+number+")";
        StringBuffer sql = new StringBuffer();
        dateList.stream().forEach(date->{
            sql.append(s.replace("xx",date)+" union all ");
        });
        Integer replaceIndex = sql.lastIndexOf("union all");
        sql.replace(replaceIndex,replaceIndex+9,"");
        return sql.toString();
    }
    /**
     * 查询乘机人信息sql拼接
     *
     * @param passengerParam
     * @param accountId
     * @return Reply
     */
    public String getPassengerList(String accountId, PassengerParam passengerParam){
        StringBuilder sqlBuilder = new StringBuilder("select seat.* ");
        sqlBuilder.append("from mbs_airline_flight_seat seat join mbs_airline_flight flight on seat.flight_id = flight.id ");

        sqlBuilder.append("where flight.airline_id = '" + passengerParam.getAirlineId() + "' ")
                .append("and seat.account_id ='" + accountId + "' ")
                .append("and seat.cred_number is not null ")
                .append("and date_format(flight.flight_date,'%Y-%m-%d') = '"+ passengerParam.getCurrentDate() + "' ");
        if (StringUtils.isNotBlank(passengerParam.getOwner())){
            sqlBuilder.append("and seat.owner_id like '%" + passengerParam.getOwner() + "%' ");
        }
        return sqlBuilder.toString();
    }
}