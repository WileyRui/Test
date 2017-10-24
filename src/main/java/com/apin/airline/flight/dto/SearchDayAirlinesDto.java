package com.apin.airline.flight.dto;

import java.io.Serializable;

/**
 * 航线查询条件Dto
 */
public class SearchDayAirlinesDto implements Serializable {
    //页码
    private Integer page;
    //每页记录数
    private Integer size;
    //出发城市
    private String departureCity;
    //到达城市
    private String arriveCity;
    //出发日期从
    private String departureDateStart;
    //出发日期到
    private String departureDateEnd;
    //去程航班号
    private String departureFlightNo;
    //返程航班号
    private String returnFlightNo;
    //返程日期从
    private String returnDateStart;
    //返程日期到
    private String returnDateEnd;
    //航线资源号
    private String airlineNo;
    //行程天数
    private Integer days;
    //供应商名称
    private String supplierName;
    //资源负责人
    private String charger;
    //航线状态
    private Integer airlineStatus;
    //行程类型
    private Integer flightType;

    //价格排序
    private Integer priceOrderBy;

    //查询crm来源
    private String sourceId;

    public Integer getPriceOrderBy() {
        return priceOrderBy;
    }

    public void setPriceOrderBy(Integer priceOrderBy) {
        this.priceOrderBy = priceOrderBy;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public String getDepartureDateStart() {
        return departureDateStart;
    }

    public void setDepartureDateStart(String departureDateStart) {
        this.departureDateStart = departureDateStart;
    }

    public String getDepartureDateEnd() {
        return departureDateEnd;
    }

    public void setDepartureDateEnd(String departureDateEnd) {
        this.departureDateEnd = departureDateEnd;
    }

    public String getDepartureFlightNo() {
        return departureFlightNo;
    }

    public void setDepartureFlightNo(String departureFlightNo) {
        this.departureFlightNo = departureFlightNo;
    }

    public String getReturnFlightNo() {
        return returnFlightNo;
    }

    public void setReturnFlightNo(String returnFlightNo) {
        this.returnFlightNo = returnFlightNo;
    }

    public String getReturnDateStart() {
        return returnDateStart;
    }

    public void setReturnDateStart(String returnDateStart) {
        this.returnDateStart = returnDateStart;
    }

    public String getReturnDateEnd() {
        return returnDateEnd;
    }

    public void setReturnDateEnd(String returnDateEnd) {
        this.returnDateEnd = returnDateEnd;
    }

    public String getAirlineNo() {
        return airlineNo;
    }

    public void setAirlineNo(String airlineNo) {
        this.airlineNo = airlineNo;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public Integer getAirlineStatus() {
        return airlineStatus;
    }

    public void setAirlineStatus(Integer airlineStatus) {
        this.airlineStatus = airlineStatus;
    }

    public Integer getFlightType() {
        return flightType;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
