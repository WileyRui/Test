package com.apin.airline.flight.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class StockBo {
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    private int solded;
    private int remain;
    private int total;
    private Date flightDate;
    private Date recoveryDate;
    private String flightId;
    private Integer autoCount;
    private Integer handCount;

    public StockBo() {
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

    public Integer getSolded() {
        return solded;
    }

    public void setSolded(Integer solded) {
        this.solded = solded;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getTotal() {
        return remain+solded;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Integer getAutoCount() {
        return autoCount;
    }

    public void setAutoCount(Integer autoCount) {
        this.autoCount = autoCount;
    }

    public Integer getHandCount() {
        return handCount;
    }

    public void setHandCount(Integer handCount) {
        this.handCount = handCount;
    }}