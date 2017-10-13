package com.apin.airline.line;

import com.apin.airline.base.VariFlight;
import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.common.entity.Line;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.line.dto.LineBo;
import com.apin.util.DateHelper;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Transactional
    @Override
    public Reply queryFlightInfo(LineBo lineBo) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> resultMap = new HashMap<>();
        // 本地库查询
        List<FlightInfo> airlineList = airlineMapper.getFlightInfos(lineBo.getFlightNo());
        if (airlineList.size() > 0) {
            resultMap.put("flightList", airlineList);
            resultMap.put("week", airlineList.get(0).getFlights());
            return ReplyHelper.success(resultMap);
        }
        // 若本地库没有，则调飞常准接口
        // 查询7天的航线
        List<Map<String, String>> flightList;
        int[] days = new int[7];
        for (int i = 0; i < 7; i++) {
            flightList = variFlight.getFlightInfo(lineBo.getFlightNo(), lineBo.getBeginDate());
            Date dateS = DateHelper.parseDate(lineBo.getBeginDate());
            int week = DateHelper.getWeek(lineBo.getBeginDate());
            if (flightList != null && flightList.size() > 0) {
                resultMap.put("flightList1", flightList);
                if (week == 7) {
                    days[0] = 1;
                } else {
                    days[week] = 1;
                }
            }
            Date dateAdd = new Date(dateS.getTime() + 1 * 24 * 60 * 60 * 1000);
            lineBo.setBeginDate(new SimpleDateFormat("yyyy-MM-dd").format(dateAdd));
        }
        String flights = Arrays.toString(days).replace("[", "").replace("]", "").replace(" ", "");
        List<Map<String, String>> list = (List<Map<String, String>>) resultMap.get("flightList1");
        if (list == null) {
            return ReplyHelper.fail("航班信息不存在，如需要，请手动录入！");
        }
        List<FlightInfo> flightInfoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<>();
            for (String key : list.get(i).keySet()) {
                char[] chars = new char[1];
                chars[0] = key.charAt(0);
                String temp = new String(chars);
                switch (key) {
                    case "FlightDeptimePlanDate":
                        String deptimePlanDate = list.get(i).get(key).substring(11);
                        map.put(key.replaceFirst(temp, temp.toLowerCase()), deptimePlanDate);
                        break;
                    case "FlightArrtimePlanDate":
                        String arrtimePlanDate = list.get(i).get(key).substring(11);
                        map.put(key.replaceFirst(temp, temp.toLowerCase()), arrtimePlanDate);
                        break;
                    default:
                        map.put(key.replaceFirst(temp, temp.toLowerCase()), list.get(i).get(key));
                        break;
                }
                map.put("flights", flights);
            }
            Object flightInfo = new FlightInfo();
            BeanUtils.populate(flightInfo, map);
            FlightInfo info = (FlightInfo) flightInfo;
            info.setId(Generator.uuid());
            flightInfoList.add(info);
        }
        // save to database
        airlineMapper.addFlightInfo(flightInfoList);
        resultMap.put("flightList", flightInfoList);
        resultMap.remove("flightList1");
        resultMap.put("week", flights);
        return ReplyHelper.success(resultMap);
    }

    @Transactional
    @Override
    public Reply addFlightInfo(FlightInfo info) {   //需求待确认
        info.setId(Generator.uuid());
        info.setCreatedTime(new Date());
        info.setUpdateTime(new Date());
        List<FlightInfo> flightInfoList = new ArrayList<>();
        flightInfoList.add(info);
        airlineMapper.addFlightInfo(flightInfoList);
        return ReplyHelper.success();
    }

    @Transactional
    @Override
    public Reply updateFlightInfo(LineBo lineBo) throws InvocationTargetException, IllegalAccessException { //需求待确认
        airlineMapper.deleteFlightInfo(lineBo.getFlightNo());
        Reply reply = queryFlightInfo(lineBo);
        return ReplyHelper.success(reply.getData(), "航班信息更新成功");
    }
}
