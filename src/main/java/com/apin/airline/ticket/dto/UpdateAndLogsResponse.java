package com.apin.airline.ticket.dto;

import com.apin.airline.common.entity.Log;

import java.io.Serializable;
import java.util.List;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class UpdateAndLogsResponse implements Serializable {

    private List<FlightSeatResponse> flightSeatResponses;

    private List<Log> mbsAirlineLogs;

    public List<FlightSeatResponse> getFlightSeatResponses() {
        return flightSeatResponses;
    }

    public void setFlightSeatResponses(List<FlightSeatResponse> flightSeatResponses) {
        this.flightSeatResponses = flightSeatResponses;
    }

    public List<Log> getMbsAirlineLogs() {
        return mbsAirlineLogs;
    }

    public void setMbsAirlineLogs(List<Log> mbsAirlineLogs) {
        this.mbsAirlineLogs = mbsAirlineLogs;
    }
}