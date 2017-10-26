package com.apin.airline.ticket.dto;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class PassengerParam {
    /**
     *  包机商ID
     */
    String accountId;
    /**
     *  航线Id 航线列表中获取
     */
    String airlineId;
    /**
     *  选中的日期
     */
    String currentDate;
    /**
     *  分销商名称 1、为null时查询所有分销商的乘机人信息 2、不为null时查询某个分销商的乘机人信息
     */
    String owner;
    /**
     *  第几页
     */
    Integer pageNumber;
    /**
     *  每页大小
     */
    Integer pageSize;

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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}