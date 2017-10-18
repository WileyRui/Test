package com.apin.airline.common.mapper;

import com.apin.airline.flight.dto.CityList;
import org.apache.commons.lang.StringUtils;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/16
 */
public class AspectSql {
    public String selectFlightList(CityList cityList){
        String sql="SELECT min(b.adult_price) basePrice,sum(a.seat_count) total,min(b.flight_date) startDate,max(b.flight_date) endDate," +
                "c.dep_city depCity,c.arr_city arrCity,c.flight_type ,e.img_url arrCityImgUrl,sum(b.seat_count) remainCount FROM mbs_airline a" +
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
               sql+=" and (a.manager_id='' or a.manager_id is null) and a.airline_status=1 and c.is_invalid=0   GROUP BY c.voyage order by f.en_name, e.en_name";
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
                sql+="  and (a.manager_id='' or a.manager_id is null) and a.airline_status=1 and c.is_invalid=0  AND b.flight_date = '"+cityList.getDepDate()+"' AND e.trip_index = 1 and SUBSTRING_INDEX(DATE_ADD(b.flight_date,INTERVAL e.days DAY),'-',2)='"+cityList.getMonth()+"' GROUP BY e.days";
                return sql;
    }
}
