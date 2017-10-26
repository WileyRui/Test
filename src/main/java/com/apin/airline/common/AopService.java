package com.apin.airline.common;

import com.apin.airline.common.entity.Log;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.LogMapper;
import com.apin.airline.common.mapper.QueryMapper;
import com.apin.airline.flight.dto.ApinCalendarElement;
import com.apin.airline.ticket.dto.Stock;
import com.apin.airline.ticket.dto.Ticket;
import com.apin.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Author:huanglei
 * Description:切面方法
 * Date:2017/10/16
 */
@Component
public class AopService {
    @Autowired
    private AirlineMapper airlineMapper;
    @Autowired
    private QueryMapper queryMapper;
    @Autowired
    private LogMapper logMapper;
    public Boolean insertLog(Log log){
        log.setId(Generator.uuid());
        switch (log.getEventCode()){
            case "1001":{log.setEventName("强制收位");break;}
            case "1002":{log.setEventName("手动收位");break;}
            case "1003":{log.setEventName("分配库存");break;}
            case "1004":{log.setEventName("销客");break;}
            case "1005":{log.setEventName("修改库存");break;}
            case "1006":{log.setEventName("修改价格");break;}
            case "1007":{log.setEventName("航线过期");break;}
            default:break;
        }
        log.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        return logMapper.insert(log)>0;
    }

    public ApinCalendarElement findStock(Stock stock) {
        return queryMapper.findStock(stock);
    }

    /**
     * seat表修改状态
     * @param seat
     * @param oldSeat
     * @return
     */
    public Integer modifySeatStatus(Ticket seat, Ticket oldSeat) {
        return queryMapper.modifySeatStatus(seat,oldSeat);
    }

    /**
     * 获取包机商或分销商的已售、未售及库存值(多航班)
     * @param stock
     * @return
     */
    public List<ApinCalendarElement> findStockList(Stock stock) {
        return queryMapper.findStockList(stock);
    }
}
