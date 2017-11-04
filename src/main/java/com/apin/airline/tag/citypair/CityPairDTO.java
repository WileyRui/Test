package com.apin.airline.tag.citypair;

import java.io.Serializable;

/**
 * Created by zhaowei on 2017/10/14.
 */
public class CityPairDTO implements Serializable {
    private String id;
    //出发城市
    private String depCity;
    //目的城市
    private String arrCity;
    //航班类型 1、单程；2、往返；3、多程
    private Integer flightType;
    //操作人
    private String operatorUserName;
    //null 为全部，0=非精品，1=精品
    private Integer isExtract;
    //页码
    private Integer page;
    //每页记录数
    private Integer size;
    //新增模糊查询条件
    private String str;

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

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }

    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }

    public Integer getIsExtract() {
        return isExtract;
    }

    public void setIsExtract(Integer isExtract) {
        this.isExtract = isExtract;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
