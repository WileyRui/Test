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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
            String voyage = Airline.getVoyage(cityList);
            List<String> dateList = queryMapper.selectFlightDates(voyage);
            if (dateList.size() == 0) continue;
            FlightDetail flightDetail = queryMapper.selectFlight(voyage);
            flightDetail.setImg(img);
            flightDetail.setDateList(dateList);
            flightDetails.add(flightDetail);
        }
        return ReplyHelper.success(flightDetails);
    }

    @Override
    public Reply searchFlights(CityList cityList) {
        queryMapper.selectFlights(cityList);
        return null;
    }

    @Override
    public Reply searchFlightDetail(CityList cityList) {
        queryMapper.selectFlightDetail(cityList);
        return null;
    }

    @Override
    public Reply searchFlightList(CityList cityList) {
        ResponseAirlineDto responseAirlineDto = queryMapper.selectFlightList(cityList);
        return ReplyHelper.success(responseAirlineDto);
    }


}
