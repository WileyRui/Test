package com.apin.airline.flight.dto;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class HomeCalendarInfoQueryRequest implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 查询的日期开始日期，格式：2017-08-01
     */
    private String startDate;

    /**
     * 查询的日期结束日期，格式：2017-08-31
     */
    private String endDate;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
