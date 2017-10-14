package com.apin.airline.ticket;

import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Deal;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;
import org.springframework.stereotype.Service;

/**
 * Author:huanglei
 * Description:机票服务实现
 * Date:2017/10/11
 */
@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public Reply airlineInfo(CalendarInfo calendarInfo) {
        return null;
    }

    @Override
    public Reply modifyStock(Stock stock) {
        return null;
    }

    @Override
    public Reply saleStock(Stock stock) {

        return null;
    }

    @Override
    public Reply dealStock(Deal deal) {
        return null;
    }
}
