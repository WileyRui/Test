package com.apin.airline.flight.dto;

import com.apin.util.DateHelper;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class HomeAirlineQueryRequest implements Serializable {
    private static final long serialVersionUID = -1L;


    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 查询日期，yyyy-MM-dd 格式
     */
    private String queryDate;

    /**
     * 数据处理业务 需要，前端界面不需要
     * 截取week_flights字段，判断该天是否有班期
     * 比如：0,1,0,1,0,1,0
     * 截取：星期一 weekday*2+1   weekday 从0开始
     */
    private int weekFlightsPos;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
        int week = DateHelper.getWeek(queryDate);
        if(week != -1) {
            this.weekFlightsPos = week*2 + 1;
        }
    }

    public int getWeekFlightsPos() {
        return weekFlightsPos;
    }

    public void setWeekFlightsPos(int weekFlightsPos) {
        this.weekFlightsPos = weekFlightsPos;
    }
}
