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
        return arrTime.toString();
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getDepTime() {
        return depTime.toString();
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

    public long getFlightTime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date arr = formatter.parse(arrTime);
        Date dep = formatter.parse(depTime);
        return arr.getTime()>dep.getTime()?arr.getTime()-dep.getTime():arr.getTime()+24*3600*100-dep.getTime();
    }

    public void setFlightTime(long flightTime) {
        this.flightTime = flightTime;
    }
}
