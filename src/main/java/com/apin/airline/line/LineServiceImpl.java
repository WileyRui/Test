package com.apin.airline.line;

import com.alibaba.fastjson.JSONObject;
import com.apin.airline.common.AirlineVO;
import com.apin.airline.common.VariFlightClient;
import com.apin.airline.common.entity.*;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.LogMapper;
import com.apin.airline.line.dto.NewLine;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author 温睿
 * @date 2017/10/12
 * @remark
 */
@Service
public class LineServiceImpl implements LineService {
    @Autowired
    VariFlightClient variFlight;
    @Autowired
    AirlineMapper airlineMapper;
    @Autowired
    LogMapper logMapper;
    @Autowired
    AirlineVO airlineVO;

    @Override
    @Transactional
    public Reply addLine(String token, Line line) {
        line.setId(Generator.uuid());
        Reply result = airlineVO.checkData(line);
        if (!result.getSuccess()) {
            return result;
        }

        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setAccountId(accessToken.getAccountId());
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());
        List<Date> dates = airlineVO.getDates(line.getDepartureStart(), line.getDepartureEnd(), line.getDetails().get(0).getFlights());

        // 根据城市、航班号和行程天数计算摘要并查询航线基础数据ID
        String key = airlineVO.hashValue(line.getDetails());
        String airLineId = airlineMapper.getExistedAirline(key);

        // 如航线基础数据不存在,则生成相应的航线基础数据并持久化到数据库
        if (airLineId == null) {
            Airline airline = airlineVO.setAirline(line);
            line.setAirlineId(airline.getId());
            List<Voyage> voyages = airlineVO.setVoyage(line.getDetails(), airline.getId());
            Integer count = airlineMapper.addAirline(airline);
            count += airlineMapper.addVoyages(voyages);
            if (count <= 0) {
                return ReplyHelper.error();
            }
        } else {
            // 校验数据是否重复
            List<Date> existedDates = airlineMapper.getExistedflightDate(accessToken.getAccountId(), airLineId);
            existedDates.retainAll(dates);
            if (existedDates.size() > 0) {
                return ReplyHelper.invalidParam("重复的航班");
            }
            line.setAirlineId(airLineId);
        }

        // 处理航线资源数据
        line.setAirwayId(airlineMapper.getAirwayIdByFlightNo(line.getDetails().get(0).getFlightNo()));

        // 生成航班资源数据集合
        List<Flight> flights = airlineVO.setFlight(line, dates);

        // 持久化数据
        Integer count = airlineMapper.addLine(line);
        count += airlineMapper.addLineFlights(flights);
        Log log = airlineVO.setAirlineLog(line, true);
        count += logMapper.insert(log);

        if (StringUtils.isNotBlank(line.getRemark())){
            log.setMessage(line.getRemark());
            count += logMapper.insert(log);
        }

        if (count <= 0) {
            return ReplyHelper.error();
        }
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

        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setCreatorUser(accessToken.getUserName());
        line.setCreatorUserId(accessToken.getUserId());

        Integer count = airlineMapper.updateLine(line);
        count += logMapper.insert(airlineVO.setAirlineLog(line, false));

        if (count <= 0) {
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
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
        //日志
        Log log = new Log();
        log.setOperatorId(accessToken.getUserId());
        log.setOperatorUser(accessToken.getUserName());
        log.setAirlineId(line.getId());
        log.setEventName("删除航线");
        log.setEventSource("CRM");
        log.setMessage("删除航线成功");
        row += logMapper.insert(log);

        if (row <= 0) {
            return ReplyHelper.error();
        }
        return ReplyHelper.success();
    }

    @Override
    @Transactional
    public Reply lineList(Line line, String token) {
        // 更新航线状态：过期
        airlineMapper.updateAirLineStatusByNow();
        AccessToken accessToken = JsonUtils.toAccessToken(token);
        line.setAccountId(accessToken.getAccountId());
        line.setPageIndex((line.getPageIndex() - 1) * line.getPageSize());
        List<Line> lines = airlineMapper.queryLineList(line);
        return ReplyHelper.success(lines);
    }

    @Override
    public Reply lineInfo(String token, Line line) {
        line = airlineMapper.getLine(line.getId());
        Airline airline = airlineMapper.getAirlineById(line.getAirlineId());
        List<LineDetail> voyages = airlineMapper.getVoyages(airline.getId());
        Map<String, Object> resultMap = new HashMap<>(16);
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

        String accountId = JsonUtils.toAccessToken(token).getAccountId();
        String accountName = line.getSupplierName();
        String userId = JsonUtils.toAccessToken(token).getUserId();
        String userName = JsonUtils.toAccessToken(token).getUserName();
        List<Flight> flightList = airlineMapper.getFlights(airlineId);

        if (1 == status && airlineMapper.ifOnSale(airlineId) == null) {
            flightList.stream().forEach(f -> airlineVO.addSeats(accountId, f.getId(), accountName, f.getSeatCount(), userName, userId));
        } else if (2 == status) {
            airlineMapper.deleteSeats(airlineId);
        }

        return ReplyHelper.success();
    }

