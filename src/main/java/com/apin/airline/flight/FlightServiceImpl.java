package com.apin.airline.flight;

import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;
import org.springframework.stereotype.Service;

/**
 * Author:huanglei
 * Description:
 * Date:2017/10/12
 */
@Service
public class FlightServiceImpl implements FlightService {
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
        return null;
    }
}
