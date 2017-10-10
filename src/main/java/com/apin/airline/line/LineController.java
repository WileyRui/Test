package com.apin.airline.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线管理服务控制器
 */
@RestController
@RequestMapping("/lineapi")
public class LineController {
    @Autowired
    private LineService service;
}
