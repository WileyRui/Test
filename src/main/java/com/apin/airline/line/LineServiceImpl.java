package com.apin.airline.line;

import com.apin.airline.common.VariFlightService;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.line.dto.AirlineVO;
import com.apin.airline.line.dto.FlightDetail;
import com.apin.airline.line.dto.LineBo;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Reply addLine(String token, LineBo lineBo) {
        airlineVO.setLineBo(lineBo, token);
        StringBuilder appendFlight = new StringBuilder();
        airlineVO.checkData(lineBo);
        List<FlightDetail> msdAirlineList = lineBo.getMsdAirlineInfoList();
        Line line = airlineVO.setLine(lineBo, msdAirlineList);
        Airline airline = airlineVO.setAirline(lineBo, msdAirlineList);
        line.setAirlineId(airline.getId());
        Voyage airlineVoyage = new Voyage();
        airlineVoyage.setAirlineId(airline.getId());
        for (int i = 0; i < msdAirlineList.size(); i++) {
            FlightDetail flightDetail = msdAirlineList.get(i);
/*            FlightInfo flightInfo = airlineMapper.findByflightNoAndIatacode(
                    msdAirlineList.get(i).getFlightNo(), msdAirlineList.get(i).getDepAirportCode(),
                    msdAirlineList.get(i).getArrAirportCode());*/
            if (i == 0) {
//                airline.setFlightTime(flightInfo.getFlightDeptimePlanDate());
//                line.setAirwayId(airlineMapper.getAirwayIdByFlightNo(flightInfo.getFlightNo()));
            }
            airline = airlineVO.setMsdAirline(airline, msdAirlineList, i, lineBo.getFlightType(), appendFlight);
        }
        if (airlineVO.LineRepeat(airline.getHashKey())) {
            return ReplyHelper.fail("航线已存在");
        }
        airlineMapper.addLine(line);
        airlineMapper.addAirline(airline);
        List<Flight> flights = airlineVO.setFlight(line, lineBo, msdAirlineList);
        airlineMapper.addLineFlights(flights);
        List<Voyage> voyages = airlineVO.setVoyage(msdAirlineList, airline);
        airlineMapper.addVoyages(voyages);
        Log log = airlineVO.setAirlineLog(lineBo, line.getId(), true);
        airlineMapper.addLog(log);
        return ReplyHelper.success();
    }

    @Override
    public Reply editLine(String token, LineBo lineBo) {
        return null;
    }

    @Override
    @Transactional
    public Reply delLine(String token, LineBo lineBo) {
        airlineVO.setLineBo(lineBo,token);
        Integer row = airlineMapper.deleteLine(lineBo.getId());
        if (row <= 0){
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply lineList(LineBo lineBo) {
        return null;
    }

    @Override
    public Reply lineInfo(String token,LineBo lineBo) {
        airlineVO.setLineBo(lineBo,token);
        Line line = airlineMapper.getLine(lineBo.getId());
        Airline airline = airlineMapper.getAirlineById(line.getAirlineId());
        List<AirlineDetail> voyages = airlineMapper.getVoyages(airline.getId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("line",line);
        resultMap.put("airline",airline);
        resultMap.put("voyages",voyages);
        return ReplyHelper.success(resultMap);
    }

    @Override
    @Transactional
    public Reply upOrDown(String token,LineBo lineBo) {
        airlineVO.setLineBo(lineBo,token);
        Integer row = airlineMapper.updateAirLineStatus(lineBo.getId(),lineBo.getAirlineStatus());
        if (row<=0){
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply queryFlightInfo(LineBo lineBo) throws InvocationTargetException, IllegalAccessException {
        List<FlightInfo> airlineList = airlineMapper.getFlightInfos(lineBo.getFlightNo());
        if (airlineList.size() > 0) {
            return ReplyHelper.success(airlineList);
        }
        List<FlightInfo> flightInfoList = variFlight.initVariFlightData(lineBo.getFlightNo(), lineBo.getBeginDate());
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
    public Reply updateFlightInfo(LineBo lineBo) throws InvocationTargetException, IllegalAccessException { //需求待确认
        Integer row = airlineMapper.deleteFlightInfo(lineBo.getFlightNo());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        List<FlightInfo> flightInfoList = variFlight.initVariFlightData(lineBo.getFlightNo(), lineBo.getBeginDate());
        return ReplyHelper.success(flightInfoList, "航班信息更新成功");
    }
}
