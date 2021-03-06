package com.apin.airline.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 供应商航线资源实体类
 */
public class LineDetail implements Serializable {
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
     * 航班号
     */
    private String flightNo;

    /**
     * 出发日期
     */
    private String beginDate;

    /**
     * 航空公司名称
     */
    private String flightCompany;

    /**
     * 航班属性 0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知
     */
    private Byte fcategory;

    /**
     * 出发城市名
     */
    private String flightDep;

    /**
     * 到达城市名
     */
    private String flightArr;

    /**
     * 出发机场名
     */
    private String flightDepAirport;

    /**
     * 到达机场名
     */
    private String flightArrAirport;

    /**
     * 出发地机场三字码
     */
    private String flightDepcode;

    /**
     * 目的地机场三字码
     */
    private String flightArrcode;

    /**
     * 计划起飞时间
     */
    private String flightDeptimePlanDate;

    /**
     * 计划到达时间
     */
    private String flightArrtimePlanDate;

    /**
     * 是否经停 false:不经停;true:经停
     */
    private Boolean stopFlag;

    /**
     * 飞行班次 周日开始 0,1,0,1,0,1,0代表每周135有航班飞行
     */
    private String flights;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 返程天数
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

    public Byte getTripIndex() {
        return tripIndex;
    }

    public void setTripIndex(Byte tripIndex) {
        this.tripIndex = tripIndex;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public void setFlightCompany(String flightCompany) {
        this.flightCompany = flightCompany;
    }

    public Byte getFcategory() {
        return fcategory;
    }

    public void setFcategory(Byte fcategory) {
        this.fcategory = fcategory;
    }

    public String getFlightDep() {
        return flightDep;
    }

    public void setFlightDep(String flightDep) {
        this.flightDep = flightDep;
    }

    public String getFlightArr() {
        return flightArr;
    }

    public void setFlightArr(String flightArr) {
        this.flightArr = flightArr;
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

    public String getFlightDepcode() {
        return flightDepcode;
    }

    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode;
    }

    public String getFlightArrcode() {
        return flightArrcode;
    }

    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode;
    }

    public String getFlightDeptimePlanDate() {
        return flightDeptimePlanDate.length() > 11 ? flightDeptimePlanDate.substring(11) : flightDeptimePlanDate;
    }

    public void setFlightDeptimePlanDate(String flightDeptimePlanDate) {
        this.flightDeptimePlanDate = flightDeptimePlanDate;
    }

    public String getFlightArrtimePlanDate() {
        return flightArrtimePlanDate.length() > 11 ? flightArrtimePlanDate.substring(11) : flightArrtimePlanDate;
    }

    public void setFlightArrtimePlanDate(String flightArrtimePlanDate) {
        this.flightArrtimePlanDate = flightArrtimePlanDate;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
