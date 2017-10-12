package com.apin.airline.common.entity;

import java.io.Serializable;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 航线航程明细实体类
 */
public class Voyage  implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 航线ID
     */
    private String airlineId;

    /**
     * 航班信息ID
     */
    private String flightInfoId;

    /**
     * 航段序号,第一程为0
     */
    private Byte tripIndex;

    /**
     * 出发延后天数(x天y晚的y),第一程为0
     */
    private Integer days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getFlightInfoId() {
        return flightInfoId;
    }

    public void setFlightInfoId(String flightInfoId) {
        this.flightInfoId = flightInfoId;
    }

    public Byte getTripIndex() {
        return tripIndex;
    }

    public void setTripIndex(Byte tripIndex) {
        this.tripIndex = tripIndex;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
