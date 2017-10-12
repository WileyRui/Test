package com.apin.airline.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 供应商机票分配/回收记录实体类
 */
public class AssignRecord implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 包机商ID
     */
    private String accountId;

    /**
     * 分销商ID
     */
    private String dealerId;

    /**
     * 航班ID
     */
    private String flightId;

    /**
     * 类型 0:未定义;1:分配;2:强制回收;3:手动回收
     */
    private Byte type;

    /**
     * 数量 回收为负值,代表分销商的可售舱位减少
     */
    private Integer count;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creatorUser;

    /**
     * 创建用户ID
     */
    private String creatorUserId;

    /**
     * 创建时间
     */
    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(String creatorUser) {
        this.creatorUser = creatorUser;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
