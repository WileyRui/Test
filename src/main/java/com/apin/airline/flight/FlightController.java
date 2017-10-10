package com.apin.airline.flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航班管理服务控制器
 */
@RestController
@RequestMapping("/flightapi")
public class FlightController {
    @Autowired
    private FlightService service;
}
