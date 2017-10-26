package com.apin.airline.flight;

import com.apin.airline.flight.dto.*;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * arm指定航线日历查看
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
     * arm首页日历查看所有航线的库存/价格信息
     *
     * @param request
     * @return Reply
     * 正常：返回接口调用成功,通过data返回日历相关信息
     */
    @PostMapping(value = "/v1.0/homeCalendarInfo/query")
    public Reply homeCalendarInfo(@RequestBody HomeCalendarInfoQueryRequest request) throws Exception {
        return flightService.homeCalendarInfo(request);
    }
    /**
     * 首页日历，查询某天的所有的航班信息
     *
     * @param request 查询请求参数
     * @return 返回某天的在飞航班信息
     */
    @PostMapping(value = "/v1.0/homeAirlineInfo/query")
    public Reply homeCalendarInfo(@RequestBody HomeAirlineQueryRequest request) throws Exception {
        return flightService.homeAirlineInfo(request);
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
     * arm价格批量导入更新
     *
     * @param priceTemplateBeanList
     * @return
     */
    @PostMapping(value = "/v1.0/flights/batchPrice/update")
    public Reply priceImport(@RequestBody List<PriceTemplateBean> priceTemplateBeanList) {
        return flightService.priceImport(priceTemplateBeanList);
    }
    /**
     * arm航班列表查询
     *
     * @param listDto
     * @return
     */
    @PostMapping(value = "/v1.0/flightList/query")
    public Reply flightList(@RequestHeader("Authorization") String token, @RequestBody DealerListDto listDto) {
        return flightService.flightList(listDto);
    }

    /**
     * arm指定分销商库存日历查询
     *
     * @param listDto
     * @return
     */
    @PostMapping(value = "/v1.0/stockList/query")
    public Reply stockList(@RequestHeader("Authorization") String token,@RequestBody DealerListDto listDto) {
        return flightService.stockList(listDto);
    }

    /**
     * arm包机商航司合作线路数接口
     * @param listBo
     * @return
     */
    @PostMapping(value = "/v1.0/count/query")
    public Reply count(@RequestBody DealerListDto listBo) {
        if (StringUtils.isBlank(listBo.getAccountId())) {
            return ReplyHelper.invalidParam("包机商id不能为空");
        }

        return flightService.count(listBo);
    }

    /**
     * arm包机商航司合作线路航空公司名字接口
     * @return
     */
    @PostMapping(value = "/v1.0/compName/query")
    public Reply compName() {
        return flightService.compName();
    }

    /**
     * 分销商判断有无数据接口
     * @param token
     * @param dto
     * @return
     */
    @PostMapping("/v1.0/hasList/query")
    public Reply queryHasListInfo(@RequestHeader(value="Authorization") String token, @RequestBody HasListDto dto){
        return flightService.hasList(dto);

    }

    /**
     * obt条件查询航班信息
     * @param searchDto 城市对 航班类型集合
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/flight/query")
    public Reply searchFlight(@RequestBody SearchDto searchDto) throws Exception {
        return flightService.searchFlight(searchDto);
    }

    /**
     * obt根据单城市或
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
     * obt查询月份
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
     * obt查询每日详情
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
     * obt根据城市对与出发日期结束日期查询航班详情
     *
     * @param cityList
     * @return Reply
     * 正常：返回接口调用成功,返回数据
     */
    @PostMapping(value = "/v1.0/retFlight/month/query")
    public Reply searchMonth(@RequestBody CityList cityList) throws Exception {
        return flightService.searchFlightMonth(cityList);
    }
    /**
     * obt根据城市对与出发日期结束日期查询航班详情
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
     * obt根据单城市或
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
