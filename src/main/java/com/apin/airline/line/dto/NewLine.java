package com.apin.airline.line.dto;

/**
 * @author wiley
 * @date 2017/10/16
 * @remark 最新航线封装类
 */
public class NewLine {
    private String lineId;
    private String voyage;
    private String price;
    private String saled;
    private String departDate;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSaled() {
        return saled;
    }

    public void setSaled(String saled) {
        this.saled = saled;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }
}