    @Override
    @Transactional
    public Reply queryFlightInfo(LineDetail info) throws InvocationTargetException, IllegalAccessException, IOException {
        List<LineDetail> airlineList = airlineMapper.getFlightInfos(info.getFlightNo());

        if (airlineList.size() > 0) {
            return ReplyHelper.success(airlineList);
        }

        List<LineDetail> lineDetails = variFlight.initVariFlightData(info.getFlightNo(), info.getBeginDate());

        if (lineDetails == null || lineDetails.size() == 0) {
            return ReplyHelper.fail("航班信息不存在，手工录入");
        }

        return ReplyHelper.success(lineDetails);
    }

    @Transactional
    @Override
    public Reply addFlightInfo(LineDetail info) {   //需求待确认
        info.setId(Generator.uuid());
        List<LineDetail> lineDetails = new ArrayList<>();
        String DepCity = airlineMapper.findCityNameByIataCode(info.getFlightDepcode());
        String arrCity = airlineMapper.findCityNameByIataCode(info.getFlightArrcode());
        if (info.getFlightDeptimePlanDate().equals(info.getFlightArrtimePlanDate())) {
            return ReplyHelper.fail("起飞时间和到达时间不能相同");
        }
        if (StringUtils.isBlank(DepCity) || StringUtils.isBlank(arrCity)) {
            return ReplyHelper.fail("三字码或对应的城市不存在");
        }
        lineDetails.add(info);
        airlineMapper.addFlightInfo(lineDetails);
        return ReplyHelper.success();
    }

    @Transactional
    @Override
    public Reply updateFlightInfo(LineDetail info) throws InvocationTargetException, IllegalAccessException, IOException { //需求待确认
        Integer row = airlineMapper.deleteFlightInfo(info.getFlightNo());
        if (row <= 0) {
            return ReplyHelper.error();
        }
        List<LineDetail> lineDetails = variFlight.initVariFlightData(info.getFlightNo(), info.getBeginDate());
        return ReplyHelper.success(lineDetails);
    }

    @Override
    public Reply newLineInfo(Line line) {
        Map<String, Object> resultMap = new HashMap<>(16);
        line.setPageIndex(line.getPageIndex() * line.getPageSize());
        List<NewLine> newLines = airlineMapper.newLineData(line);

        if (newLines.size() < line.getPageSize()) {
            resultMap.put("isLastPage", true);
        } else {
            resultMap.put("isLastPage", false);
        }

        resultMap.put("lines", newLines);
        return ReplyHelper.success(resultMap);
    }

    @Override
    public Reply listLogs(String airlineId) {
        return ReplyHelper.success(logMapper.searchLogs(airlineId));
    }

    @Override
    public Reply getAirlineInfo(Line line) {
        line = airlineMapper.getLine(line.getId());
        Airline airline = airlineMapper.getAirlineById(line.getAirlineId());
        return ReplyHelper.success(line, airline);
    }

    @Override
    @Transactional
    public Reply updateExpireFlights(Line line) {
        List<String> airlineIds = airlineMapper.queryExpireFlights(line.getDepartureEnd());
        StringBuffer sb = new StringBuffer("");

        airlineIds.forEach(aid -> {
            Log log = new Log();
            log.setAirlineId(aid);
            log.setEventCode("1007");
            log.setMessage("航线过期更新状态成功");
            log.setOperatorUser(line.getCreatorUser());
            log.setOperatorId(line.getCreatorUserId());
            logMapper.insert(log);
            sb.append("'");
            sb.append(aid);
            sb.append("',");
        });

        if (null != airlineIds && airlineIds.size() > 0) {
            String ids = sb.toString().substring(0, sb.toString().length() - 1);
            airlineMapper.updateExpireFlights(ids);
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply getLineByFlightId(Line line) {
        line = airlineMapper.getLineByFlightId(line.getId());

        if (null == line) {
            return ReplyHelper.invalidParam("暂无数据");
        }

        Airline airline = airlineMapper.getAirlineById(line.getAirlineId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("supplierName", line.getSupplierName());
        jsonObject.put("flightDate", line.getDepartureEnd());
        jsonObject.put("voyage", airline.getVoyage());
        jsonObject.put("flightNumber", airline.getFlightNumber());
        return ReplyHelper.success(jsonObject.toJSONString());
    }

    @Override
    public Reply getEnableFlights(String token, Line line) {
        AccessToken accessToken = JsonUtils.toAccessToken(token);
        Integer sum = airlineMapper.getEnableFlights(accessToken.getAccountId(), line.getId());
        return ReplyHelper.success(sum);
    }

    @Override
    public Reply supplierLineCount(String token) {
        AccessToken accessToken = JsonUtils.toAccessToken(token);
        long recordCount = airlineMapper.airlineCount(accessToken.getAccountId());
        return ReplyHelper.success(recordCount);
    }
}
