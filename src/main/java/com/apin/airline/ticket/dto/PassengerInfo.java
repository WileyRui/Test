package com.apin.airline.ticket.dto;

import com.apin.airline.common.entity.Seat;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class PassengerInfo extends Seat {
    //乘机人类型
    private String passengerTypeStr;

    //证件类型
    private String credTypeStr;

    //性别
    private String genderStr;

    public String getPassengerTypeStr() {
        return passengerTypeStr;
    }

    public void setPassengerTypeStr(String passengerTypeStr) {
        this.passengerTypeStr = passengerTypeStr;
    }

    public String getCredTypeStr() {
        return credTypeStr;
    }

    public void setCredTypeStr(String credTypeStr) {
        this.credTypeStr = credTypeStr;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }
}
