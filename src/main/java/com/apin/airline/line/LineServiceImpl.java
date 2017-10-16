package com.apin.airline.line;

import com.apin.airline.common.AirlineVO;
import com.apin.airline.common.VariFlightService;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author wiley
 * @date 2017/10/12
 * @remark
 */
@Service
public class LineServiceImpl implements LineService {
    @Autowired
    VariFlightService variFlight;
    @Autowired
    AirlineMapper airlineMapper;
    @Autowired
    AirlineVO airlineVO;

    @Override
    @Transactional
    public Reply addLine(String token, Line line) {
        Reply result = airlineVO.checkData(line);
        if (!result.getSuccess()) return result;

        AccessToken accessToken = JsonUtils.toAccessToken(token);
        List<Date> dates = airlineVO.getDates(line.getDepartureStart(), line.getDepartureEnd(), line.getWeekFlights());

        // 根据城市、航班号和行程天数计算摘要并查询航线基础数据ID
        String key = airlineVO.hashValue(line.getDetails());
        String airLineId = airlineMapper.getExistedAirline(key);

        // 如航线基础数据不存在,则生成相应的航线基础数据并持久化到数据库
        if (airLineId == null) {
            line.setAirlineId(Generator.uuid());

            Airline airline = airlineVO.setAirline(line);
            List<Voyage> voyages = airlineVO.setVoyage(line.getDetails());
            Integer count = airlineMapper.addAirline(airline);
            count += airlineMapper.addVoyages(voyages);
            if (count <= 0) return ReplyHelper.error();

        } else {
            // 校验数据是否重复
            List<Date> existedDates = airlineMapper.getExistedflightDate(accessToken.getAccountId(), airLineId);
            if (dates.retainAll(existedDates)) return ReplyHelper.invalidParam("重复的航班");

            line.setAirlineId(airLineId);
        }

        // 处理航线资源数据
        line.setId(Generator.uuid());
        line.setAirwayId(airlineMapper.getAirwayIdByFlightNo(line.getDetails().get(0).getFlightNo()));

        line.setAccountId(accessToken.getAccountId());
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());

        // 生成航班资源数据集合
        List<Flight> flights = airlineVO.setFlight(line, dates);

        // 持久化数据
        Integer count = airlineMapper.addLine(line);
        count += airlineMapper.addLineFlights(flights);
        if (count <= 0) return ReplyHelper.error();

        return ReplyHelper.success();
    }

    @Override
    public Reply editLine(String token, Line line) {
        return null;
    }

    @Override
    @Transactional
    public Reply delLine(String token, Line line) {

        Integer row = airlineMapper.deleteLine(line.getId());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply lineList(Line line) {
        return null;
    }

    @Override
    public Reply lineInfo(String token, Line line) {

        Line line1 = airlineMapper.getLine(line.getId());
        Airline airline = airlineMapper.getAirlineById(line1.getAirlineId());
        List<FlightInfo> voyages = airlineMapper.getVoyages(airline.getId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("line", line);
        resultMap.put("airline", airline);
        resultMap.put("voyages", voyages);
        return ReplyHelper.success(resultMap);
    }

    @Override
    @Transactional
    public Reply upOrDown(String token, Line line) {

        Integer row = airlineMapper.updateAirLineStatus(line.getId(), line.getAirlineStatus());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply queryFlightInfo(FlightInfo info) throws InvocationTargetException, IllegalAccessException {
        List<FlightInfo> airlineList = airlineMapper.getFlightInfos(info.getFlightNo());
        if (airlineList.size() > 0) {
            return ReplyHelper.success(airlineList);
        }
        List<FlightInfo> flightInfoList = variFlight.initVariFlightData(info.getFlightNo(), info.getBeginDate());
        if (flightInfoList.size() == 0) {
            return ReplyHelper.fail("航班信息不存在，手工录入");
        }
        return ReplyHelper.success(flightInfoList);
    }

    @Transactional
    @Override
    public Reply addFlightInfo(FlightInfo info) {   //需求待确认
        info.setId(Generator.uuid());
        List<FlightInfo> flightInfoList = new ArrayList<>();
        flightInfoList.add(info);
        airlineMapper.addFlightInfo(flightInfoList);
        return ReplyHelper.success();
    }

    @Transactional
    @Override
    public Reply updateFlightInfo(FlightInfo info) throws InvocationTargetException, IllegalAccessException { //需求待确认
        Integer row = airlineMapper.deleteFlightInfo(info.getFlightNo());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        List<FlightInfo> flightInfoList = variFlight.initVariFlightData(info.getFlightNo(), info.getBeginDate());
        return ReplyHelper.success(flightInfoList, "航班信息更新成功");
    }

    @Override
    public Reply newLineInfo() {
        return ReplyHelper.success(airlineMapper.newLineData());
    }
}
