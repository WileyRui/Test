package com.apin.airline.common.mapper;

import com.apin.airline.flight.dto.CityList;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.flight.dto.SearchDayAirlinesDto;
import org.apache.commons.lang.StringUtils;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/16
 */
public class AspectSql {
    public String selectFlightList(CityList cityList){
        String sql="SELECT min(b.adult_price) basePrice,sum(a.seat_count) total,min(b.flight_date) startDate,max(b.flight_date) endDate," +
                "c.dep_city depCity,c.arr_city arrCity,c.flight_type ,e.img_url arrCityImgUrl,sum(b.seat_count) remainCount,sum(CONV(left(b.id,1),16,10)%9+1+a.seat_count-b.seat_count ) soldCount FROM mbs_airline a" +
                " JOIN mbs_airline_flight b ON a.id = b.airline_id" +
                " JOIN msd_airline c ON a.airline_id = c.id" +
                " JOIN msd_city e on c.arr_city=e.city_name JOIN msd_city f on c.dep_city=f.city_name " +
                " WHERE   c.flight_type="+cityList.getFlightType();
                if(StringUtils.isNotBlank(cityList.getArrCity())){
                    sql+=" and c.arr_city='"+cityList.getArrCity()+"'";
                }
                if(StringUtils.isNotBlank(cityList.getDepCity())){
                    sql+=" and c.dep_city='"+cityList.getDepCity()+"'";
                }
               sql+=" and a.res_type=0 and a.airline_status=1 and a.is_invalid=0   GROUP BY c.voyage order by f.en_name, e.en_name";
        return sql;
    }
    public String selectFlights(CityList cityList){
        String sql=" SELECT min(b.adult_price) basePrice, sum(b.seat_count) remainCount, b.flight_date, e.days ,DATE_ADD(b.flight_date,INTERVAL e.days DAY) retDate FROM mbs_airline a  " +
                " JOIN mbs_airline_flight b ON a.id = b.airline_id  " +
                " JOIN msd_airline c ON a.airline_id = c.id  " +
                " JOIN msd_airline_voyage e ON e.airline_id = c.id  " +
                " WHERE c.dep_city='"+cityList.getDepCity()+"' and c.arr_city='"+cityList.getArrCity()+"'" ;
                if (cityList.getFlightType()==null){
                    sql+=" and c.flight_type="+cityList.getFlightType()+" ";
                }
                sql+="  and a.res_type=0 and a.airline_status=1 and a.is_invalid=0  AND b.flight_date = '"+cityList.getDepDate()+"' AND e.trip_index = 1 and SUBSTRING_INDEX(DATE_ADD(b.flight_date,INTERVAL e.days DAY),'-',2)='"+cityList.getMonth()+"' GROUP BY e.days";
                return sql;
    }
    public String searchDayAirlines(SearchDayAirlinesDto searchAirlineDto){
        StringBuffer sqlBuffer = new StringBuffer(
                "SELECT                                                                                                                                                                                              "
                        +"  mbsa.account_id, "
                        +"  mbsa.id,                                                                                                                                                                                          "
                        +"  mbsa.res_type,"
                        +"  mbsa.airline_status, "
                        +"	mbsa.airline_no,                                                                                                                                                                                  "
                        +"	mbsa.supplier_name,                                                                                                                                                                               "
                        +"	msda.voyage,                                                                                                                                                                                      "
                        +"  msda.flight_number,                                                                                                                                                                               "
                        +"  mbsa.manager,                                                                                                                                                                                     "
                        +"  mbsa.manager_id,                                                                                                                                                                                  "
                        +"	mbsaf.flight_date,                                                                                                                                                                                "
                        +"  DATE_ADD(mbsaf.flight_date,INTERVAL msdav.days DAY) as return_date,                                                                                                                               "
                        +"  (msdav.days+1) as days,"
                        +"  mbsaf.adult_price,                                                                                                                                                                                "
                        +"  mbsaf.child_price,                                                                                                                                                                                "
                        +" (case   "
                        +" when mbsa.airline_status =1 then  mbsaf.seat_count-IFNULL(seatMain.saldCount,0) "
                        +" when  mbsa.airline_status <> 1 then null  "
                        +" end) as unsaldCount, "
                        +"  msda.flight_type,                                                                                                                                                                                 "
                        +"  mbsa.created_time                                                                                                                                                                                 "
                        +"FROM                                                                                                                                                                                                "
                        +"	mbs_airline mbsa,                                                                                                                                                                                 "
                        +"	msd_airline msda                                                                                                                                                                                 "
                        +"  left join msd_airline_voyage msdav on msda.id = msdav.airline_id and msdav.trip_index = 1,                                                                                                                                                                        "
                        +"	mbs_airline_flight mbsaf left join                                                                                                                                                                       "
                        +"  (select mbsafs.flight_id,COUNT(*) as saldCount from mbs_airline_flight_seat mbsafs where mbsafs.account_id = mbsafs.owner_id and mbsafs.seat_status = 1  group by mbsafs.flight_id) seatMain on mbsaf.id = seatMain.flight_id   "
                        +"WHERE                                                                                                                                                                                               "
                        +"	mbsaf.airline_id = mbsa.id                                                                                                                                                                        "
                        +"AND mbsa.airline_id = msda.id                                                                                                                                                                       "
                        +"and mbsa.is_invalid = 0 "
                        +"and if(mbsa.res_type <> 0,mbsa.airline_status = 1,'1=1' ) "
        );
        if(StringUtils.isNotBlank(searchAirlineDto.getAirlineNo())){
            sqlBuffer.append(" and mbsa.airline_no like '%"+searchAirlineDto.getAirlineNo()+"%'");
        }
        if(searchAirlineDto.getAirlineStatus()!=null){
            sqlBuffer.append(" and mbsa.airline_status ="+searchAirlineDto.getAirlineStatus());
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getDepartureCity())){
            sqlBuffer.append(" and msda.voyage like '"+searchAirlineDto.getDepartureCity()+"%'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getArriveCity())){
            sqlBuffer.append(" and msda.voyage like '%"+searchAirlineDto.getArriveCity()+"'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getDepartureFlightNo())){
            sqlBuffer.append(" and msda.flight_number like '"+searchAirlineDto.getDepartureFlightNo()+"%'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getReturnFlightNo())){
            sqlBuffer.append(" and msda.flight_type <> 1 ");
            sqlBuffer.append(" and msda.flight_number like '%"+searchAirlineDto.getReturnFlightNo()+"'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getCharger())){
            sqlBuffer.append(" and mbsa.manager like '%"+searchAirlineDto.getCharger()+"%'");
        }
        if(searchAirlineDto.getDays()!=null){
            sqlBuffer.append(" and msdav.days = "+(searchAirlineDto.getDays()-1));
        }
        if(searchAirlineDto.getFlightType()!=null){
            sqlBuffer.append(" and msda.flight_type = "+ searchAirlineDto.getFlightType());
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getDepartureDateStart())){
            sqlBuffer.append(" and mbsaf.flight_date >= '"+searchAirlineDto.getDepartureDateStart()+"'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getDepartureDateEnd())){
            sqlBuffer.append(" and mbsaf.flight_date <= '"+searchAirlineDto.getDepartureDateEnd()+"'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getReturnDateStart())){
            sqlBuffer.append(" and DATE_ADD(mbsaf.flight_date,INTERVAL msdav.days DAY) >= '"+searchAirlineDto.getReturnDateStart()+"'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getReturnDateEnd())){
            sqlBuffer.append(" and DATE_ADD(mbsaf.flight_date,INTERVAL msdav.days DAY) <= '"+searchAirlineDto.getReturnDateEnd()+"'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getSupplierName())){
            sqlBuffer.append(" and mbsa.supplier_name like '%"+searchAirlineDto.getSupplierName()+"%'");
        }
        if(StringUtils.isNotBlank(searchAirlineDto.getSourceId())){
            if("1".equals(searchAirlineDto.getSourceId())) {
                sqlBuffer.append(" and mbsa.res_type = 0 ");
            } else {
                sqlBuffer.append(" and mbsa.res_type <>0 ");
            }
        }
        sqlBuffer.append(" order by ");
        if(searchAirlineDto.getPriceOrderBy()!=null){
            if(searchAirlineDto.getPriceOrderBy()==0) {
                sqlBuffer.append(" mbsaf.adult_price ,");
            } else{
                sqlBuffer.append(" mbsaf.adult_price desc ,");
            }
        }
        sqlBuffer.append(" mbsa.created_time desc,mbsa.airline_no,mbsaf.flight_date");
        return sqlBuffer.toString();
    }
    //更新每日航线资源
    public String updatePrice(PriceTemplateBean priceTemplateBean) {
        StringBuffer updateSqlBuf = new StringBuffer("update mbs_airline_flight set ");
        if(priceTemplateBean.getAdultPrice()!=null) {
            updateSqlBuf.append(" adult_price = " + priceTemplateBean.getAdultPrice() + ",");
        }
        if(priceTemplateBean.getChildPrice()!=null) {
            updateSqlBuf.append("child_price=" + priceTemplateBean.getChildPrice() + ",");
        }
        if(priceTemplateBean.getRecycleDay()!=null) {
            updateSqlBuf.append("recovery_date= DATE_ADD(flight_date,INTERVAL " + -priceTemplateBean.getRecycleDay() + " DAY),");
        }
        if(priceTemplateBean.getStoreType()!=null) {
            updateSqlBuf.append("sell_type=" + priceTemplateBean.getStoreType() + ",");
        }
        if(priceTemplateBean.getStoreCount()!=null) {
            updateSqlBuf.append("seat_count=" + priceTemplateBean.getStoreCount() + ",");
        }
        updateSqlBuf = new StringBuffer(updateSqlBuf.substring(0,updateSqlBuf.length()-1));
        updateSqlBuf.append(" where airline_id ='" + priceTemplateBean.getAirlineId() + "' and flight_date='" + priceTemplateBean.getFlightDate() + "'");
        return updateSqlBuf.toString();
    }
}
