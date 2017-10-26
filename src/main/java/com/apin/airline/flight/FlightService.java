package com.apin.airline.flight;

import com.apin.airline.flight.dto.*;
import com.apin.airline.flight.dto.CityList;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.flight.dto.SearchDayAirlinesDto;
import com.apin.airline.flight.dto.SearchDto;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;

import java.text.ParseException;
import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航班管理服务接口
 */
public interface FlightService {

    /**
     * 首页日历查看
     * @param request
     * @return
     */
    Reply homeCalendarInfo(HomeCalendarInfoQueryRequest request);

    /**
     * 指定航线日历查看
     *
     * @param calendarInfo
     * @return
     */
    Reply airlineInfo(CalendarInfo calendarInfo) throws ParseException;

    /**
     * 首页日历，查询某天的所有的航班信息
     *
     * @param request 查询请求参数
     * @return 返回某天的在飞航班信息
     */
    Reply homeAirlineInfo(HomeAirlineQueryRequest request);

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
     * arm航班列表查询
     *
     * @param listDto
     * @return
     */
    Reply flightList(DealerListDto listDto);

    /**
     * arm指定分销商库存日历查询
     * @param listDto
     * @return
     */
    Reply stockList(DealerListDto listDto);

    /**
     * arm分销商判断有无数据接口
     * @param dto
     * @return
     */
    Reply hasList(HasListDto dto);

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
     * @param cityList
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
     * 包机商航司合作线路数接口
     * @param listBo
     * @return
     */
    Reply count(DealerListDto listBo);

    /**
     * 包机商航司合作线路航空公司名字接口
     * @return
     */
    Reply compName();
    /** 查询每日航线资源
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
