package com.apin.airline.ticket;

import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Deal;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 机票服务接口
 */
public interface TicketService {
    /**
     * 日历查看
     *
     * @param calendarInfo
     * @return
     */
    Reply airlineInfo(CalendarInfo calendarInfo);

    /**
     * 日历内修改库存
     *
     * @param stock
     * @return
     */
    Reply modifyStock(Stock stock);

    /**
     * 日历内逍客
     *
     * @param stock
     * @return
     */
    Reply saleStock(Stock stock);

    /**
     * 分配库存
     *
     * @param deal
     * @return
     */
    Reply dealStock(Deal deal);
}
