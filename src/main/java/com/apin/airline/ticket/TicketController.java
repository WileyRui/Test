package com.apin.airline.ticket;

import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Deal;
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
 * @remark 机票服务控制器
 */
@RestController
@RequestMapping("/ticketapi")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    /**
     * 日历查看
     * @param calendarInfo
     * @return Reply
     * 正常：返回接口调用成功,通过data返回日历相关信息
     */
    @PostMapping(value = "/airlineInfo")
    public Reply airlineInfo(@RequestBody CalendarInfo calendarInfo) throws UnsupportedEncodingException {
        return ticketService.airlineInfo(calendarInfo);
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
        return ticketService.modifyStock(stock);
    }
    /**
     * 库存日历内销客
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,座位状态修改
     * 异常：剩余座位不足
     */
    @PostMapping(value = "/saleStock")
    public Reply saleStock(@RequestBody Stock stock) throws UnsupportedEncodingException {
        return ticketService.saleStock(stock);
    }
    /**
     * 价格日历修改价格
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,价格修改成功
     */
    @PostMapping(value = "/modifyPrice")
    public Reply modifyPrice(@RequestBody Stock stock) throws UnsupportedEncodingException {
        return ticketService.saleStock(stock);
    }
    /**
     * 分配库存
     * @param deal
     * @return Reply
     * 正常：返回接口调用成功,座位状态修改
     * 异常：可分配库存不足
     */
    @PostMapping(value = "/dealStock")
    public Reply dealStock(@RequestBody Deal deal) throws UnsupportedEncodingException {
        return ticketService.dealStock(deal);
    }

}
