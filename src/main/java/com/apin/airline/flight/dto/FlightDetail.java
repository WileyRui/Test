package com.apin.airline.flight.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/13
 */
public class FlightDetail {
    private List<String> dateList;
    private Integer seat;
    private BigDecimal price;
    private String img;

    public FlightDetail() {
    }

    public List<String> getDateList() {
        return dateList;
    }

    //    @Select("select min(b.adult_price) price,sum(b.seat_count) seat from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
//            "join msd_airline c on a.airline_id=c.id where c.voyage=#{voyage} group by c.voyage")
//    FlightDetail selectFlightDetail(String voyage);
//    @Select("select img_url from msd_city where city_name=#{deatCity}")
//    String selectCityImg(String destCity);
//    @Select("select b.flight_date from mbs_airline a join mbs_airline_flight b on a.id=b.airline_id" +
//            "join msd_airline c on a.airline_id=c.id where c.voyage=#{voyage} group by b.flight_date order by b.flight_date")
//    List<String> selectFlightDates(String voyage);
    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
