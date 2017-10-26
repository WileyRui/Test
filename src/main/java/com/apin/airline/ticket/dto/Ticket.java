package com.apin.airline.ticket.dto;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class Ticket {
    private String airlineId;
    private String owner;
    private String currentDate;
    private String ownerId;
    private String userId;
    private String userName;
    private Integer seatNumber;
    private Integer status;

    public Ticket() {
    }

    public Ticket(Stock stock,Integer status){
        airlineId = stock.getAirlineId();
        seatNumber = stock.getStockNumber();
        ownerId = stock.getAccountId();
        currentDate = stock.getCurrentDate();
        this.status = status;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
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

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
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
