package com.apin.airline.line.dto;

import java.math.BigDecimal;

/**
 * @author wiley
 * @date 2017/10/16
 * @remark 最新航线封装类
 */
public class NewLine {
    private String lineId;
    private String voyage;
    private BigDecimal price;
    private Integer saled;
    private String departDate;
    private Byte flightType;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
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
        return saled;
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
}
