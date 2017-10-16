package com.apin.airline.line;

import com.apin.airline.common.VariFlightService;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.AirlineVO;
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

        // 根据城市、航班号和行程天数计算摘要
        String key = airlineVO.hashValue(line.getDetails());

        // 根据摘要字符串查询航线基础数据ID,如航线基础数据不存在,则生成相应的航线基础数据并持久化到数据库
        String airLineId = airlineMapper.getExistedAirline(key);
        if (airLineId == null){
            airLineId = Generator.uuid();
        }

        // 处理航线资源数据
        line.setId(Generator.uuid());
        line.setAirwayId(airlineMapper.getAirwayIdByFlightNo(line.getDetails().get(0).getFlightNo()));
        line.setAirlineId(airLineId);

        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setAccountId(accessToken.getAccountId());
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());

        // 生成航班资源数据集合

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

        Integer row = airlineMapper.updateAirLineStatus(line.getId(),line.getAirlineStatus());
        if (row<=0){
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
        return null;
    }
}
