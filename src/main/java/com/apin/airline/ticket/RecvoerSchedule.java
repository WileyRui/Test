package com.apin.airline.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 黄磊
 * @date 2017/10/13
 * @remark 定时强制回收
 */
@Component
public class RecvoerSchedule {

    private Logger LOG = LoggerFactory.getLogger(RecvoerSchedule.class);

    /**
     * 每天0点触发一次
     */
    @Scheduled(cron = "${recoverySeat}")
    public void autoRecoverySeat() {

    }
}
