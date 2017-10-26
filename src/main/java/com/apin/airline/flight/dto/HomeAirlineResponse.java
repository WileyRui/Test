package com.apin.airline.flight.dto;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class HomeAirlineResponse implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 航线ID
     */
    private String id;

    /**
     * 城市 示例：杭州<->上海
     */
    private String voyage;

    /**
     * 航班号 示例：MU111|MU222
     */
    private String flightNumber;

    /**
     * 航程类型  航程类型：0、未定义；1、单程；2、往返；3、多程
     */
    private String flightType;

    /**
     * 销售开始日期(出发日期)
     */
    private String departureStart;

    /**
     * 销售截止日期(出发日期)
     */
    private String departureEnd;

    /**
     * 天数（夜数+1）
     */
    private int days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getDepartureStart() {
        return departureStart;
    }

    public void setDepartureStart(String departureStart) {
        this.departureStart = departureStart;
    }

    public String getDepartureEnd() {
        return departureEnd;
    }

    public void setDepartureEnd(String departureEnd) {
        this.departureEnd = departureEnd;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}