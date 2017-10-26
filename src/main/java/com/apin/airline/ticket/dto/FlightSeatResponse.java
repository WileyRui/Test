package com.apin.airline.ticket.dto;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class FlightSeatResponse implements Serializable {
    private static final long serialVersionUID = -1L;
    private String id;
    private String accountId;
    private String flightId;
    private String owner;
    private String ownerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
