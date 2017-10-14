package com.apin.airline.flight;

import com.apin.airline.base.Airline;
import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.flight.dto.FlightDetail;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.flight.dto.SearchDto;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
//        BigDecimal adultPrice = stock.getAdultPrice();
//        BigDecimal childPrice = stock.getChildPrice();
//        stock.getDateList().stream().forEach(date->{
//            Flight flight = airlineMapper.findByAirlineIdAndFlightDate(stock.getAirlineId(),date);
//            if(flight!=null){
//                flight.setAdultPrice(adultPrice);
//                flight.setChildPrice(childPrice);
//                airlineMapper.updateById(flight);
//            }
//        });
//        Log mbsAirlineLog = new Log(stock.getAirlineId(),"1006","修改价格成功,修改为成人价"+adultPrice+",儿童价"+childPrice,stock.getUserName(),stock.getUserId());
//        airline.insertLog(mbsAirlineLog);
//        return ReplyHelper.success();
        return null;
    }
    @Override
    public Reply priceImport(List<PriceTemplateBean> priceTemplateBeanList) {
        return null;
    }
    @Override
    public Reply searchFlight(SearchDto searchDto) {
//       String img = airlineMapper.selectCityImg(searchDto.getDestCity());
//        String voyage = Airline.getVoyage(searchDto);
//        List<String> dateList = airlineMapper.selectFlightDates(voyage);
//        if (dateList.size()==0)
//            return ReplyHelper.success();
//        FlightDetail flightDetail = airlineMapper.selectFlightDetail(voyage);
//        flightDetail.setImg(img);
//        flightDetail.setDateList(dateList);
//        return ReplyHelper.success(flightDetail);
        return null;
    }

    @Override
    public Reply searchFlights(SearchDto searchDto) {

        return null;
    }

    @Override
    public Reply searchFlightDetail(SearchDto searchDto) {
        return null;
    }


}
