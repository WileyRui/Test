package com.apin.airline.base;

import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.line.utils.FastJsonUtils;
import com.apin.airline.common.HttpFlightUtils;
import com.apin.util.DateHelper;
import com.apin.util.Generator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wiley
 * @date 2017/10/12
 * @remark 飞常准查询接口
 */
@Component
public class VariFlight {
    private Logger logger = LoggerFactory.getLogger(VariFlight.class);
    @Autowired
    AirlineMapper airlineMapper;
    @Value("${variflight.appid}")
    private String appId;
    @Value("${variflight.appSecurity}")
    private String appSecurity;
    @Value("${variflight.url}")
    private String url;

    /**
     * 根据航班号和日期获取航线信息
     *
     * @param flightNo
     * @param departDate
     * @return
     */
    public List<Map<String, String>> getFlightInfo(String flightNo, String departDate) {
        StringBuffer content = new StringBuffer();
        Map<String, String> resultMap;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("fnum", flightNo);
        paramMap.put("appid", appId);
        paramMap.put("date", departDate);
        ArrayList<String> keyList = new ArrayList<>(paramMap.keySet());
        Collections.sort(keyList);
        for (int i = 0; i < keyList.size(); ++i) {
            String key = keyList.get(i);
            String value = String.valueOf(paramMap.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        content.append(appSecurity);
        String token = DigestUtils.md5Hex(DigestUtils.md5Hex(content.toString()));
        paramMap.put("token", token);
        try {
            String result = HttpFlightUtils.net(url, paramMap, "GET");
            logger.info("====the variflight result is : " + result);
            if (result.contains("error")) {
                resultMap = FastJsonUtils.parseToMap(result);
                System.out.println("解码后:" + URLDecoder.decode(resultMap.get("error"), "utf-8"));
                return null;
            }
            List<Map<String, String>> flightInfoMapList = FastJsonUtils.parseMapInList(result);
            return flightInfoMapList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("=====the variflight interface get something wrong======");
            return null;
        }
    }

    /**
     * 初始化飞常准数据
     *
     * @param flightNo
     * @param departDate
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public List<FlightInfo> initVariFlightData(String flightNo, String departDate) throws InvocationTargetException, IllegalAccessException {
        // 查询7天的航线
        List<Map<String, String>> flightList;
        Map<String, Object> resultMap = new HashMap<>();
        int[] days = new int[7];
        for (int i = 0; i < 7; i++) {
            flightList = getFlightInfo(flightNo, departDate);
            Date dateS = DateHelper.parseDate(departDate);
            int week = DateHelper.getWeek(departDate);
            if (flightList != null && flightList.size() > 0) {
                resultMap.put("flightList1", flightList);
                if (week == 7) {
                    days[0] = 1;
                } else {
                    days[week] = 1;
                }
            }
            Date dateAdd = new Date(dateS.getTime() + 1 * 24 * 60 * 60 * 1000);
            departDate = new SimpleDateFormat("yyyy-MM-dd").format(dateAdd);
        }
        String flights = Arrays.toString(days).replace("[", "").replace("]", "").replace(" ", "");
        List<Map<String, String>> list = (List<Map<String, String>>) resultMap.get("flightList1");
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
        airlineMapper.addFlightInfo(flightInfoList);
        return flightInfoList;
    }
}

