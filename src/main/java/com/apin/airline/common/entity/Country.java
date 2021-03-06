package com.apin.airline.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/14
 * @remark 国家基础数据实体类
 */
public class Country implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 区域代码
     */
    private String zoneCode;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 国家名称
     */
    private String countryName;

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

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
