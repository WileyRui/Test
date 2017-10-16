package com.apin.airline.flight.dto;

import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/13
 */
public class SearchDto {

    private List<CityList> cityList;
    private String depDate;
    private String arrDate;

    public SearchDto() {
    }

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
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
}
