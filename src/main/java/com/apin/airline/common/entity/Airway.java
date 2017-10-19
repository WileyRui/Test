package com.apin.airline.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Airway implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 航司编码
     */
    private String iataCode;

    /**
     * 航司名称
     */
    private String companyName;

    /**
     * 国家代码
     */
    private String nationCode;

    /**
     * 航司LOGO
     */
    private String logoIco;

    /**
     * 是否失效 false:正常;true:失效
     */
    private Boolean invalid;

    /**
     * 更新用户
     */
    private String updateUser;

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

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getLogoIco() {
        return logoIco;
    }

    public void setLogoIco(String logoIco) {
        this.logoIco = logoIco;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
