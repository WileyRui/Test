package com.apin.airline.ticket.dto;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class GetEnableSeatDto implements Serializable {

    private String accountId;
    private String dealerId;
    private String flightId;

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
}
