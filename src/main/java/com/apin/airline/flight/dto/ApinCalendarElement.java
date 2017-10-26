package com.apin.airline.flight.dto;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Info Created by Administrator on 2017/9/4.
 * @Remark:日库存历中子元素的内容
 */
public class ApinCalendarElement implements Serializable{
    private static final long serialVersionUID = -1L;
    //启程日期
    private String departDate;
    //返回日期
    private String backDate;
    //已售
    private Integer sold;
    //下级剩余
    private Integer surplus;
    //未分配
    private Integer unallocated;
    //是否预警
    private Boolean alertStatus;
    //是否暂无班期
    private Boolean freeStatus;
    //是否可修改库存
    private Boolean ifAllocated;
    //成人价
    private BigDecimal adultPrice;
    //儿童价
    private BigDecimal childPrice;
    //是否过期
    private Boolean isExpire;
    //总库存
    private Integer seatCount;
    //收位时间
    private String recoveryDate;

    public ApinCalendarElement() {
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public Integer getUnallocated() {
        return unallocated;
    }

    public void setUnallocated(Integer unallocated) {
        this.unallocated = unallocated;
    }

    public Boolean getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(Boolean alertStatus) {
        this.alertStatus = alertStatus;
    }

    public Boolean getFreeStatus() {
        return freeStatus;
    }

    public void setFreeStatus(Boolean freeStatus) {
        this.freeStatus = freeStatus;
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

    public Boolean getIfAllocated() {
        return ifAllocated;
    }

    public void setIfAllocated(Boolean ifAllocated) {
        this.ifAllocated = ifAllocated;
    }

    public Boolean getExpire() {
        return isExpire;
    }

    public void setExpire(Boolean expire) {
        isExpire = expire;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public String getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(String recoveryDate) {
        this.recoveryDate = recoveryDate;
    }
}
