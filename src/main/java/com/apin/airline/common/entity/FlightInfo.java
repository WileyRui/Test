package com.apin.airline.common.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @author 宣炳刚
 * @date 2017/10/11
 * @remark 供应商航线资源实体类
 */
public class FlightInfo  implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    @JsonIgnore
    private String id;

    @JsonProperty("org_timezone")
    private String orgTimezone;

    @JsonProperty("dst_timezone")
    private String dstTimezone;

    /**
     * 是否共享（0:不共享;1:共享）
     */
    @JsonProperty("ShareFlag")
    private String shareFlag;

    /**
     * 是否虚拟（0：非虚拟航班；1：虚拟航班；未来航班返回为空）
     */
    @JsonProperty("VirtualFlag")
    private String virtualFlag;

    /**
     * 航段标识（0计划航段；1因备降而产生的航段；2因返航而产生的航段）
     */
    @JsonProperty("LegFlag")
    private String legFlag;

    /**
     * 航班状态（计划、延误、取消、备降、返航、起飞、到达、备降起飞、备降取消、备降到达、返航起飞、返航取消、返航到达、提前取消）
     */
    @JsonProperty("FlightState")
    private String flightState;

    /**
     * 共享航班号（实际承运航班的航班号）
     */
    @JsonProperty("ShareFlightNo")
    private String shareFlightNo;

    /**
     * 航班号
     */
    @JsonProperty("FlightNo")
    private String flightNo;

    /**
     * 航空公司名称
     */
    @JsonProperty("FlightCompany")
    private String flightCompany;

    /**
     * 航班属性 0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知
     */
    @JsonProperty("fcategory")
    private Byte fcategory;

    /**
     * 出发城市名
     */
    @JsonProperty("FlightDep")
    private String flightDep;

    /**
     * 到达城市名
     */
    @JsonProperty("FlightArr")
    private String flightArr;

    /**
     * 出发机场名
     */
    @JsonProperty("FlightDepAirport")
    private String flightDepAirport;

    /**
     * 到达机场名
     */
    @JsonProperty("FlightArrAirport")
    private String flightArrAirport;

    /**
     * 出发地机场三字码
     */
    @JsonProperty("FlightDepcode")
    private String flightDepcode;

    /**
     * 目的地机场三字码
     */
    @JsonProperty("FlightArrcode")
    private String flightArrcode;

    /**
     * 计划起飞时间
     */
    @JsonProperty("FlightDeptimePlanDate")
    private Time flightDeptimePlanDate;

    /**
     * 计划到达时间
     */
    @JsonProperty("FlightArrtimePlanDate")
    private Time flightArrtimePlanDate;

    /**
     * 是否经停 false:不经停;true:经停
     */
    @JsonProperty("StopFlag")
    private Boolean stopFlag;

    /**
     * 飞行班次 周日开始 0,1,0,1,0,1,0代表每周135有航班飞行
     */
    @JsonIgnore
    private String flights;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public void setFlightCompany(String flightCompany) {
        this.flightCompany = flightCompany;
    }

    public Byte getFcategory() {
        return fcategory;
    }

    public void setFcategory(Byte fcategory) {
        this.fcategory = fcategory;
    }

    public String getFlightDep() {
        return flightDep;
    }

    public void setFlightDep(String flightDep) {
        this.flightDep = flightDep;
    }

    public String getFlightArr() {
        return flightArr;
    }

    public void setFlightArr(String flightArr) {
        this.flightArr = flightArr;
    }

    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport;
    }

    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport;
    }

    public String getFlightDepcode() {
        return flightDepcode;
    }

    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode;
    }

    public String getFlightArrcode() {
        return flightArrcode;
    }

    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode;
    }

    public Time getFlightDeptimePlanDate(Time time) {
        return flightDeptimePlanDate;
    }

    public void setFlightDeptimePlanDate(Time flightDeptimePlanDate) {
        this.flightDeptimePlanDate = flightDeptimePlanDate;
    }

    public Time getFlightArrtimePlanDate() {
        return flightArrtimePlanDate;
    }

    public void setFlightArrtimePlanDate(Time flightArrtimePlanDate) {
        this.flightArrtimePlanDate = flightArrtimePlanDate;
    }

    public Boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getFlights() {
        return flights;
    }

    public void setFlights(String flights) {
        this.flights = flights;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
