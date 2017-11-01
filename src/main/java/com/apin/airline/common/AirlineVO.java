package com.apin.airline.common;

import com.apin.airline.common.entity.Airline;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.util.DateHelper;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
        List<LineDetail> details = line.getDetails();
        Integer days = 0;
        for (LineDetail i : details) {
            days += i.getDays();
        }

        Airline airline = new Airline();
        airline.setId(Generator.uuid());
        airline.setHashKey(hashValue(details));
        airline.setFlightType(line.getFlightType());
        if (line.getFlightType() != 3) {
            airline.setDepCity(details.get(0).getFlightDep());
            airline.setArrCity(details.get(0).getFlightArr());
            airline.setDays(days);
        } else { // 多程
            airline.setDepCity(details.get(0).getFlightDep());
            airline.setArrCity(details.get(details.size() - 1).getFlightArr());
            airline.setDays(days + 1);
        }
        airline.setVoyage(appendVoyage(line.getFlightType(), details));
        airline.setFlightNumber(appendFlightNumber(details));
        airline.setFlightTime(details.get(0).getFlightDeptimePlanDate());
        airline.setWeekFlights(details.get(0).getFlights());
        airline.setCreatorUser(line.getCreatorUser());
        airline.setCreatorUserId(line.getCreatorUserId());

        return airline;
    }

    /**
     * 初始化航程数据
     *
     * @param details
     * @return
     */
    public List<Voyage> setVoyage(List<LineDetail> details, String id) {
        List<Voyage> voyages = new ArrayList<>();
        details.forEach(i -> {
            Voyage voyage = new Voyage();

            voyage.setId(Generator.uuid());
            voyage.setAirlineId(id);
            voyage.setFlightInfoId(i.getId());
            voyage.setTripIndex(i.getTripIndex());
            voyage.setDays(i.getDays());

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
        dates.forEach(date -> {
            Flight flight = new Flight();

            flight.setId(Generator.uuid());
            flight.setAirlineId(line.getId());
            flight.setSellType(Byte.valueOf("0"));
            flight.setFlightDate(date);
            flight.setSeatCount(line.getSeatCount());
            flight.setAdultPrice(line.getAdultPrice());
            flight.setChildPrice(line.getChildPrice());
            if (line.getAlertRate() != null) {
                flight.setAlertThreshold((int) (line.getSeatCount() * line.getAlertRate() * 0.01));
            }
            if (line.getAlertAdvance() != null) {
                flight.setAlertDate(new Date(date.getTime() - line.getAlertAdvance() * 24 * 60 * 60 * 1000));
            }
            flight.setRecoveryDate(new Date(date.getTime() - line.getRecoveryAdvance() * 24 * 60 * 60 * 1000));
            flight.setTicketDate(new Date(date.getTime() - line.getTicketAdvance() * 24 * 60 * 60 * 1000));

            flights.add(flight);
        });
        return flights;
    }

    /**
     * 增加日志
     *
     * @param line
     * @param flag
     * @return
     */
    public Log setAirlineLog(Line line, boolean flag) {
        Log log = new Log();
        log.setId(Generator.uuid());
        log.setEventSource("CRM");
        log.setAirlineId(line.getId());
        if (flag) {
            log.setEventName("新增航线");
            log.setMessage("新增航线成功");
        } else {
            log.setEventName("编辑航线");
            log.setMessage("编辑航线成功");
        }
        log.setOperatorId(line.getCreatorUserId());
        log.setOperatorUser(line.getCreatorUser());
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
     * @param flightType 航线类型
     * @param details    航班詳情
     * @return string
     */
    public String appendVoyage(int flightType, List<LineDetail> details) {
        switch (flightType) {
            // 单程
            case 1:
                return details.get(0).getFlightDep() + "-" + details.get(0).getFlightArr();

            // 往返
            case 2:
                return details.get(0).getFlightDep() + "<->" + details.get(0).getFlightArr();

            // 多程
            case 3:
                StringBuffer buffer = new StringBuffer();
                details.forEach(i -> buffer.append(i.getFlightDep() + "-" + i.getFlightArr() + ","));
                return buffer.substring(0, buffer.length() - 1);

            default:
                return null;
        }
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
        return Generator.md5(hashValue.toString());
    }

    /**
     * 验证参数
     *
     * @param line
     * @return Reply
     */
    public Reply checkData(Line line) {
        if (line.getTicketAdvance() >= line.getRecoveryAdvance()) {
            return ReplyHelper.invalidParam("余票回收天数必须大于开票提前天数");
        }

        List<LineDetail> details = line.getDetails();
        if (details == null || details.size() == 0) {
            return ReplyHelper.invalidParam("缺少航班信息");
        }

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
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
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
            if ("0".equals(dayOfWeek[i])) {
                continue;
            }

            dayList.add(i);
        }

        while (!date.after(end)) {
            String cur = DateHelper.formatDate(date);
            if (dayList.contains(getWeekDay(cur))) {
                dates.add(date);
            }

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }

        return dates;
    }

    /**
     * 判断航线是否分配
     *
     * @param airlineId
     * @param accountId
     * @return
     */
    public boolean isAllot(String airlineId, String accountId) {
        Integer count = airlineMapper.isAllot(airlineId, accountId);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断航线是否已售
     *
     * @param airlineId
     * @param accountId
     * @return
     */
    public boolean isSaled(String airlineId, String accountId) {
        Integer count = airlineMapper.isSaled(airlineId, accountId);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 上架seat插入记录
     *
     * @param accountId
     * @param flightId
     * @param accountName
     * @param seatCount
     * @param userName
     * @param userId
     * @return
     */
    @Async
    public Integer addSeats(String accountId, String flightId, String accountName, Integer seatCount, String userName, String userId) {
        return airlineMapper.addSeats(accountId, flightId, accountName, seatCount, userName, userId);
    }
}
