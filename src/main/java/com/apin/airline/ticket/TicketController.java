package com.apin.airline.ticket;

import com.apin.airline.flight.dto.DealerListDto;
import com.apin.airline.ticket.dto.*;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 机票服务控制器
 */
@RestController
@RequestMapping("/ticketapi")
public class TicketController {
    @Autowired
    private TicketService service;


    /**
     * 库存日历内销客
     *
     * @param stock
     * @return Reply
     * 正常：返回接口调用成功,座位状态修改
     * 异常：剩余座位不足s
     */
    @PostMapping(value = "/v1.0/seats/status/update")
    public Reply saleStock(@RequestBody Stock stock) throws Exception {
        return service.saleStock(stock);
    }
    /**
     * 分销商逍客接口
     * @param token
     * @param dto
     * @return
     */
    @PostMapping("/v1.0/sale/update")
    public Reply updateSale(@RequestHeader("Authorization") String token, @RequestBody DealerListDto dto){
        return service.sale(dto);
    }

    /**
     * 分配库存
     *
     * @param deal
     * @return Reply
     * 正常：返回接口调用成功,座位状态修改
     * 异常：可分配库存不足
     */
    @PostMapping(value = "/v1.0/seats/owner/update")
    public Reply dealStock(@RequestBody Deal deal) throws Exception {
        return service.dealStock(deal);
    }

    /**
     * 手动收位
     *
     * @param deal
     * @return Reply
     * 正常：返回接口调用成功,座位状态修改
     * 异常：可分配库存不足
     */
    @PostMapping(value = "/v1.0/seats/recover/update")
    public Reply handRecover(@RequestBody Deal deal) throws Exception {
        return service.dealStock(deal);
    }
    /**
     * 分销商乘机人列表接口
     * @param token
     * @param dto
     * @return
     */
    @PostMapping(value = "/v1.0/passengerList/query")
    public Reply passengerList(@RequestHeader(value="Authorization") String token, @RequestBody DealerListDto dto) throws Exception {
        return service.passengerInfo(dto);
    }
    /**
     * 包机商乘机人列表接口
     * @param token
     * @param dto
     * @return
     */
    @PostMapping(value = "/v1.0/merchantPassengerList/query")
    public Reply merchantPassengerList(@RequestHeader(value="Authorization") String token, @RequestBody PassengerParam dto) throws Exception {
        return service.merchantPassengerInfo(dto);
    }
    /**
     * 分销商添加乘机人接口
     * @param token
     * @param dto
     * @return
     */
    @PostMapping(value = "/v1.0/passenger/create")
    public Reply handRecover(@RequestHeader(value="Authorization") String token, @RequestBody PassengerInfoDto dto) throws Exception {
        return service.addPassenger(dto);
    }
    /**
     * 分销商导入乘机人接口
     * @param token
     * @param dto
     * @return
     */
    @PostMapping(value = "/v1.0/importPassenger/update")
    public Reply handRecover(@RequestHeader(value="Authorization") String token, @RequestBody ImportPassengerDto dto) throws Exception {
        return service.importPassenger(dto);
    }

    /**
     * 批量更新航线所有者
     * @param
     * @return
     */
    @PostMapping(value = "/v1.0/seatsOwner/update")
    public Reply updateSeatsOwner(@RequestBody UpdateSeatsOwnerDto dto) throws Exception {
        return service.updateSeatsOwner(dto);
    }

    /**
     * 根据id修改航线所有者
     * @param updateAndLogsResponse
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/v1.0/seatsOwnerById/update")
    public Reply updateSeatsOwnerById(@RequestBody UpdateAndLogsResponse updateAndLogsResponse) throws Exception {
        return service.updateSeatsOwnerById(updateAndLogsResponse);
    }

    /**
     * 查询可回收位数
     * @param getEnableSeatDto
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/v1.0/enableSeat/query")
    public Reply getEnableSeat(@RequestBody GetEnableSeatDto getEnableSeatDto) throws Exception {

        return service.getEnableSeat(getEnableSeatDto);
    }

    /**
     * 查询当前日期可以回收的航线
     * @param today
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/v1.0/queryFlightsToRecovery/{today}")
    public Reply queryFlightsToRecovery(@PathVariable String today) throws Exception {
        return service.queryFlightsToRecovery(today);
    }

}
