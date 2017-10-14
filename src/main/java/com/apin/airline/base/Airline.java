package com.apin.airline.base;

import com.apin.airline.flight.dto.SearchDto;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Deal;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线基础数据类
 */
public class Airline {
    public static Reply nullValue(Object... obj){
        Reply reply = ReplyHelper.fail();
        String out = "";
        Boolean flag = true;
        Integer size = obj.length;
        for(int i=0;i<size/2;i++){
            Object o = obj[i];
            Object oName = obj[i+size/2];
            if(o==null) {
                out += oName+"|";
                flag = false;
            }
        }
        if(flag)
            return ReplyHelper.success();
        reply.setMessage(out+"为空");
        return reply;
    }

    public static Reply calendarInfoCheck(CalendarInfo calendarInfo){
        return nullValue(calendarInfo.getAccountId(),calendarInfo.getAirlineId(),calendarInfo.getStartDate(),
                calendarInfo.getEndDate(),"accountId","airlineId","startDate","endDate");
    }

    public static Reply stockCheck(Stock stock){
        return nullValue(stock.getAccountId(),stock.getAirlineId(),stock.getFlightId(),
                stock.getStockNumber(),"accountId","airlineId","flightId","stockNumber");
    }

    public static Reply stockPriceCheck(Stock stock){
        return nullValue(stock.getAdultPrice(),stock.getChildPrice(),stock.getAirlineId(),
                stock.getDateList(),"adultPrice","childPrice","airlineId","dateList");
    }

    public static Reply dealCheck(Deal deal){
        return nullValue(deal.getAirlineId(),deal.getDateList(),deal.getTotal(),deal.getAccountId(),
                deal.getOwnerElementList(),"airlineId","dateList","total","accountId","ownerList");
    }
    public static Reply searchCheck(SearchDto searchDto, int i){
        Reply reply=new Reply();
        switch (i) {
            case 1:
                reply = nullValue(searchDto.getDepartCity(), searchDto.getDestCity(), searchDto.getType(),
                        "departCity", "destCity", "type");
                break;
            case 2:
                reply = nullValue(searchDto.getDepartCity(), searchDto.getDestCity(), searchDto.getType(),
                        searchDto.getDepartDate(), "departCity", "destCity", "type", "departDate");
                break;
            case 3:
                reply = nullValue(searchDto.getDepartCity(), searchDto.getDestCity(), searchDto.getType(),
                        searchDto.getDepartDate(),searchDto.getDestDate(), "departCity", "destCity", "type", "departDate","destDate");
                break;
        }
        return reply;
    }
    public static String getVoyage(SearchDto searchDto){
        String voyageStr="";
        switch (searchDto.getType()) {
            case 1:
                voyageStr = searchDto.getDepartCity() + "-" + searchDto.getDestCity();
                break;
            case 2:
                voyageStr = searchDto.getDepartCity() + "<->" + searchDto.getDestCity();
                break;
        }
        return voyageStr;
    }

}
