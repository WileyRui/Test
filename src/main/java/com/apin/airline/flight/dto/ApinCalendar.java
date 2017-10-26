package com.apin.airline.flight.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Info Created by Administrator on 2017/9/4.
 * @Remark:
 */
public class ApinCalendar implements Serializable{
    private static final long serialVersionUID = -1L;
    /**
     * 查询周期的第一天为周几
     * 0-6 分别为 周日 ~ 周六
     */
    private Integer firstWeekDay;

    /**
     * 日历元素列表
     */
    private List<ApinCalendarElement> contentList;

    public ApinCalendar() {
    }

    public Integer getFirstWeekDay() {
        return firstWeekDay;
    }

    public void setFirstWeekDay(Integer firstWeekDay) {
        this.firstWeekDay = firstWeekDay;
    }

    public List<ApinCalendarElement> getContentList() {
        return contentList;
    }

    public void setContentList(List<ApinCalendarElement> contentList) {
        this.contentList = contentList;
    }
}
