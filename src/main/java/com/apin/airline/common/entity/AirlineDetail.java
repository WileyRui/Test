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
    private String flightCompany;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 出发机场名
     */
    private String flightDepAirport;

    /**
     * 到达机场名
     */
    private String flightArrAirport;

    /**
     * 计划起飞时间
     */
    private Time flightDeptimePlanDate;

    /**
     * 计划到达时间
     */
    private Time flightArrtimePlanDate;

    /**
     * 是否经停 false:不经停;true:经停
     */
    private Boolean stopFlag;

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

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getFlights() {
        return flights;
    }

    public void setFlights(String flights) {
        this.flights = flights;
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public void setFlightCompany(String flightCompany) {
        this.flightCompany = flightCompany;
    }

    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport;
    }

    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport;
    }

    public Time getFlightDeptimePlanDate() {
        return flightDeptimePlanDate;
    }

    public void setFlightDeptimePlanDate(Time flightDeptimePlanDate) {
        this.flightDeptimePlanDate = flightDeptimePlanDate;
    }

    public Time getFlightArrtimePlanDate() {
        return flightArrtimePlanDate;
    }

    public void setFlightArrtimePlanDate(Time flightArrtimePlanDate) {
        this.flightArrtimePlanDate = flightArrtimePlanDate;
    }
}
