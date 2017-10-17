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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static javax.xml.bind.DatatypeConverter.parseDateTime;

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
     * @return 航线基础数据实体类
     */
    public Airline setAirline(Line line) {
        com.apin.airline.common.entity.Airline airline = new Airline();

        List<LineDetail> details = line.getDetails();

        airline.setId(Generator.uuid());
        airline.setHashKey(hashValue(details));
        airline.setFlightNumber(appendFlightNumber(details));
        airline.setVoyage(appendVoyage(airline.getFlightType(), details));
        airline.setInvalid(false);
        airline.setCreatorUser(line.getCreatorUser());
        airline.setCreatorUserId(line.getCreatorUserId());
        airline.setCreatedTime(new Date());
        return airline;
    }

    /**
     * 初始化航程数据
     *
     * @param details
     * @return
     */
    public List<Voyage> setVoyage(List<LineDetail> details) {
        List<Voyage> voyages = new ArrayList<>();
        details.forEach(i -> {
            Voyage voyage = new Voyage();
            voyage.setId(Generator.uuid());
            voyage.setTripIndex(i.getTripIndex());
            voyage.setAirlineId(i.getAirlineId());
            voyage.setDays(i.getDays());
            voyage.setFlightInfoId(i.getId());
            voyages.add(voyage);
        });
        return voyages;
    }

    /**
     * 初始化航线班次数据
     *
     * @param line
     * @return
     */
    public List<Flight> setFlight(Line line, List<Date> dates) {
        List<Flight> flights = new ArrayList<>();
        for (Date date : dates) {
            Flight airlineFlight = new Flight();
            if (line.getAlertRate() != null) {
                airlineFlight.setAlertThreshold((int) (line.getSeatCount() * line.getAlertRate() * 0.01));
            }

            airlineFlight.setAirlineId(line.getId());
            airlineFlight.setSeatCount(line.getSeatCount());

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
     * 拼接航班号
     *
     * @param details 航程明细
     * @return 航班号
     */
    public String appendFlightNumber(List<LineDetail> details) {
        StringBuilder flightNumber = new StringBuilder();
        details.forEach(i -> flightNumber.append(i.getFlightNo() + "-"));
        return flightNumber.substring(0, flightNumber.length() - 1);
    }

    /**
     * 拼接航程
     *
     * @param flightType    航线类型
     * @param flightDetails 航班詳情
     * @return string
     */
    public String appendVoyage(int flightType, List<LineDetail> flightDetails) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < flightDetails.size(); i++) {
            LineDetail flightDetail = flightDetails.get(i);
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
    public String hashValue(List<LineDetail> details) {
        StringBuffer hashValue = new StringBuffer();
        details.forEach(detail -> {
            String depCity = detail.getFlightDep();
            String arrCity = detail.getFlightArr();
            String flightNo = detail.getFlightNo();
            Integer days = detail.getDays();
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

        List<LineDetail> details = line.getDetails();
        if (details == null || details.size() == 0) return ReplyHelper.invalidParam("缺少航班信息");

        return ReplyHelper.success();
    }

    /**
     * 日期-周X转换
     *
     * @param strDate 日期
     * @return 周X, 周日在前则周日为0
     */
    public Integer getWeekDay(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 生成执飞日期
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param weeks 执飞规则
     * @return 执飞日期集合
     */
    public List<Date> getDates(Date start, Date end, String weeks) {
        Date date = start;
        List<Integer> dayList = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        String[] dayOfWeek = weeks.split(",");
        for (int i = 0; i < 7; i++) {
            if (dayOfWeek[i].equals("0")) continue;

            dayList.add(i);
        }

        while (!date.after(end)) {
            String cur = DateHelper.formatDate(date);
            if (!dayList.contains(getWeekDay(cur))) continue;

            dates.add(date);
        }

        return dates;
    }
}
