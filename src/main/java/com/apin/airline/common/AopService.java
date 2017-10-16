package com.apin.airline.common;

import com.apin.airline.common.entity.Log;
import com.apin.util.Generator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Author:huanglei
 * Description:切面方法
 * Date:2017/10/16
 */
@Component
public class AopService {
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
        }
        log.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//        return logMapper.insert(log)>0;
        return false;
    }

}
