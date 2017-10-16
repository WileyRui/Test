package com.apin.airline.common.entity;

import java.io.Serializable;

/**
 * @author wiley
 * @date 2017/10/10
 * @remark 航程明细封装类
 */
public class FlightDetail implements Serializable{
    private static final long serialVersionUID = -1L;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 出发机场三字码
     */
    private String depAirportCode;

    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 返程天数
     */
    private String days;

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
