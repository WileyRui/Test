package com.apin.airline.line;

import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.entity.Line;
import com.apin.airline.common.entity.LineDetail;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线管理服务控制器
 */
@RestController
@RequestMapping("/lineapi")
public class LineController {
    @Autowired
    LineService service;

    /**
     * 增加航线
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/create")
    public Reply addLine(@RequestHeader("Authorization") String token, @RequestBody Line line) {
        return service.addLine(token, line);
    }

    /**
     * 编辑航线
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/edit")
    public Reply editLine(@RequestHeader("Authorization") String token, @RequestBody Line line) {
        return service.editLine(token, line);
    }

    /**
     * 删除航线
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/delete")
    public Reply delLine(@RequestHeader("Authorization") String token, @RequestBody Line line) {
        return service.delLine(token, line);
    }

    /**
     * 航线列表/查询
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/list")
    public Reply lineList(@RequestBody Line line, @RequestHeader("Authorization") String token) {
        return service.lineList(line, token);
    }

    /**
     * 航线详情
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/detail/query")
    public Reply lineInfo(@RequestHeader("Authorization") String token, @RequestBody Line line) {
        return service.lineInfo(token, line);
    }

    /**
     * 航线上下架
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/status/update")
    public Reply upOrDown(@RequestHeader("Authorization") String token, @RequestBody Line line) {
        return service.upOrDown(token, line);
    }

    /**
     * 航班信息查询
     *
     * @param info
     * @return
     */
    @PostMapping("/v1.0/flightInfo/query")
    public Reply queryFlightInfo(@RequestBody LineDetail info) throws InvocationTargetException, IllegalAccessException, IOException {
        return service.queryFlightInfo(info);
    }

    /**
     * 航班信息维护
     *
     * @param lineDetail
     * @return
     */
    @PostMapping("/v1.0/flightInfo/create")
    public Reply addFlightInfo(@RequestBody LineDetail lineDetail) {
        return service.addFlightInfo(lineDetail);
    }

    /**
     * 航班信息更新
     *
     * @param info
     * @return
     */
    @PostMapping("/v1.0/flightInfo/update")
    public Reply updateFlightInfo(@RequestBody LineDetail info) throws InvocationTargetException, IllegalAccessException, IOException {
        return service.updateFlightInfo(info);
    }

    /**
     * 获取最新的航线信息
     *
     * @return
     */
    @PostMapping("/v1.0/lines/new")
    public Reply newLineInfo(@RequestBody Line line) {
        return service.newLineInfo(line);
    }

    /**
     * 查询航线操作日志
     *
     * @param id
     * @return
     */
    @PostMapping("/v1.0/logs")
    public Reply getAirlinesLogs(@RequestBody String id) {
        return service.listLogs(id);
    }

    /**
     * 查询航线航班信息
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/info/query")
    public Reply getAirlineInfo(@RequestBody Line line) {
        return service.getAirlineInfo(line);
    }

    /**
     * 更新过期航线
     *
     * @param token
     * @param line
     * @return
     */
    @PostMapping(value = "/v1.0/flight/updateExpireFlights")
    public Reply updateExpireFlights(@RequestHeader("Authorization") String token, @RequestBody Line line) {
        return service.updateExpireFlights(token, line);
    }

    /**
     * 根据航班查询详细信息
     *
     * @param line
     * @return
     */
    @PostMapping(value = "/v1.0/flight/getLine")
    public Reply getMbsAirlineByFlightId(@RequestBody Line line) {
        return service.getLineByFlightId(line);
    }

}
