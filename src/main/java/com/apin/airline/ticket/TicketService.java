package com.apin.airline.ticket;

import com.apin.airline.flight.dto.DealerListDto;
import com.apin.airline.ticket.dto.*;
import com.apin.util.pojo.Reply;

import java.text.ParseException;

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
     * 分销商逍客接口
     * @param dto
     * @return
     */
    Reply sale(DealerListDto dto);

    /**
     * 分配库存
     *
     * @param deal
     * @return
     */
    Reply dealStock(Deal deal);

    /**
     * 分销商添加乘机人接口
     * @param dto
     * @return
     */
    Reply addPassenger(PassengerInfoDto dto);
    /**
     * 分销商导入乘机人接口
     * @param dto
     * @return
     */
    Reply importPassenger(ImportPassengerDto dto);
    /**
     * 分销商乘机人列表接口
     * @param dto
     * @return
     */
    Reply passengerInfo(DealerListDto dto);

    /**
     * 批量更新航线所有者
     * @param dto
     * @return
     */
    Reply updateSeatsOwner(UpdateSeatsOwnerDto dto);

    /**
     * 根据id修改航线所有者
     * @param updateAndLogsResponse
     * @return
     */
    Reply updateSeatsOwnerById(UpdateAndLogsResponse updateAndLogsResponse);

    /**
     * 查询可回收位数
     * @param getEnableSeatDto
     * @return
     */
    Reply getEnableSeat(GetEnableSeatDto getEnableSeatDto);

    /**
     * 查询当前日期可以回收的航线
     * @param today
     * @return
     */
    Reply queryFlightsToRecovery(String today) throws Exception;

    /**
     *  包机商乘机人列表接口
     * @param dto
     * @return
     */
    Reply merchantPassengerInfo(PassengerParam dto);
}
