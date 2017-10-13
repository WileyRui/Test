package com.apin.airline.flight;

import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

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
     * 日历查看
     * @param calendarInfo
     * @return Reply
     * 正常：返回接口调用成功,通过data返回日历相关信息
     */
    @PostMapping(value = "/airlineInfo")
    public Reply airlineInfo(@RequestBody CalendarInfo calendarInfo) throws UnsupportedEncodingException {
        return flightService.airlineInfo(calendarInfo);
    }
    /**
     * 库存日历内修改库存
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,库存数量修改，座位数修改
     * 异常：已分配库存，无法修改
     */
    @PostMapping(value = "/modifyStock")
    public Reply modifyStock(@RequestBody Stock stock) throws UnsupportedEncodingException {
        return flightService.modifyStock(stock);
    }
    /**
     * 价格日历修改价格
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,价格修改成功
     */
    @PostMapping(value = "/modifyPrice")
    public Reply modifyPrice(@RequestBody Stock stock) throws UnsupportedEncodingException {
        return flightService.modifyPrice(stock);
    }
}
