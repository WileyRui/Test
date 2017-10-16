package com.apin.airline.flight.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
}