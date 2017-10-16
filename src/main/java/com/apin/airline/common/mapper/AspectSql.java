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
        String sql="SELECT min(b.adult_price) price,sum(a.seat_count),min(b.flight_date),max(b.flight_date)," +
                "c.voyage,e.img_url,sum(b.seat_count) seat FROM mbs_airline a" +
                "JOIN mbs_airline_flight b ON a.id = b.airline_id" +
                "JOIN msd_airline c ON a.airline_id = c.id" +
                "JOIN msd_city e on c.arr_city=e.city_name " +
                "WHERE";
                if(StringUtils.isNotBlank(cityList.getArrCity())){
                    sql+=" c.arr_city='"+cityList.getArrCity()+"'";
                }
                if(StringUtils.isNotBlank(cityList.getDepCity())){
                    sql+=" c.dep_city='"+cityList.getDepCity()+"'";
                }
               sql+=" and c.flight_type="+cityList.getFlightType()+" GROUP BY c.voyage order by flight_date";
        return sql;
    }
}
