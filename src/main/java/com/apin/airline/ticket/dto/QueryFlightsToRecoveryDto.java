package com.apin.airline.ticket.dto;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */

import java.io.Serializable;


/**
 * @author yangwei
 * @create 2017-09-07
 **/
public class QueryFlightsToRecoveryDto implements Serializable {
    private String id;
    private String accountId;
    private String flightId;
    private String airlineId;

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

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }
}
