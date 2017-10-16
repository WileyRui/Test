package com.apin.airline.flight.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/13
 */
public class FlightDetail {
    private List<String> dateList;
    private Integer seat;
    private BigDecimal price;
    private String img;

    public FlightDetail() {
    }

    public List<String> getDateList() {
        return dateList;
    }


    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
