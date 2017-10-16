package com.apin.airline.common.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 航线基础数据实体类
 */
public class Airline implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 航线HASH值
     */
    private String hashKey;

    /**
     * 航程类型 0:未定义;1:单程;2:往返;3:多程
     */
    private Byte flightType;

    /**
     * 出发城市
     */
    private String depCity;

    /**
     * 到达城市
     */
    private String arrCity;

    /**
     * 往返行程天数,单程为0 (当天往返为0)
     */
    private Integer days;

    /**
     * 航程 拼接的中文字符串,如 北京<->曼谷;北京->曼谷->巴厘岛->北京
     */
    private String voyage;

    /**
     * 航班号 全部航班号拼接,中划线分隔
     */
    private String flightNumber;

    /**
     * 计划起飞时间
     */
    private Time flightTime;

    /**
     * 每周飞行班次 周日开始 0,1,0,1,0,1,0代表每周135有航班飞行
     */
    private String weekFlights;

    /**
     * 是否失效 false:正常;true:失效
     */
    private Boolean invalid;

    /**
     * 创建人
     */
    private String creatorUser;

    /**
     * 创建用户ID
     */
    private String creatorUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 航程明细
     */
    private List<LineDetail> details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public Byte getFlightType() {
        return flightType;
    }

    public void setFlightType(Byte flightType) {
        this.flightType = flightType;
    }

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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Time getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Time flightTime) {
        this.flightTime = flightTime;
    }

    public String getWeekFlights() {
        return weekFlights;
    }

    public void setWeekFlights(String weekFlights) {
        this.weekFlights = weekFlights;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public String getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(String creatorUser) {
        this.creatorUser = creatorUser;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
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

    public List<LineDetail> getDetails() {
        return details;
    }

    public void setDetails(List<LineDetail> details) {
        this.details = details;
    }
}
