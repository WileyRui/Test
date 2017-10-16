package com.apin.airline.line;

import com.apin.airline.common.VariFlightService;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.line.dto.AirlineVO;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.AccessToken;
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
    public Reply addLine(String token, Line line) {

        // 校验数据

        // 处理数据
        line.setId(Generator.uuid());
        line.setAirwayId(airlineMapper.getAirwayIdByFlightNo(line.getDetails().));

        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setAccountId(accessToken.getAccountId());
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());

        // 持久化数据
        Integer count = airlineMapper.addLine(line);
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
        airlineVO.setLine(line,token);
        Integer row = airlineMapper.deleteLine(line.getId());
        if (row <= 0){
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply lineList(Line line) {
        return null;
    }

    @Override
    public Reply lineInfo(String token,Line line) {
        airlineVO.setLine(line,token);
        Line line1 = airlineMapper.getLine(line.getId());
        Airline airline = airlineMapper.getAirlineById(line1.getAirlineId());
        List<AirlineDetail> voyages = airlineMapper.getVoyages(airline.getId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("line",line);
        resultMap.put("airline",airline);
        resultMap.put("voyages",voyages);
        return ReplyHelper.success(resultMap);
    }

    @Override
    @Transactional
    public Reply upOrDown(String token,Line line) {
        airlineVO.setLine(line,token);
        Integer row = airlineMapper.updateAirLineStatus(line.getId(),line.getAirlineStatus());
        if (row<=0){
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply queryFlightInfo(Line line) throws InvocationTargetException, IllegalAccessException {
        List<FlightInfo> airlineList = airlineMapper.getFlightInfos(line.getFlightNo());
        if (airlineList.size() > 0) {
            return ReplyHelper.success(airlineList);
        }
        List<FlightInfo> flightInfoList = variFlight.initVariFlightData(line.getFlightNo(), line.getBeginDate());
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
    public Reply updateFlightInfo(Line line) throws InvocationTargetException, IllegalAccessException { //需求待确认
        Integer row = airlineMapper.deleteFlightInfo(line.getFlightNo());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        List<FlightInfo> flightInfoList = variFlight.initVariFlightData(line.getFlightNo(), line.getBeginDate());
        return ReplyHelper.success(flightInfoList, "航班信息更新成功");
    }

    @Override
    public Reply newLineInfo() {
        return null;
    }
}
