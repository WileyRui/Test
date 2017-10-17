package com.apin.airline.flight.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @author songjia
 * @create 2017-10-16 9:13
 * @Remark: 响应航线查询结果实体类
 */
public class ResponseAirlineDto implements Serializable {
    /**
     * 出发城市名
     */
    private String depCity;
    /**
     * 到达城市名
     */
    private String arrCity;
    /**
     * 到达城市图片Url
     */
    private String arrCityImgUrl;
    /**
     * 航程类型：0、未定义；1、单程；2、往返；3、多程
     */
    private Integer flightType;
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 底价
     */
    private BigDecimal basePrice;
    /**
     * 已售数量
     */
    private Integer soldCount;
    /**
     * 剩余数量
     */
    private Integer remainCount;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 去程航班号
     */
    private String depNum;
    /**
     * 返程航班号
     */
    private String arrNum;

    /**
     * 飞行信息
     *
     */
    private List<AirlineInfo> airlineInfos;

    /**
     * 航程天数
     */
    private Integer days;

    /**
     * 返程出发日期
     */
    private String retDate;

    /**
     * 航线id
     */
    private String airlineId;

    public ResponseAirlineDto() {
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

    public String getArrCityImgUrl() {
        return arrCityImgUrl;
    }

    public void setArrCityImgUrl(String arrCityImgUrl) {
        this.arrCityImgUrl = arrCityImgUrl;
    }

    public Integer getFlightType() {
        return flightType;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getSoldCount() {
        return total-remainCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDepNum() {
        return depNum;
    }

    public void setDepNum(String depNum) {
        this.depNum = depNum;
    }

    public String getArrNum() {
        return arrNum;
    }

    public void setArrNum(String arrNum) {
        this.arrNum = arrNum;
    }

    public List<AirlineInfo> getAirlineInfo() {
        return airlineInfos;
    }

    public void setAirlineInfo(List<AirlineInfo> airlineInfo) {
        this.airlineInfos = airlineInfo;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }
}