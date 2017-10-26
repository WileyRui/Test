package com.apin.airline.ticket.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/11
 */
public class Stock {
    /**
     * 包机商id
     */
    private String accountId;
    /**
     * 航线id
     */
    private String airlineId;
    /**
     * 航班id
     */
    private String flightId;
    /**
     * 库存数量
     */
    private Integer stockNumber;
    /**
     * 成人价格
     */
    private BigDecimal adultPrice;
    /**
     * 儿童价格
     */
    private BigDecimal childPrice;
    /**
     * 日期集合
     */
    private List<String> dateList;
    /**
     * 日志操作id
     */
    private String userId;
    /**
     * 日志操作者名字
     */
    private String userName;

    /**
     * 来源
     */
    private String eventSource;

    /**
     * 选择日期
     */
    private String currentDate;

    public Stock() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
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

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
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

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
