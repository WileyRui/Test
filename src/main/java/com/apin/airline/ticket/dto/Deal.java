package com.apin.airline.ticket.dto;

import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/11
 */
public class Deal {
    /**
     * 航线id
     */
    private String airlineId;
    /**
     * 包机商id
     */
    private String accountId;
    /**
     * 日期列表
     */
    private List<String> dateList;
    /**
     * 分配的总座位数
     */
    private Integer total;
    /**
     * 持有者信息
     */
    private List<OwnerElement> ownerElementList;

    private String userId;
    private String userName;

    public Deal() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<OwnerElement> getOwnerElementList() {
        return ownerElementList;
    }

    public void setOwnerElementList(List<OwnerElement> ownerElementList) {
        this.ownerElementList = ownerElementList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
