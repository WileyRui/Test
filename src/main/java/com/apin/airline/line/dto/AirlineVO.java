package com.apin.airline.line.dto;

import com.apin.airline.common.entity.Airline;
import com.apin.airline.common.entity.Line;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.AirportMapper;
import com.apin.airline.common.mapper.CityMapper;
import com.apin.util.*;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 航线信息操作
 *
 * @author wiley
 * @date 2017年7月19日 下午2:24:39
 */
public class AirlineVO {
    @Autowired
    AirlineMapper airlineMapper;
    @Autowired
    AirportMapper airportMapper;
    @Autowired
    CityMapper cityMapper;

    private static AccessToken accessToken;

    /**
     * 初始化航线数据
     *
     * @param token
     * @param lineBo
     * @param flightDetails
     * @return 航线实体类
     */
    public Line setLine(String token, LineBo lineBo, List<FlightDetail> flightDetails) {
        accessToken = JsonUtils.toAccessToken(token);
        String accountId = accessToken.getAccountId();
        String userId = accessToken.getUserId();
        String userName = accessToken.getUserName();
        Line line = new Line();
        line.setId(Generator.uuid());
        String newAirlineNo = airlineMapper.findNew();
        if (StringUtils.isBlank(newAirlineNo)) {
            line.setAirlineNo("1");
        } else {
            line.setAirlineNo(String.valueOf(Integer.parseInt(newAirlineNo) + 1));
        }
        line.setAccountId(accountId);
        line.setSupplierName(lineBo.getSupplireName());
        line.setCreatorUserId(userId);
        line.setCreatorUser(userName);
        String[] dateArray = flightDetails.get(0).getDatePeriod().split("/");
        line.setDepartureStart(DateHelper.parseDate(dateArray[0]));
        line.setDepartureEnd(DateHelper.parseDate(dateArray[1]));
        // 舱位类型：1、系列团；2、余位
        line.setSeatType(lineBo.getSeatType().byteValue());
        // 舱位数量
        line.setSeatCount(lineBo.getSeatCount());
        // 定金
        line.setDepositAmount(lineBo.getDepositAmount());
        // 成人、儿童票价
        line.setAdultPrice(lineBo.getAdultPrice());
        line.setChildPrice(lineBo.getChildPrice());
        // 尾款、余票回收、出票最晚天数
        line.setPayAdvance(lineBo.getPayAdvance());
        line.setTicketAdvance(lineBo.getTicketAdvance());
        line.setRecoveryAdvance(lineBo.getRecoveryAdvance());
        // 行李规则
        line.setFreeBag(lineBo.getFreeBag());
        line.setWeightLimit(lineBo.getWeightLimit());
        // 预警 选填
        if (lineBo.getAlertAdvance() != null) {
            line.setAlertAdvance(lineBo.getAlertAdvance());
        }
        if (lineBo.getAlertRate() != null) {
            line.setAlertRate(lineBo.getAlertRate());
        }
        String manager = lineBo.getManager().toString();
        String managerId = lineBo.getManagerId();
        if (!StringUtils.isBlank(manager)) {
            line.setManager(manager);
        }
        if (!StringUtils.isBlank(managerId)) {
            line.setManagerId(managerId);
        }
        // 退、该、签
        line.setCanReturn(false);
        line.setCanChange(false);
        line.setCanSign(false);
        // 新增航线默认待上架、有效
        line.setAirlineStatus((byte) 0);
        line.setInvalid(false);
        line.setCreatedTime(new Date());
        return line;
    }

    /**
     * 初始化航线基础数据
     *
     * @param lineBo
     * @param flightDetails
     * @return 航线基础数据实体类
     */
    public Airline setAirline(LineBo lineBo, List<FlightDetail> flightDetails) {
        Airline airline = new Airline();
        airline.setId(Generator.uuid());
        airline.setWeekFlights(flightDetails.get(0).getWeekFlights());
        Integer flightType = lineBo.getFlightType();
        airline.setFlightype(flightType.byteValue());
        airline.setInvalid(false);
        airline.setCreatorUser(lineBo.getCreatorUser());
        airline.setCreatorUserId(lineBo.getCreatorUserId());
        airline.setCreatedTime(new Date());
        return airline;
    }

    public Airline setMsdAirline(Airline msdAirline, List<FlightDetail> flightDetails, int i, int flightType, StringBuilder appendFlight) {
        FlightDetail flightDetail = flightDetails.get(i);
        if (flightType == 1) { // 单程
            msdAirline.setFlightNumber(flightDetail.getFlightNo());
            msdAirline.setVoyage(this.appendVoyage(flightType, flightDetails));
            msdAirline.setHashKey(this.hashValue(flightDetails));
        }
        if (flightType == 2) { // 往返
            String flightNo = flightDetail.getFlightNo();
            msdAirline.setHashKey(this.hashValue(flightDetails));
            if (i == flightDetails.size() - 1) {
                msdAirline.setFlightNumber(msdAirline.getFlightNumber() + "<->" + flightNo);
                msdAirline.setVoyage(this.appendVoyage(flightType, flightDetails));
            } else {
                msdAirline.setFlightNumber(flightNo);
            }
        }
        if (flightType == 3) { // 多程
            msdAirline.setHashKey(this.hashValue(flightDetails));
            if (i == flightDetails.size() - 1) {
                msdAirline.setFlightNumber(appendFlight.append(flightDetail.getFlightNo()).toString());
                msdAirline.setVoyage(this.appendVoyage(flightType, flightDetails));
            } else {
                appendFlight.append(flightDetail.getFlightNo() + ",");
            }
        }
        return msdAirline;
    }

