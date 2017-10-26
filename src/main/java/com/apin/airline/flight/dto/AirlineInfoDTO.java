package com.apin.airline.flight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 航线信息列表Dto
 * Created by zhaowei on 2017/9/13.
 */
public class AirlineInfoDTO {
    private String id;
    private String accountId;
    private String airlineNo;
    private String supplierName;
    private String voyage;
    private String flightNumber;
    private String manager;
    private String managerId;
    private Date flightDate;
    private Date returnDate;
    private Integer days;
    private Integer resType;
    private BigDecimal adultPrice;
    private Integer unsaldCount;
    private Integer flightType;
    private Date createdTime;
    private Integer airlineStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirlineNo() {
        return airlineNo;
    }

    public void setAirlineNo(String airlineNo) {
        this.airlineNo = airlineNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(BigDecimal adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Integer getUnsaldCount() {
        return unsaldCount;
    }

    public void setUnsaldCount(Integer unsaldCount) {
        this.unsaldCount = unsaldCount;
    }

    public Integer getFlightType() {
        return flightType;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getAirlineStatus() {
        return airlineStatus;
    }

    public void setAirlineStatus(Integer airlineStatus) {
        this.airlineStatus = airlineStatus;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }
}
