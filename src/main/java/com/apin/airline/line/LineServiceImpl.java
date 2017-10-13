package com.apin.airline.line;

import com.apin.airline.base.VariFlight;
import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.common.entity.Line;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.line.dto.LineBo;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
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
    VariFlight variFlight;
    @Autowired
    AirlineMapper airlineMapper;

    @Override
    public Reply addLine(LineBo lineBo) {
        StringBuilder appendFlight = new StringBuilder();
        Line line = new Line();
/*        String newAirlineNo = airlineMapper.findNew();
        line.setId(Generator.uuid());
            if (StringUtils.isBlank(newAirlineNo)) {
                line.setAirlineNo("1");
            } else {
                line.setAirlineNo(String.valueOf(Integer.parseInt(newAirlineNo) + 1));
            }*/
        return null;
    }

    @Override
    public Reply editLine(LineBo lineBo) {
        return null;
    }

    @Override
    public Reply delLine(LineBo lineBo) {
        return null;
    }

    @Override
    public Reply lineList(LineBo lineBo) {
        return null;
    }

    @Override
    public Reply lineInfo(LineBo lineBo) {
        return null;
    }

    @Override
    public Reply upOrDown(LineBo lineBo) {
        return null;
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
