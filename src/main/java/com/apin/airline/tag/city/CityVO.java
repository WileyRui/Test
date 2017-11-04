package com.apin.airline.tag.city;

import java.util.List;

/**
 * 显示城市VO
 * Created by zhaowei on 2017/10/16.
 */
public class CityVO {
    private String id;
    //0=出发城市热门，1=目的城市热门
    private List<Integer> tag;
    private String cityCode;
    private String cityName;
    private String imgUrl;
    private String countryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getTag() {
        return tag;
    }

    public void setTag(List tag) {
        this.tag = tag;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
