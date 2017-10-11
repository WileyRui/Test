package com.apin.airline.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 供应商机票资源实体类
 */
public class Seat  implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父乘客ID(用于儿童和成人的关联)
     */
    private String parentId;

    /**
     * 账户ID,用于业务数据隔离
     */
    private String accountId;

    /**
     * 班次ID
     */
    private String flightId;

    /**
     * 所有者(包机商/分销商名称)
     */
    private String owner;

    /**
     * 所有者账户ID
     */
    private String ownerId;

    /**
     * 舱位等级
     */
    private String grade;

    /**
     * 票号(多票号英文逗号分隔)
     */
    private String ticket;

    /**
     * 乘客类型 0:成人;1:儿童;2:婴儿;3:青年;4:留学生;5:劳工;6:移民;7:海员;8:老年;9:探亲
     */
    private Byte passengerType;

    /**
     * 乘客姓名
     */
    private String passengerName;

    /**
     * 证件类型 0:身份证;1:护照;2:台胞证;3:港澳通行证;4:回乡证;5:海员证;6:军官证;7:士兵证;8:其他
     */
    private Byte credType;

    /**
     * 证件号
     */
    private String credNumber;

    /**
     * 性别 0:女性;1:男性
     */
    private Byte gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 国籍
     */
    private String nation;

    /**
     * 证件失效日期
     */
    private Date expireTime;

    /**
     * 证件签发地
     */
    private String issuePlace;

    /**
     * 舱位状态 0:未销售;1:已销售;2:已上传乘客信息;3:已出票
     */
    private Byte seatStatus;

    /**
     * 分配者名称(操作员用户名,初始分配者为系统)
     */
    private String divider;

    /**
     * 分配者ID,00000000000000000000000000000000代表系统分配
     */
    private String dividerId;

    /**
     * 分配时间
     */
    private Date assignedTime;

    /**
     * 是否失效 false:正常;true:失效
     */
    private Boolean invalid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Byte getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(Byte passengerType) {
        this.passengerType = passengerType;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Byte getCredType() {
        return credType;
    }

    public void setCredType(Byte credType) {
        this.credType = credType;
    }

    public String getCredNumber() {
        return credNumber;
    }

    public void setCredNumber(String credNumber) {
        this.credNumber = credNumber;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public Byte getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(Byte seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }

    public String getDividerId() {
        return dividerId;
    }

    public void setDividerId(String dividerId) {
        this.dividerId = dividerId;
    }

    public Date getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(Date assignedTime) {
        this.assignedTime = assignedTime;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }
}
