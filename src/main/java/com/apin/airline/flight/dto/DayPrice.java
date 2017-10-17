package com.apin.airline.flight.dto;

import java.math.BigDecimal;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/17
 */
public class DayPrice {
    private String retDate;
    private BigDecimal basePrice;

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}
