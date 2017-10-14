package com.apin.airline.flight.dto;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/13
 */
public class SearchDto {
    private String departCity;
    private String destCity;
    private Integer type;
    private String departDate;
    private String destDate;

    public SearchDto() {
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getDestDate() {
        return destDate;
    }

    public void setDestDate(String destDate) {
        this.destDate = destDate;
    }
}
