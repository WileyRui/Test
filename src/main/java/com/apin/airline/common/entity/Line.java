package com.apin.airline.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 供应商航线资源实体类
 */
public class Line implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 账户ID,用于业务数据隔离
     */
    private String accountId;

    /**
     * 资源编号
     */
    private String airlineNo;

    /**
     * 包机商/供应商名称
     */
    private String supplierName;

    /**
     * 航空公司ID
     */
    private String airwayId;

    /**
     * 航线基础数据ID
     */
    private String airlineId;

    /**
     * 航程类型 0:未定义;1:单程;2:往返;3:多程
     */
    private Byte flightType;

    /**
     * 资源类型 0:线下资源;1:包机商资源;2:一般供应商资源;3:软切资源;4:硬切资源
     */
    private Byte resType;

    /**
     * 舱位类型 0:未定义;1:系列团;2:余位
     */
    private Byte seatType;

    /**
     * 销售开始日期(出发日期)
     */
    private Date departureStart;

    /**
     * 销售截止日期(出发日期)
     */
    private Date departureEnd;

    /**
     * 每周飞行班次 周日开始 0,1,0,1,0,1,0代表每周135有航班飞行
     */
    private String weekFlights;

    /**
     * 舱位数量
     */
    private Integer seatCount;

    /**
     * 订金金额
     */
    private BigDecimal depositAmount;

    /**
     * 成人票价(基准价)
     */
    private BigDecimal adultPrice;

    /**
     * 儿童票价(基准价)
     */
    private BigDecimal childPrice;

    /**
     * 尾款提前天数
     */
    private Integer payAdvance;

    /**
     * 开票提前天数(相对执飞日)
     */
    private Integer ticketAdvance;

    /**
     * 强制回收提前天数(相对开票日)
     */
    private Integer recoveryAdvance;

    /**
     * 免费行李件数
     */
    private Integer freeBag;

    /**
     * 每件限重
     */
    private Integer weightLimit;

    /**
     * 预警提前天数
     */
    private Integer alertAdvance;

    /**
     * 预警阈值(百分数)
     */
    private Integer alertRate;

    /**
     * 是否可退票 false:不可退票;true:可退票
     */
    private Boolean canReturn;

    /**
     * 是否可改期 false:不可改期;true:可改期
     */
    private Boolean canChange;

    /**
     * 是否可签转 false:不可签转;true:可签转
     */
    private Boolean canSign;

    /**
     * 是否在售 0:待上架;1:上架;2:下架;3:过期
     */
    private Byte airlineStatus;

    /**
     * 是否失效 false:正常;true:失效
     */
    private Boolean invalid;

    /**
     * 负责人姓名
     */
    private String manager;

    /**
     * 航班负责人ID
     */
    private String managerId;

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

    /**
     * 航程明细
     */
    private List<FlightDetail> details;

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

    public String getAirlineNo() {
        return airlineNo;
    }

    public void setAirlineNo(String airlineNo) {
        this.airlineNo = airlineNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAirwayId() {
        return airwayId;
    }

    public void setAirwayId(String airwayId) {
        this.airwayId = airwayId;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public Byte getFlightType() {
        return flightType;
    }

    public void setFlightType(Byte flightType) {
        this.flightType = flightType;
    }

    public Byte getResType() {
        return resType;
    }

    public void setResType(Byte resType) {
        this.resType = resType;
    }

    public Byte getSeatType() {
        return seatType;
    }

    public void setSeatType(Byte seatType) {
        this.seatType = seatType;
    }

    public Date getDepartureStart() {
        return departureStart;
    }

    public void setDepartureStart(Date departureStart) {
        this.departureStart = departureStart;
    }

    public Date getDepartureEnd() {
        return departureEnd;
    }

    public void setDepartureEnd(Date departureEnd) {
        this.departureEnd = departureEnd;
    }

    public String getWeekFlights() {
        return weekFlights;
    }

    public void setWeekFlights(String weekFlights) {
        this.weekFlights = weekFlights;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(BigDecimal adultPrice) {
        this.adultPrice = adultPrice;
    }

    public BigDecimal getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(BigDecimal childPrice) {
        this.childPrice = childPrice;
    }

    public Integer getPayAdvance() {
        return payAdvance;
    }

    public void setPayAdvance(Integer payAdvance) {
        this.payAdvance = payAdvance;
    }

    public Integer getTicketAdvance() {
        return ticketAdvance;
    }

    public void setTicketAdvance(Integer ticketAdvance) {
        this.ticketAdvance = ticketAdvance;
    }

    public Integer getRecoveryAdvance() {
        return recoveryAdvance;
    }

    public void setRecoveryAdvance(Integer recoveryAdvance) {
        this.recoveryAdvance = recoveryAdvance;
    }

    public Integer getFreeBag() {
        return freeBag;
    }

    public void setFreeBag(Integer freeBag) {
        this.freeBag = freeBag;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getAlertAdvance() {
        return alertAdvance;
    }

    public void setAlertAdvance(Integer alertAdvance) {
        this.alertAdvance = alertAdvance;
    }

    public Integer getAlertRate() {
        return alertRate;
    }

    public void setAlertRate(Integer alertRate) {
        this.alertRate = alertRate;
    }

    public Boolean getCanReturn() {
        return canReturn;
    }

    public void setCanReturn(Boolean canReturn) {
        this.canReturn = canReturn;
    }

    public Boolean getCanChange() {
        return canChange;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean getCanSign() {
        return canSign;
    }

    public void setCanSign(Boolean canSign) {
        this.canSign = canSign;
    }

    public Byte getAirlineStatus() {
        return airlineStatus;
    }

    public void setAirlineStatus(Byte airlineStatus) {
        this.airlineStatus = airlineStatus;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
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

    public List<FlightDetail> getDetails() {
        return details;
    }

    public void setDetails(List<FlightDetail> details) {
        this.details = details;
    }
}
