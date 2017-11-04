package com.apin.airline.tag.city;

import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;

/**
 * Created by zhaowei on 2017/10/16.
 */
public interface CityService {
    public Reply listCitys(String Authorization, CityDTO cityDTO) ;
    public Reply setCityTag(AccessToken accessToken, CityDTO cityDTO);
    public Reply setCityImg(String Authorization, CityDTO cityDTO) ;
}
