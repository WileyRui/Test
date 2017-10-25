package com.apin.airline.flight;

import com.apin.airline.flight.dto.CityList;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.flight.dto.SearchDayAirlinesDto;
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
     * 根据城市对查询航班信息
     *
     * @param searchDto
     * @return
     */
    Reply searchFlight(SearchDto searchDto);

    /**
     * 根据城市对与出发日期查询航班
     *
     * @param cityList
     * @return
     */
    Reply searchFlights(CityList cityList);

    /**
     * 根据城市对与出发日期结束日期查询航班详情
     *
     * @param searchDto
     * @return
     */
    Reply searchFlightDetail(CityList cityList) throws Exception;

    /**
     * 条件查询航班信息
     * @param cityList
     * @return
     */
    Reply searchFlightList(CityList cityList);

    /**
     * 月份查询
     * @param cityList
     * @return
     */
    Reply monthQuery(CityList cityList);

    /**
     * 每日详情查询
     * @param cityList
     * @return
     */
    Reply dayQuery(CityList cityList);

    /**
     * 返程航班月份查询
     * @param cityList
     * @return
     */
    Reply searchFlightMonth(CityList cityList);

    /**
     * 查询每日航线资源
     * @param searchAirlineDto
     * @return
     */
    public Reply searchDayAirlines(SearchDayAirlinesDto searchAirlineDto);

    public Reply priceImport(List<PriceTemplateBean> priceTemplateBeans);
    /**
     * 更新每日航线资源
     * @param priceTemplateBean
     */
    public Reply priceUpdate(PriceTemplateBean priceTemplateBean);

    /**
     * 获取航线下的所有日期
     * @param airlineId
     * @return
     */
    public Reply getAirlineDates(String airlineId);
}
