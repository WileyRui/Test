package com.apin.airline.tag.city;

import java.io.Serializable;

/**
 * 查询城市DTO
 * Created by zhaowei on 2017/10/16.
 */
public class CityDTO implements Serializable {
    private String id;
    private String countryName;
   //国家ID
    private String countryId;
    //城市名称
    private String cityName;
    private String imgBase64;
    private String imgUrl;
    //城市编码
    private String cityCode;
    //描述
    private String description;
    //英文名
    private String enName;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //拼音
    private String pinyinFirst;
    //热门查询条件 null=全部，0=出发城市热门，1=目的城市热门
    private Integer tag;
    //设置出发热门 1=热门
    private Integer depHot;
    //设置目的热门 1=热门
    private Integer arrHot;
    private Integer page;
    private Integer size;
    //airline偏移量
    private Integer offset;
    //airline记录数量
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDepHot() {
        return depHot;
    }

    public void setDepHot(Integer depHot) {
        this.depHot = depHot;
    }

    public Integer getArrHot() {
        return arrHot;
    }

    public void setArrHot(Integer arrHot) {
        this.arrHot = arrHot;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPinyinFirst() {
        return pinyinFirst;
    }

    public void setPinyinFirst(String pinyinFirst) {
        this.pinyinFirst = pinyinFirst;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
