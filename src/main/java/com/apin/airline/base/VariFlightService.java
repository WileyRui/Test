package com.apin.airline.base;

import com.apin.airline.line.utils.FastJsonUtils;
import com.apin.airline.line.utils.HttpFlightUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.*;

/**
 * @author wiley
 * @date 2017/10/12
 * @remark 飞常准查询接口
 */
@Service
public class VariFlightService {
    private Logger logger = LoggerFactory.getLogger(VariFlightService.class);

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
}

