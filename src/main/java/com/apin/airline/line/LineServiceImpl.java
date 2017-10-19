package com.apin.airline.line;

import com.apin.airline.common.AirlineVO;
import com.apin.airline.common.VariFlightService;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.line.dto.NewLine;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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
        line.setId(Generator.uuid());
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
            List<Voyage> voyages = airlineVO.setVoyage(line.getDetails(), airline.getId());
            Integer count = airlineMapper.addAirline(airline);
            count += airlineMapper.addVoyages(voyages);
            if (count <= 0) return ReplyHelper.error();

        } else {
            // 校验数据是否重复
            List<Date> existedDates = airlineMapper.getExistedflightDate(accessToken.getAccountId(), airLineId);
            existedDates.retainAll(dates);
            if (existedDates.size() > 0) return ReplyHelper.invalidParam("重复的航班");

            line.setAirlineId(airLineId);
        }

        // 处理航线资源数据
        line.setAirwayId(airlineMapper.getAirwayIdByFlightNo(line.getDetails().get(0).getFlightNo()));

        line.setAccountId(accessToken.getAccountId());
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());

        // 生成航班资源数据集合
        List<Flight> flights = airlineVO.setFlight(line, dates);
        Log log = new Log();
        log.setId(Generator.uuid());
        log.setAirlineId(line.getId());
        log.setMessage("新增航线资源");
        log.setOperatorUser(accessToken.getUserName());
        log.setOperatorId(accessToken.getUserId());

        // 持久化数据
        Integer count = airlineMapper.addLine(line);
        count += airlineMapper.addLineFlights(flights);
        count += airlineMapper.addLog(log);
        if (count <= 0) return ReplyHelper.error();

        return ReplyHelper.success();
    }

    @Transactional
    @Override
    public Reply editLine(String token, Line line) {
        boolean flag = airlineVO.isAllot(line.getId(), line.getAccountId());
        if (flag) {
            return ReplyHelper.fail("航线已分配，无法编辑");
        }
        flag = airlineVO.isSaled(line.getId(), line.getAccountId());
        if (flag) {
            return ReplyHelper.fail("航线已售，无法编辑");
        }
        String airlineId = airlineMapper.getLine(line.getId()).getAirlineId();
        int count = 0;
        count += airlineMapper.deleteVoyage(airlineId);
        count += airlineMapper.deleteAirline(airlineId);
        count += airlineMapper.deleteFlight(line.getId());
        count += airlineMapper.deleteLine(line.getId());
        //日志
        count += airlineMapper.addLog(airlineVO.setAirlineLog(line, false));
        if (count <= 0) {
            return ReplyHelper.error();
        }
        return addLine(token, line);
    }

    @Override
    @Transactional
    public Reply delLine(String token, Line line) {
        AccessToken accessToken = JsonUtils.toAccessToken(token);
        String accountId = accessToken.getAccountId();
        boolean flag = airlineVO.isAllot(line.getId(), accountId);
        if (flag) {
            return ReplyHelper.fail("航线已分配，无法删除");
        }
        flag = airlineVO.isSaled(line.getId(), accountId);
        if (flag) {
            return ReplyHelper.fail("航线已售，无法删除");
        }
        Integer row = airlineMapper.deleteLine(line.getId());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply lineList(Line line, String token) {
        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setAccountId(accessToken.getAccountId());
        line.setPageIndex((line.getPageIndex() - 1) * line.getPageSize());
        List<Line> lines = airlineMapper.queryLineList(line);
        return ReplyHelper.success(lines);
    }

    @Override
    public Reply lineInfo(String token, Line line) {

        Line line1 = airlineMapper.getLine(line.getId());
        Airline airline = airlineMapper.getAirlineById(line1.getAirlineId());
        List<LineDetail> voyages = airlineMapper.getVoyages(airline.getId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("line", line);
        resultMap.put("airline", airline);
        resultMap.put("voyages", voyages);
        return ReplyHelper.success(resultMap);
    }

    @Override
    @Transactional
    public Reply upOrDown(String token, Line line) {
        String airlineId = line.getId();
        Byte status = line.getAirlineStatus();
        line = airlineMapper.getLine(airlineId);
        Integer row = airlineMapper.updateAirLineStatus(airlineId, status);
        if (row <= 0) {
            return ReplyHelper.error();
        }
        String accountId = line.getAccountId();
        String accountName = line.getSupplierName();
        String userId = JsonUtils.toAccessToken(token).getUserId();
        String userName = JsonUtils.toAccessToken(token).getUserName();
        List<Flight> flightList = airlineMapper.getFlights(airlineId);
        if(1==status&&airlineMapper.ifOnSale(airlineId)==null){
            flightList.stream().forEach(f->{
                airlineVO.addSeats(accountId,f.getId(),accountName,f.getSeatCount(),userName,userId);
            });
        }else if(2==status){
            airlineMapper.deleteSeats(airlineId);
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply queryFlightInfo(LineDetail info) throws InvocationTargetException, IllegalAccessException {
        List<LineDetail> airlineList = airlineMapper.getFlightInfos(info.getFlightNo());
        if (airlineList.size() > 0) {
            return ReplyHelper.success(airlineList);
        }
        List<LineDetail> lineDetails = variFlight.initVariFlightData(info.getFlightNo(), info.getBeginDate());
        if (lineDetails.size() == 0) {
            return ReplyHelper.fail("航班信息不存在，手工录入");
        }
        return ReplyHelper.success(lineDetails);
    }

    @Transactional
    @Override
    public Reply addFlightInfo(LineDetail info) {   //需求待确认
        info.setId(Generator.uuid());
        List<LineDetail> lineDetails = new ArrayList<>();
        lineDetails.add(info);
        airlineMapper.addFlightInfo(lineDetails);
        return ReplyHelper.success();
    }

    @Transactional
    @Override
    public Reply updateFlightInfo(LineDetail info) throws InvocationTargetException, IllegalAccessException { //需求待确认
        Integer row = airlineMapper.deleteFlightInfo(info.getFlightNo());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        List<LineDetail> lineDetails = variFlight.initVariFlightData(info.getFlightNo(), info.getBeginDate());
        return ReplyHelper.success(lineDetails, "航班信息更新成功");
    }

    @Override
    public Reply newLineInfo(Line line) {
        Map<String, Object> resultMap = new HashMap<>();
        line.setPageIndex(line.getPageIndex() * 25);
        List<NewLine> newLines = airlineMapper.newLineData(line);
        if (newLines.size() < 25) {
            resultMap.put("isLastPage", true);
        } else {
            resultMap.put("isLastPage", false);
        }
        resultMap.put("lines", newLines);
        return ReplyHelper.success(resultMap);
    }
}
