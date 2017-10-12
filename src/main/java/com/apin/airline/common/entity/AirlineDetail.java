package com.apin.airline.common.entity;

import java.io.Serializable;
import java.sql.Time;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 航线航程明细类
 */
public class AirlineDetail implements Serializable {
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
     * 航段序号,第一程为0
     */
    private Byte tripIndex;

    /**
     * 航空公司名称
     */
    private String company;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 出发机场名
     */
    private String depAirport;

    /**
     * 到达机场名
     */
    private String arrAirport;

    /**
     * 计划起飞时间
     */
    private Time deptime;

    /**
     * 计划到达时间
     */
    private Time arrtime;

    /**
     * 是否经停 false:不经停;true:经停
     */
    private Boolean stop;

    /**
     * 飞行班次 周日开始 0,1,0,1,0,1,0代表每周135有航班飞行
     */
    private String flights;

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

    public Byte getTripIndex() {
        return tripIndex;
    }

    public void setTripIndex(Byte tripIndex) {
        this.tripIndex = tripIndex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    public Time getDeptime() {
        return deptime;
    }

    public void setDeptime(Time deptime) {
        this.deptime = deptime;
    }

    public Time getArrtime() {
        return arrtime;
    }

    public void setArrtime(Time arrtime) {
        this.arrtime = arrtime;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public String getFlights() {
        return flights;
    }

    public void setFlights(String flights) {
        this.flights = flights;
    }
}
