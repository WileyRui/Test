package com.apin.airline.flight.dto;

import java.io.Serializable;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class HomeCalendarInfoDto implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 航班统计日期
     */
    private String flightDate;

    /**
     * 统计日期的星期，0-星期天   1-星期一...
     */
    private int week;

    /**
     * 已售数量
     */
    private int sold;

    /**
     * 下级剩余
     */
    private int surplus;

    /**
     * 未分配
     */
    private int unallocated;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    public int getUnallocated() {
        return unallocated;
    }

    public void setUnallocated(int unallocated) {
        this.unallocated = unallocated;
    }
}
