package com.apin.airline.flight;

import com.apin.airline.flight.dto.CityList;
import com.apin.airline.flight.dto.PriceTemplateBean;
import com.apin.airline.flight.dto.SearchDto;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航班管理服务控制器
 */
@RestController
@RequestMapping("/flightapi")
public class FlightController {
    @Autowired
    private FlightService flightService;

    /**
     * arm日历查看
     *
     * @param calendarInfo
     * @return Reply
     * 正常：返回接口调用成功,通过data返回日历相关信息
     */
    @PostMapping(value = "/v1.0/flights/list")
    public Reply airlineInfo(@RequestBody CalendarInfo calendarInfo) throws Exception {
        return flightService.airlineInfo(calendarInfo);
    }

    /**
     * arm库存日历内修改库存
     *
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,库存数量修改，座位数修改
     * 异常：已分配库存，无法修改
     */
    @PostMapping(value = "/v1.0/flights/seat/update")
    public Reply modifyStock(@RequestBody Stock stock) throws Exception {
        return flightService.modifyStock(stock);
    }

    /**
     * arm价格日历修改价格
     *
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,价格修改成功
     */
    @PostMapping(value = "/v1.0/flights/price/update")
    public Reply modifyPrice(@RequestBody Stock stock) throws Exception {
        return flightService.modifyPrice(stock);
    }

    /**
     * 价格批量导入更新
     *
     * @param priceTemplateBeanList
     * @return
     */
    @PostMapping(value = "/v1.0/flights/batchPrice/update")
    public Reply importPassenger(@RequestBody List<PriceTemplateBean> priceTemplateBeanList) {
        return flightService.priceImport(priceTemplateBeanList);
    }

    /**
     * 根据城市对查询信息
     *
     * @param searchDto
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flight/query")
    public Reply searchFlight(@RequestBody SearchDto searchDto) throws Exception {
        return flightService.searchFlight(searchDto);
    }

    /**
     * 根据单城市或
     *
     * @param cityList
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flights/query")
    public Reply searchFlights(@RequestBody CityList cityList) throws Exception {
        return flightService.searchFlights(cityList);
    }
    /**
     * 查询月份
     *
     * @param cityList
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flightDetail/month/query")
    public Reply monthQuery(@RequestBody CityList cityList) throws Exception {
        return flightService.monthQuery(cityList);
    }
    /**
     * 查询每日详情
     *
     * @param cityList
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flightDetail/day/query")
    public Reply dayQuery(@RequestBody CityList cityList) throws Exception {
        return flightService.dayQuery(cityList);
    }


    /**
     * 根据城市对与出发日期结束日期查询航班详情
     *
     * @param cityList
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flightDetail/query")
    public Reply searchFlightDetail(@RequestBody CityList cityList) throws Exception {
        return flightService.searchFlightDetail(cityList);
    }

    /**
     * 根据条件查询
     *
     * @param cityList
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flightDetail/list")
    public Reply searchFlightList(@RequestBody CityList cityList) throws Exception {
        return flightService.searchFlightList(cityList);
    }


}
