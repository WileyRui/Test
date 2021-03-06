package com.apin.airline.flight.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/13
 */
public class FlightDetail {
    private List<DayPrice> dateList;
    private Integer remainCount;
    private Integer total;
    private Integer soldCount;
    private BigDecimal basePrice;
    private String arrCityImgUrl;
    private Integer days;
    private String depCity;
    private String arrCity;
    private String retDate;
    private String startDate;
    private String endDate;
    private Integer flightType;

    public FlightDetail() {
    }

    public List<DayPrice> getDateList() {
        return dateList;
    }


    public void setDateList(List<DayPrice> dateList) {
        this.dateList = dateList;
    }


    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSoldCount() {
        return soldCount!=null&&soldCount>0?soldCount:0;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getArrCityImgUrl() {
        return arrCityImgUrl;
    }

    public void setArrCityImgUrl(String arrCityImgUrl) {
        this.arrCityImgUrl = arrCityImgUrl;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getFlightType() {
        return flightType;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }
}
