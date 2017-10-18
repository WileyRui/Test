package com.apin.airline.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/14
 * @remark 城市基础数据实体类
 */
public class City implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 国家代码 例如:CN(中国)
     */
    private String countryId;

    /**
     * 国家名称
     */
    private String countryName;

    /**
     * 机场名称,逗号分隔
     */
    private String airports;

    /**
     * 机场三字码,逗号分隔
     */
    private String codes;

    /**
     * 城市三字码 例如:PEK(北京)
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市拼音 例如:BeiJing(北京)
     */
    private String enName;

    /**
     * 城市拼音首字母 例如:BJ(北京)
     */
    private String pinyinFirst;

    /**
     * 城市图片
     */
    private String imgUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 是否失效 false:正常;true:失效
     */
    private Boolean invalid;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 记录数量
     */
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAirports() {
        return airports;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public void setAirports(String airports) {
        this.airports = airports;
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPinyinFirst() {
        return pinyinFirst;
    }

    public void setPinyinFirst(String pinyinFirst) {
        this.pinyinFirst = pinyinFirst;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
}
