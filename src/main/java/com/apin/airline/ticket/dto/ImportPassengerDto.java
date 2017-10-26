package com.apin.airline.ticket.dto;

import java.util.List;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class ImportPassengerDto {
    private List<PassengerInfoDto> list;
    private String ownerId;
    private String flightId;

    public ImportPassengerDto() {
    }

    public List<PassengerInfoDto> getList() {
        return list;
    }

    public void setList(List<PassengerInfoDto> list) {
        this.list = list;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}
