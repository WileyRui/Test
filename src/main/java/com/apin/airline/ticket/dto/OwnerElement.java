package com.apin.airline.ticket.dto;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/11
 */
public class OwnerElement {
    /**
     * 持有者名字
     */
    private String owner;
    /**
     * 持有者id
     */
    private String ownerId;
    /**
     * 座位数量
     */
    private Integer seatNumber;

    public OwnerElement() {
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
}
