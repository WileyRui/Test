package com.apin.airline.flight;

import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.flight.dto.SearchDto;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航班管理服务接口
 */
public interface FlightService {


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
     * 修改价格
     *
     * @param stock
     * @return
     */
    Reply modifyPrice(Stock stock);

    /**
     * 价格批量导入
     *
     * @param priceTemplateBeanList
     */
    Reply priceImport(List<PriceTemplateBean> priceTemplateBeanList);

    /**
     * 根据城市对查询航班信息
     *
     * @param searchDto
     * @return
     */
    Reply searchFlight(SearchDto searchDto);

    /**
     * 根据城市对与出发日期查询航班
     *
     * @param searchDto
     * @return
     */
    Reply searchFlights(SearchDto searchDto);

    /**
     * 根据城市对与出发日期结束日期查询航班详情
     *
     * @param searchDto
     * @return
     */
    Reply searchFlightDetail(SearchDto searchDto);
}
