package com.apin.airline.tag.airway;

import com.apin.util.pojo.Reply;

/**
 * Created by zhaowei on 2017/10/16.
 */
public interface AirWayService {
    public Reply updateAirWay(String Authorization, AirWayDTO airWayDTO);
    public Reply listAirWays(String Authorization, AirWayDTO airWayDTO);
}
