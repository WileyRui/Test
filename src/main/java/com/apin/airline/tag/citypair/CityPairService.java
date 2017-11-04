package com.apin.airline.tag.citypair;

import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;

/**
 * Created by zhaowei on 2017/10/14.
 */
public interface CityPairService {
    public Reply listCityPairs(CityPairDTO cityPairDTO);
    public Reply insertCityPair(String Authorization, CityPairDTO cityPairDTO);
    public Reply deleteCityPair(String id);
    public boolean existCityPair(CityPairDTO cityPairDTO);
    public Reply setExtract(AccessToken accessToken, String id);
    public Reply cancelExtract(String id);
}