    /**
     * 拼接航程
     *
     * @param flightType    航线类型
     * @param flightDetails 航班詳情
     * @return string
     */
    public String appendVoyage(int flightType, List<FlightDetail> flightDetails) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < flightDetails.size(); i++) {
            FlightDetail flightDetail = flightDetails.get(i);
            String depAirportCode = flightDetail.getDepAirportCode();
            String depCity = cityMapper.findCityNameByIataCode(depAirportCode);
            String arrAirportCode = flightDetail.getArrAirportCode();
            String arrCity = cityMapper.findCityNameByIataCode(arrAirportCode);
            switch (flightType) {
                case 1:
                    buffer.append(depCity + "-").append(arrCity);
                    break;
                case 2:
                    if (i == 0) {
                        buffer.append(depCity + "<->").append(arrCity);
                    }
                    break;
                case 3:
                    if (i == flightDetails.size() - 1) {
                        buffer.append(depCity + "-").append(arrCity);
                    } else {
                        buffer.append(depCity + "-").append(arrCity + ",");
                    }
                    break;
                default:
                    break;
            }
        }
        return buffer.toString();
    }

    /**
     * 生成hashValue
     *
     * @param flightDetails
     * @return hashValue
     */
    public String hashValue(List<FlightDetail> flightDetails) {
        // hashValue
        StringBuffer hashValue = new StringBuffer();
        // 出发城市-到达城市
        StringBuffer city2City = new StringBuffer();
        // 出发城市-到达机场
        StringBuffer city2airport = new StringBuffer();
        // 出发机场-到达城市
        StringBuffer airport2City = new StringBuffer();
        // 出发机场-到达机场
        StringBuffer airport2airport = new StringBuffer();
        for (FlightDetail flightDetail : flightDetails) {
            String depAirportCode = flightDetail.getDepAirportCode();
            String arrAirportCode = flightDetail.getArrAirportCode();
            String arrCityCode = airportMapper.findCityCode(arrAirportCode);
            String depCityCode = airportMapper.findCityCode(depAirportCode);
            String days = flightDetail.getDays();
            city2City.append(depCityCode + arrCityCode + days);
            city2airport.append(depCityCode + arrAirportCode + days);
            airport2City.append(depAirportCode + arrCityCode + days);
            airport2airport.append(depAirportCode + arrAirportCode + days);
        }
        hashValue.append(Generator.md5(city2City.toString()) + "," + Generator.md5(city2airport.toString()) + ","
                + Generator.md5(airport2City.toString()) + "," + Generator.md5(airport2airport.toString()));
        return hashValue.toString();
    }


    /**
     * 验证参数
     *
     * @param lineBo
     * @return Reply
     */
    public Reply checkData(LineBo lineBo) {
        if (StringUtils.isBlank(lineBo.getFlightType().toString())
                || StringUtils.isBlank(lineBo.getSeatType().toString())
                || StringUtils.isBlank(lineBo.getAdultPrice().toString())
                || StringUtils.isBlank(lineBo.getChildPrice().toString())
                || StringUtils.isBlank(lineBo.getSeatCount().toString())
                || StringUtils.isBlank(lineBo.getTicketAdvance().toString())) {
            return ReplyHelper.fail("参数为空或不符合格式");
        }
        if (lineBo.getTicketAdvance() >= lineBo.getRecoveryAdvance()) {
            return ReplyHelper.fail("余票回收天数必须大于开票提前天数");
        }
        List<FlightDetail> msdAirlineList = lineBo.getMsdAirlineInfoList();
        if (msdAirlineList == null) {
            return ReplyHelper.fail("缺少航班信息");
        }
        return null;
    }

    /**
     * 判断航线是否重复
     * @param hashKey
     * @return
     */
    public boolean LineRepeat(String hashKey){
        String airlineId = airlineMapper.getExistedAirline(hashKey);
        if (StringUtils.isNotBlank(airlineId)){
            return true;
        }
        return false;
    }


   /* public MbsAirlineFlight setAirlineFlight(MbsAirlineFlight airlineFlight, MbslineBo airlineE, String flightDate) {
        Date date = DateUtil.String2Date(flightDate, "yyyy-MM-dd");
        airlineFlight.setId(UUID.randomUUID().toString().replace("-", ""));
        airlineFlight.setFlightDate(date);
        airlineFlight.setAdultPrice(airlineE.getAdultPrice());
        airlineFlight.setChildPrice(airlineE.getChildPrice());
        if (airlineE.getAlertAdvance() != null) {
            airlineFlight.setAlertDate(DateUtil.dateAddSub(date, -airlineE.getAlertAdvance()));
        }
        airlineFlight.setTicketDate(DateUtil.dateAddSub(date, -airlineE.getTicketAdvance()));
        airlineFlight.setRecoveryDate(DateUtil.dateAddSub(date, -airlineE.getRecoveryAdvance()));
        return airlineFlight;
    }

    public MbsAirlineLog setAirlineLog(MbsAirlineE airlineE, String id, boolean flag) {
        MbsAirlineLog airlineLog = new MbsAirlineLog();
        airlineLog.setId(Generator.uuid());
        airlineLog.setEventSource("CRM");
        airlineLog.setAirlineId(id);
        if (flag) {
            airlineLog.setEventName("新增航线");
            airlineLog.setMessage("新增航线成功");
        } else {
            airlineLog.setEventName("编辑航线");
            airlineLog.setMessage("编辑航线成功");
        }
        airlineLog.setOperatorId(airlineE.getCreatorUserId());
        airlineLog.setOperatorUser(airlineE.getCreatorUser());
        return airlineLog;
    }*/
}
