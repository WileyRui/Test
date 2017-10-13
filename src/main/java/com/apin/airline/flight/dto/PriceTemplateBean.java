package com.apin.airline.flight.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 更新价格模板Dto
 */
public class PriceTemplateBean implements Serializable {
    private String airlineId;
    private String airlineNo;
    private String flightDate;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    private Integer storeType;
    private Integer storeCount;
    private Integer recycleDay;
    private List<String> flightDates;
    private String accountId;
    private String userId;
    private String userName;
    public String getAirlineNo() {
        return airlineNo;
    }

    public void setAirlineNo(String airlineNo) {
        this.airlineNo = airlineNo;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public BigDecimal getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(BigDecimal adultPrice) {
        this.adultPrice = adultPrice;
    }

    public BigDecimal getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(BigDecimal childPrice) {
        this.childPrice = childPrice;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public Integer getRecycleDay() {
        return recycleDay;
    }

    public void setRecycleDay(Integer recycleDay) {
        this.recycleDay = recycleDay;
    }

    public List<String> getFlightDates() {
        return flightDates;
    }

    public void setFlightDates(List<String> flightDates) {
        this.flightDates = flightDates;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
