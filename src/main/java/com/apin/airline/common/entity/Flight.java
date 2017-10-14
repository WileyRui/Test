package com.apin.airline.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 供应商航班资源实体类
 */
public class Flight implements Serializable {
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
     * 切位类型 0:未定义;1:软切;2:硬切
     */
    private Byte sellType;

    /**
     * 执飞日期
     */
    private Date flightDate;

    /**
     * 舱位数量
     */
    private Integer seatCount;

    /**
     * 成人票价
     */
    private BigDecimal adultPrice;

    /**
     * 儿童票价
     */
    private BigDecimal childPrice;

    /**
     * 预警阈值(库存超过该值时预警)
     */
    private Integer alertThreshold;

    /**
     * 预警日期
     */
    private Date alertDate;

    /**
     * 强制回收日期
     */
    private Date recoveryDate;

    /**
     * 开票日期
     */
    private Date ticketDate;

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

    public Byte getSellType() {
        return sellType;
    }

    public void setSellType(Byte sellType) {
        this.sellType = sellType;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
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

    public Integer getAlertThreshold() {
        return alertThreshold;
    }

    public void setAlertThreshold(Integer alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public Date getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }
}
