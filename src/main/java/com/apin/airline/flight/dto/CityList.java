package com.apin.airline.flight.dto;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/16
 */
public class CityList {
    private String depCity;
    private String arrCity;
    private Integer flightType;
    private String depDate;
    private String arrDate;


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

    public Integer getFlightType() {
        return flightType;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }
}
