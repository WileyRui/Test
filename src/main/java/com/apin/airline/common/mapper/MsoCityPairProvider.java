package com.apin.airline.common.mapper;

import com.apin.airline.tag.citypair.CityPairDTO;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhaowei on 2017/10/14.
 */
public class MsoCityPairProvider {
    public final static String EXTRACTTAGID="7c7d704ac87f4aa28c366e7b005e2bc9";
    public String listCityPairs(CityPairDTO cityPairDTO){
        StringBuffer sqlbuffer = new StringBuffer("SELECT cp.*,IF(rt.id is null,0,1) as isExtract FROM mso_citypair cp " +
                "left join mbs_resource_tag rt on cp.id = rt.ref_id and rt.tag_id = '"+EXTRACTTAGID+"' where 1=1 ");
        if(StringUtils.isNotBlank(cityPairDTO.getDepCity())){
            sqlbuffer.append(" and cp.dep_city like '%"+cityPairDTO.getDepCity()+"%'");
        }
        if(StringUtils.isNotBlank(cityPairDTO.getArrCity())){
            sqlbuffer.append(" and cp.arr_city like '%"+cityPairDTO.getArrCity()+"%'");
        }
        if(cityPairDTO.getFlightType()!=null){
            sqlbuffer.append(" and cp.flight_type ="+cityPairDTO.getFlightType());
        }
        if(cityPairDTO.getIsExtract()!=null){
            //精品
            if(cityPairDTO.getIsExtract()==1){
                sqlbuffer.append(" and rt.id is not null");
            }
            //非精品
            else {
                sqlbuffer.append(" and rt.id is null");
            }
        }
        if(StringUtils.isNotBlank(cityPairDTO.getOperatorUserName())){
            sqlbuffer.append(" and cp.update_user_name like '%"+cityPairDTO.getOperatorUserName()+"%'");
        }
        sqlbuffer.append(" order by cp.update_time desc,cp.created_time desc");
        return sqlbuffer.toString();
    }
}
