package com.apin.airline.common;

import com.apin.airline.common.entity.Airline;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.util.DateHelper;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 航线信息操作
 *
 * @author wiley
 * @date 2017年7月19日 下午2:24:39
 */
@Component
public class AirlineVO {
    @Autowired
    AirlineMapper airlineMapper;

    /**
     * 初始化航线基础数据
     *
     * @param line
     * @param flightDetails
     * @return 航线基础数据实体类
     */
    public com.apin.airline.common.entity.Airline setAirline(Line line, List<FlightInfo> flightDetails) {
        com.apin.airline.common.entity.Airline airline = new com.apin.airline.common.entity.Airline();
        airline.setId(Generator.uuid());
        airline.setInvalid(false);
        airline.setCreatorUser(line.getCreatorUser());
        airline.setCreatorUserId(line.getCreatorUserId());
        airline.setCreatedTime(new Date());
        return airline;
    }

    public com.apin.airline.common.entity.Airline setMsdAirline(com.apin.airline.common.entity.Airline msdAirline, List<FlightInfo> flightDetails, int i, int flightType, StringBuilder appendFlight) {
        FlightInfo flightDetail = flightDetails.get(i);
        if (flightType == 1) { // 单程
            msdAirline.setFlightNumber(flightDetail.getFlightNo());
            msdAirline.setVoyage(this.appendVoyage(flightType, flightDetails));
            msdAirline.setHashKey(this.hashValue(flightDetails));
        }
        if (flightType == 2) { // 往返
            String flightNo = flightDetail.getFlightNo();
            msdAirline.setHashKey(this.hashValue(flightDetails));
            if (i == flightDetails.size() - 1) {
                msdAirline.setFlightNumber(msdAirline.getFlightNumber() + "<->" + flightNo);
                msdAirline.setVoyage(this.appendVoyage(flightType, flightDetails));
            } else {
                msdAirline.setFlightNumber(flightNo);
            }
        }
        if (flightType == 3) { // 多程
            msdAirline.setHashKey(this.hashValue(flightDetails));
            if (i == flightDetails.size() - 1) {
                msdAirline.setFlightNumber(appendFlight.append(flightDetail.getFlightNo()).toString());
                msdAirline.setVoyage(this.appendVoyage(flightType, flightDetails));
            } else {
                appendFlight.append(flightDetail.getFlightNo() + ",");
            }
        }
        return msdAirline;
    }

    /**
     * 初始化航程数据
     *
     * @param flightDetails
     * @param airline
     * @return
     */
    public List<Voyage> setVoyage(List<FlightInfo> flightDetails, Airline airline) {
        List<Voyage> voyages = new ArrayList<>();
        for (int i = 0; i < flightDetails.size(); i++) {
            Voyage voyage = new Voyage();
            voyage.setId(Generator.uuid());
            voyage.setTripIndex((byte) i);
            voyage.setAirlineId(airline.getId());
            voyage.setDays(Integer.parseInt(flightDetails.get(i).getDays()));
            voyage.setFlightInfoId(airline.getId());
            voyages.add(voyage);
        }
        return voyages;
    }

    /**
     * 初始化航线班次数据
     *
     * @param line
     * @param flightDetails
     * @return
     */
    public List<Flight> setFlight(Line line, List<FlightInfo> flightDetails) {
        List<Flight> flights = new ArrayList<>();
        String[] datesByWeek = line.getWeekFlights().split(",");
        for (String flightDate : datesByWeek) {
            Flight airlineFlight = new Flight();
            if (line.getAlertRate() != null) {
                airlineFlight.setAlertThreshold((int) (line.getSeatCount() * line.getAlertRate() * 0.01));
            }

            airlineFlight.setAirlineId(line.getId());
            airlineFlight.setSeatCount(line.getSeatCount());
            Date date = DateHelper.parseDate(flightDate);
            airlineFlight.setId(Generator.uuid());
            airlineFlight.setFlightDate(date);
            airlineFlight.setAdultPrice(line.getAdultPrice());
            airlineFlight.setChildPrice(line.getChildPrice());
            if (line.getAlertAdvance() != null) {
                airlineFlight.setAlertDate(new Date(date.getTime() - line.getAlertAdvance() * 24 * 60 * 60 * 1000));
            }
            airlineFlight.setTicketDate(new Date(date.getTime() - line.getTicketAdvance() * 24 * 60 * 60 * 1000));
            airlineFlight.setRecoveryDate(new Date(date.getTime() - line.getRecoveryAdvance() * 24 * 60 * 60 * 1000));
            flights.add(airlineFlight);
        }
        return flights;
    }

    /**
     * 增加日志
     *
     * @param Line
     * @param id
     * @param flag
     * @return
     */
    public Log setAirlineLog(Line Line, String id, boolean flag) {
        Log log = new Log();
        log.setId(Generator.uuid());
        log.setEventSource("CRM");
        log.setAirlineId(id);
        if (flag) {
            log.setEventName("新增航线");
            log.setMessage("新增航线成功");
        } else {
            log.setEventName("编辑航线");
            log.setMessage("编辑航线成功");
        }
        log.setOperatorId(Line.getCreatorUserId());
        log.setOperatorUser(Line.getCreatorUser());
        return log;
    }

    /**
     * 拼接航程
     *
     * @param flightType    航线类型
     * @param flightDetails 航班詳情
     * @return string
     */
    public String appendVoyage(int flightType, List<FlightInfo> flightDetails) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < flightDetails.size(); i++) {
            FlightInfo flightDetail = flightDetails.get(i);
            String depAirportCode = flightDetail.getFlightDepcode();
            String depCity = airlineMapper.findCityNameByIataCode(depAirportCode);
            String arrAirportCode = flightDetail.getFlightArrcode();
            String arrCity = airlineMapper.findCityNameByIataCode(arrAirportCode);
            switch (flightType) {
                case 1:
                    buffer.append(depCity + "-").append(arrCity);
                    break;
                case 2:
                    if (i == 0) {
                        buffer.append(depCity + "<->").append(arrCity);
                    }
                    break;
                case 3:
                    if (i == flightDetails.size() - 1) {
                        buffer.append(depCity + "-").append(arrCity);
                    } else {
                        buffer.append(depCity + "-").append(arrCity + ",");
                    }
                    break;
                default:
                    break;
            }
        }
        return buffer.toString();
    }

    /**
     * 生成hashValue
     *
     * @param details
     * @return hashValue
     */
    public String hashValue(List<FlightInfo> details) {
        StringBuffer hashValue = new StringBuffer();
        details.forEach(detail -> {
            String depCity = detail.getFlightDep();
            String arrCity = detail.getFlightArr();
            String flightNo = detail.getFlightNo();
            String days = detail.getDays();
            hashValue.append(flightNo + depCity + arrCity + days);
        });
        return hashValue.toString();
    }

    /**
     * 验证参数
     *
     * @param line
     * @return Reply
     */
    public Reply checkData(Line line) {
        if (line.getTicketAdvance() >= line.getRecoveryAdvance()) return ReplyHelper.invalidParam("余票回收天数必须大于开票提前天数");

        List<FlightInfo> details = line.getDetails();
        if (details == null || details.size() == 0) return ReplyHelper.invalidParam("缺少航班信息");

        return ReplyHelper.success();
    }
}
