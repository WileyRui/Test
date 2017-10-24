package com.apin.airline.common;

import com.apin.airline.common.entity.LineDetail;
import com.apin.airline.common.entity.VariFlight;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.util.DateHelper;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wiley
 * @date 2017/10/12
 * @remark 飞常准查询接口
 */
@Component
public class VariFlightClient {
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
     * @param flightNo   航班号
     * @param departDate 出发日期 "yyyy-MM-dd"
     * @return List<VariFlight>
     */
    private List<VariFlight> getFlightInfo(String flightNo, String departDate) {
        StringBuilder content = new StringBuilder();
        List<VariFlight> variFlights;
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("fnum", flightNo);
        paramMap.put("appid", appId);
        paramMap.put("date", departDate);
        ArrayList<String> keyList = new ArrayList<>(paramMap.keySet());
        Collections.sort(keyList);
        for (int i = 0; i < keyList.size(); ++i) {
            String key = keyList.get(i);
            String value = String.valueOf(paramMap.get(key));
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }
        content.append(appSecurity);
        String token = DigestUtils.md5Hex(DigestUtils.md5Hex(content.toString()));
        paramMap.put("token", token);
        try {
            String result = sendHttpGet(url, paramMap);
            variFlights = JsonUtils.toBean(result, JsonUtils.getJavaType(List.class, VariFlight.class));
            return variFlights;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化飞常准数据
     *
     * @param flightNo   航班号
     * @param departDate 出发日期
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public List<LineDetail> initVariFlightData(String flightNo, String departDate) throws InvocationTargetException, IllegalAccessException {
        List<VariFlight> variFlights;
        List<VariFlight> variFlights1 = new ArrayList<>();
        int[] days = new int[7];
        for (int i = 0; i < days.length; i++) {
            variFlights = getFlightInfo(flightNo, departDate);
            Date dateS = DateHelper.parseDate(departDate);
            int week = DateHelper.getWeek(departDate);
            if (variFlights != null && variFlights.size() > 0) {
                variFlights1 = new ArrayList<>(variFlights);
                if (week == days.length) {
                    days[0] = 1;
                } else {
                    days[week] = 1;
                }
            }
            Date dateAdd = new Date(dateS.getTime() + 24 * 60 * 60 * 1000);
            departDate = new SimpleDateFormat("yyyy-MM-dd").format(dateAdd);
        }
        if (variFlights1.size() == 0) {
            return null;
        }
        String flights = Arrays.toString(days).replace("[", "").replace("]", "").replace(" ", "");
        List<LineDetail> lineDetails = new ArrayList<>();
        variFlights1.forEach(i -> {
            LineDetail info = new LineDetail();
            info.setId(Generator.uuid());
            info.setFcategory(Byte.valueOf(i.getFcategory()));
            info.setFlightNo(i.getFlightNo());
            info.setFlightArr(i.getFlightArr());
            info.setFlightDep(i.getFlightDep());
            info.setFlightArrAirport(i.getFlightArrAirport());
            info.setFlightArrcode(i.getFlightArrcode());
            info.setFlightArrtimePlanDate(i.getFlightArrtimePlanDate());
            info.setFlightCompany(i.getFlightCompany());
            info.setFlightDepAirport(i.getFlightDepAirport());
            info.setFlightDepcode(i.getFlightDepcode());
            info.setFlightDeptimePlanDate(i.getFlightDeptimePlanDate());
            info.setStopFlag(i.getStopFlag().equals(1));
            info.setWeekFlights(flights);
            lineDetails.add(info);
        });
        airlineMapper.addFlightInfo(lineDetails);
        return lineDetails;
    }

    /**
     * 发送http请求 GET
     *
     * @param strUrl url
     * @param params 参数
     * @return
     * @throws IOException
     */
    private static String sendHttpGet(String strUrl, Map params) throws IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod();
        strUrl = strUrl + "?" + urlEncode(params);
        method.setPath(strUrl);
        client.executeMethod(method);
        String response = new String(method.getResponseBodyAsString().getBytes("UTF-8"));
        System.out.println(response);
        return response;
    }

    /**
     * 初始化请求参数
     *
     * @param data 请求参数
     * @return
     */
    private static String urlEncode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

