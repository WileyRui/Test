package com.apin.airline.ticket.dto;

/**
 * Author:huanglei
 * Description:日历查看接收类
 * Date:2017/10/11
 */
public class CalendarInfo {
    /**
     * 包机商id
     */
    private String accountId;
    /**
     * 航线id
     */
    private String airlineId;
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;

    public CalendarInfo() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
