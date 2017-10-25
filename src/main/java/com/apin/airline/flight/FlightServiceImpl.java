package com.apin.airline.flight;

import com.apin.airline.common.AopService;
import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.entity.Log;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.BaseMapper;
import com.apin.airline.common.mapper.LogMapper;
import com.apin.airline.common.mapper.QueryMapper;
import com.apin.airline.flight.dto.*;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/12
 */
@Service
public class FlightServiceImpl implements FlightService {
    @Autowired
    private AirlineMapper airlineMapper;
    @Autowired
    private AopService aopService;
    @Autowired
    private QueryMapper queryMapper;
    @Autowired
    private BaseMapper baseMapper;
    @Autowired
    private LogMapper logMapper;
    @Override
    public Reply airlineInfo(CalendarInfo calendarInfo) {
        return null;
    }

    @Override
    public Reply modifyStock(Stock stock) {
        return null;
    }

    @Override
    public Reply modifyPrice(Stock stock) {
        BigDecimal adultPrice = stock.getAdultPrice();
        BigDecimal childPrice = stock.getChildPrice();
            airlineMapper.updatePrice(stock.getAirlineId(),stock.getDateList(),stock.getAdultPrice(),stock.getChildPrice());
        Log mbsAirlineLog = new Log(stock.getAirlineId(),"1006","修改价格成功,修改为成人价"+adultPrice+",儿童价"+childPrice,stock.getUserName(),stock.getUserId(),stock.getEventSource());
        aopService.insertLog(mbsAirlineLog);
        return ReplyHelper.success();
    }

    @Override
    public Reply searchFlight(SearchDto searchDto) {
        List<FlightDetail> flightDetails=new ArrayList<>();
        for (CityList cityList : searchDto.getCityList()) {
            String img = queryMapper.selectCityImg(cityList.getArrCity());
            FlightDetail flightDetail = queryMapper.selectFlight(cityList);
            if (flightDetail!=null) {
                flightDetail.setArrCityImgUrl(img);
                flightDetails.add(flightDetail);
            }
        }
        if (flightDetails.size()>8){
            flightDetails = flightDetails.subList(0, 8);
        }
        return ReplyHelper.success(flightDetails);
    }

    /**
     * 根据城市对与出发日期查询航班
     *
     * @param cityList
     * @return
     */
    @Override
    public Reply searchFlights(CityList cityList) {
        List<FlightDetail> flightDetails = queryMapper.selectFlights(cityList);
        return ReplyHelper.success(flightDetails);
    }

    @Override
    public Reply searchFlightMonth(CityList cityList) {
        return ReplyHelper.success(queryMapper.selectFlightsMonth(cityList));
    }

    @Override
    public Reply searchFlightDetail(CityList cityList) throws Exception{
        List<ResponseAirlineDto> responseAirlineDtos = queryMapper.selectFlightDetail(cityList);
        for (ResponseAirlineDto responseAirlineDto : responseAirlineDtos) {
            List<AirlineInfo> airlineInfos = queryMapper.selectByFlightNum(responseAirlineDto.getAirlineId());
            if (airlineInfos.size()==0) {
                continue;
            }
            if (airlineInfos.size() == 2) {
                AirlineInfo airlineInfo = airlineInfos.get(1);
                airlineInfo.setDepDate(responseAirlineDto.getRetDate());
                airlineInfo.setArrDate(getArrDate(airlineInfo));
            }
            AirlineInfo airlineInfo = airlineInfos.get(0);
            airlineInfo.setDepDate(cityList.getDepDate());
            airlineInfo.setArrDate(getArrDate(airlineInfo));
            responseAirlineDto.setAirlineInfo(airlineInfos);
        }
        return ReplyHelper.success(responseAirlineDtos);
    }

    @Override
    public Reply searchFlightList(CityList cityList) {
        List<ResponseAirlineDto> responseAirlineDto = queryMapper.selectFlightList(cityList);
        return ReplyHelper.success(responseAirlineDto);
    }

    @Override
    public Reply monthQuery(CityList cityList) {
       List<String> months= queryMapper.selectMonthQuery(cityList);
        return ReplyHelper.success(months);
    }

    @Override
    public Reply dayQuery(CityList cityList) {
        List<DayPrice> dayPrices = queryMapper.selectFlightDates(cityList);
        return ReplyHelper.success(dayPrices);
    }



    private String getArrDate(AirlineInfo airlineInfo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date arr = formatter.parse(airlineInfo.getArrTime());
        Date dep = formatter.parse(airlineInfo.getDepTime());
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = formatter1.parse(airlineInfo.getDepDate());
        if (arr.getTime() > dep.getTime()) {
            return airlineInfo.getDepDate();
        }
        airlineInfo.setTag(1);
        return formatter1.format(new Date(parse.getTime()+24*3600*1000));
    }

    @Override
    public Reply searchDayAirlines(SearchDayAirlinesDto searchAirlineDto) {
        PageHelper.startPage(searchAirlineDto.getPage(), searchAirlineDto.getSize());
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<AirlineInfo> list = queryMapper.searchDayAirlines(searchAirlineDto);
        PageInfo pageInfo = new PageInfo(list);
        map.put("totalRows", (int) pageInfo.getTotal());
        map.put("currentPage", pageInfo.getPageNum());
        return ReplyHelper.success(pageInfo.getList(),map);
    }
    /**
     * 价格批量导入
     * @param priceTemplateBeans
     */
    @Override
    @Transactional
    public Reply priceImport(List<PriceTemplateBean> priceTemplateBeans) {
        if(priceTemplateBeans!=null){
            for (int i = 0; i < priceTemplateBeans.size(); i++) {
                airlineMapper.updatePrice(priceTemplateBeans.get(i));
            }
            Log log = new Log();
            log.setId(Generator.uuid());
            log.setAirlineId(priceTemplateBeans.get(0).getAirlineId());
            log.setEventSource("CRM");
            log.setOperatorId(priceTemplateBeans.get(0).getAccountId());
            log.setOperatorUser(priceTemplateBeans.get(0).getUserName());
            log.setMessage("批量导入更新");
            logMapper.insert(log);

        }
        return ReplyHelper.success();
    }
    /**
     * 更新每日价格
     * @param priceTemplateBean
     */
    @Override
    @Transactional
    public Reply priceUpdate(PriceTemplateBean priceTemplateBean) {
        List<String> flightDates = priceTemplateBean.getFlightDates();
        if(flightDates!=null){
            for (int i = 0; i < flightDates.size(); i++) {
                priceTemplateBean.setFlightDate(flightDates.get(i));
                airlineMapper.updatePrice(priceTemplateBean);
            }
            String message = "";
            if(priceTemplateBean.getAdultPrice()!=null){
                message = "批量修改价格";
            }
            else if(priceTemplateBean.getStoreCount()!=null){
                message = "批量修改库存";
            }
            else if(priceTemplateBean.getRecycleDay()!=null){
                message = "批量修改清位时间";
            }
            Log log = new Log();
            log.setId(Generator.uuid());
            log.setAirlineId(priceTemplateBean.getAirlineId());
            log.setEventSource("CRM");
            log.setOperatorId(priceTemplateBean.getUserId());
            log.setOperatorUser(priceTemplateBean.getUserName());
            log.setMessage(message);
            logMapper.insert(log);
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply getAirlineDates(String airlineId) {
        List<Flight> flights = airlineMapper.getFlights(airlineId);
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(flights!=null&&flights.size()>0){
            for (int i = 0; i < flights.size(); i++) {
         dates.add(formatter.format(flights.get(i).getFlightDate()));
            }
        }
        return ReplyHelper.success(dates);
    }
}
