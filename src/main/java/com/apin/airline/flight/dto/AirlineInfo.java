package com.apin.airline.flight.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/16
 */
public class AirlineInfo {
    private String compName;
    private String logo;
    private String arrTime;
    private String depTime;
    private String arrAirport;
    private String depAirport;
    private long flightTime;
    private String num;
    private String depDate;
    private String arrDate;
    private String flightType;

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getArrTime() {
        return arrTime.substring(0,5).toString();
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getDepTime() {
        return depTime.substring(0,5).toString();
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    public String getFlightTime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date arr = formatter.parse(arrTime);
        Date dep = formatter.parse(depTime);
        long l = arr.getTime() > dep.getTime() ? arr.getTime() - dep.getTime() : arr.getTime() + 24 * 3600 * 1000 - dep.getTime();
        long hours = l/3600000;
        long minutes = (l - hours * (1000 * 60 * 60)) / (1000 * 60);
        return hours+":"+minutes;
    }

    public void setFlightTime(long flightTime) {
        this.flightTime = flightTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getArrDate() throws ParseException {
return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }
}
