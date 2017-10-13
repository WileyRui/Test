package com.apin.airline.flight;

import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航班管理服务接口
 */
public interface FlightService {



    /**
     * 日历查看
     * @param calendarInfo
     * @return
     */
    Reply airlineInfo(CalendarInfo calendarInfo);

    /**
     * 日历内修改库存
     * @param stock
     * @return
     */
    Reply modifyStock(Stock stock);

    /**
     * 修改价格
     * @param stock
     * @return
     */
    Reply modifyPrice(Stock stock);
}
