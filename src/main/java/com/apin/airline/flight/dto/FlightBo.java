package com.apin.airline.flight.dto;

import java.util.List;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class FlightBo {
    private Integer recoveryDay;
    private int remain;
    private Integer flightType;
    private String voyage;
    private String airlineId;
    private String flightNum;
    private int total;
    private Integer handCount;
    private Integer autoCount;
    private Integer days;
    private String startTime;
    private String endTime;
    private int solded;

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    private List<String> dateList;

    public FlightBo() {
    }

    public Integer getRecoveryDay() {
        return recoveryDay;
    }

    public void setRecoveryDay(Integer recoveryDay) {
        this.recoveryDay = recoveryDay;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }


    public Integer getFlightType() {
        return flightType;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public int getTotal() {
        return solded+remain;
    }

    public void setTotal(int total) {
        this.total = solded+remain;
    }

    public Integer getHandCount() {
        return handCount;
    }

    public void setHandCount(Integer handCount) {
        this.handCount = handCount;
    }

    public Integer getAutoCount() {
        return autoCount;
    }

    public void setAutoCount(Integer autoCount) {
        this.autoCount = autoCount;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }




    public int getSolded() {
        return solded;
    }}

