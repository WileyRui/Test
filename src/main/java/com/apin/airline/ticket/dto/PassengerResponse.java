package com.apin.airline.ticket.dto;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class PassengerResponse {

    /**
     *  所属分销商
     */
    String owner;
    /**
     *  乘客姓名
     */
    String passengerName;
    /**
     *  证件类型
     */
    String credTypeStr;
    /**
     *  证件号码
     */
    String credNumber;
    /**
     *  乘客类型
     */
    String passengerTypeStr;
    /**
     *  出生日期
     */
    String birthday;
    /**
     *  性别
     */
    String genderStr;
    /**
     *  国籍
     */
    String nation;
    /**
     *  失效日期
     */
    String expireTime;
    /**
     *  签发地
     */
    String issuePlace;


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getCredTypeStr() {
        return credTypeStr;
    }

    public void setCredTypeStr(String credTypeStr) {
        this.credTypeStr = credTypeStr;
    }

    public String getCredNumber() {
        return credNumber;
    }

    public void setCredNumber(String credNumber) {
        this.credNumber = credNumber;
    }

    public String getPassengerTypeStr() {
        return passengerTypeStr;
    }

    public void setPassengerTypeStr(String passengerTypeStr) {
        this.passengerTypeStr = passengerTypeStr;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }
}
