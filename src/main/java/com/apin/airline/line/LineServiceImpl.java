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
import java.util.List;

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

        line.setAirwayId("");
        line.setAirlineId("");

        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setAccountId(accessToken.getAccountId());
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());

        Integer count = airlineMapper.addLine(line);

        return ReplyHelper.success();
    }

    @Override
    public Reply editLine(Line line) {
        return null;
    }

    @Override
    public Reply delLine(Line line) {
        return null;
    }

    @Override
    public Reply lineList(Line line) {
        return null;
    }

    @Override
    public Reply lineInfo(Line line) {
        return null;
    }

    @Override
    public Reply upOrDown(Line line) {
        return null;
    }

    @Override
    public Reply queryFlightInfo(Line line) throws InvocationTargetException, IllegalAccessException {
        List<FlightInfo> airlineList = airlineMapper.getFlightInfos(line.g());
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
}
