package com.apin.airline.line.dto;

import java.math.BigDecimal;

/**
 * @author wiley
 * @date 2017/10/16
 * @remark 最新航线封装类
 */
public class NewLine {
    private String lineId;
    private BigDecimal price;
    private Integer saled;
    private String departDate;
    private Byte flightType;
    private String depCity;
    private String arrCity;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSaled() {
        return saled < 0 ? 0 : saled;
    }

    public void setSaled(Integer saled) {
        this.saled = saled;
    }

    public Byte getFlightType() {
        return flightType;
    }

    public void setFlightType(Byte flightType) {
        this.flightType = flightType;
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
}
