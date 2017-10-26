package com.apin.airline.ticket.dto;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class UpdateSeatsOwnerDto implements Serializable {
    private String owner;
    private String accountId;
    private String dealerId;
    private String flightId;
    private Integer recoveryNum;

    private String userName;
    private String userId;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Integer getRecoveryNum() {
        return recoveryNum;
    }

    public void setRecoveryNum(Integer recoveryNum) {
        this.recoveryNum = recoveryNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

