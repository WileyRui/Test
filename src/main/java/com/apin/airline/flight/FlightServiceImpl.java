package com.apin.airline.flight;

import com.apin.airline.common.Airline;
import com.apin.airline.common.AopService;
import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.entity.Log;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.QueryMapper;
import com.apin.airline.flight.dto.*;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Reply priceImport(List<PriceTemplateBean> priceTemplateBeanList) {
        return null;
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
        return ReplyHelper.success(flightDetails);
    }

    @Override
    public Reply searchFlights(CityList cityList) {
        //cityList.setArrDate(cityList.);
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
            if (airlineInfos.size() == 2) {
                AirlineInfo airlineInfo = airlineInfos.get(1);
                airlineInfo.setDepDate(responseAirlineDto.getRetDate());
                airlineInfo.setArrDate(getArrDate(airlineInfo.getArrTime(), airlineInfo.getDepTime(), airlineInfo.getDepDate()));
            }
            AirlineInfo airlineInfo = airlineInfos.get(0);
            airlineInfo.setDepDate(cityList.getDepDate());
            airlineInfo.setArrDate(getArrDate(airlineInfo.getArrTime(), airlineInfo.getDepTime(), airlineInfo.getDepDate()));
            responseAirlineDto.setAirlineInfo(airlineInfos);
        }
        return ReplyHelper.success(responseAirlineDtos);
    }

    @Override
    public Reply searchFlightList(CityList cityList) {
        PageHelper.startPage(cityList.getPageIndex(),cityList.getPageSize());
        List<ResponseAirlineDto> responseAirlineDto = queryMapper.selectFlightList(cityList);
        if (responseAirlineDto.size() == 0)
            return ReplyHelper.success(new ArrayList<>(),0);
        PageInfo pageInfo=new PageInfo(responseAirlineDto);
        return ReplyHelper.success(responseAirlineDto,pageInfo.getTotal());
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



    private String getArrDate(String arrTime,String depTime,String depDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date arr = formatter.parse(arrTime);
        Date dep = formatter.parse(depTime);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = formatter1.parse(depDate);
        return arr.getTime() > dep.getTime() ? depDate:formatter1.format(new Date(parse.getTime()+24*3600*1000));
    }
}
