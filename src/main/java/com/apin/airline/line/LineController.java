package com.apin.airline.line;

import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.common.entity.Line;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Reply addLine(@RequestHeader("accessToken") String token, @RequestBody Line line) {
        return service.addLine(token, line);
    }

    /**
     * 编辑航线
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/edit")
    public Reply editLine(@RequestHeader("accessToken") String token, @RequestBody Line line) {
        return service.editLine(token, line);
    }

    /**
     * 删除航线
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/delete")
    public Reply delLine(@RequestHeader("accessToken") String token, @RequestBody Line line) {
        return service.delLine(token, line);
    }

    /**
     * 航线列表/查询
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/list")
    public Reply lineList(@RequestBody Line line) {
        return service.lineList(line);
    }

    /**
     * 航线详情
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/detail/query")
    public Reply lineInfo(@RequestHeader("accessToken") String token,@RequestBody Line line) {
        return service.lineInfo(token,line);
    }

    /**
     * 航线上下架
     *
     * @param line
     * @return
     */
    @PostMapping("/v1.0/lines/status/update")
    public Reply upOrDown(@RequestHeader("accessToken") String token,@RequestBody Line line) {
        return service.upOrDown(token,line);
    }

    /**
     * 航班信息查询
     *
     * @param info
     * @return
     */
    @PostMapping("/v1.0/flightInfo/query")
    public Reply queryFlightInfo(@RequestBody FlightInfo info) throws InvocationTargetException, IllegalAccessException {
        return service.queryFlightInfo(info);
    }

    /**
     * 航班信息维护
     *
     * @param flightInfo
     * @return
     */
    @PostMapping("/v1.0/flightInfo/create")
    public Reply addFlightInfo(@RequestBody FlightInfo flightInfo) {
        return service.addFlightInfo(flightInfo);
    }

    /**
     * 航班信息更新
     *
     * @param info
     * @return
     */
    @PostMapping("/v1.0/flightInfo/update")
    public Reply updateFlightInfo(@RequestBody FlightInfo info) throws InvocationTargetException, IllegalAccessException {
        return service.updateFlightInfo(info);
    }

    /**
     *获取最新的航线信息
     * @return
     */
    @PostMapping("/v1.0/lines/new")
    public Reply newLineInfo(){
        return service.newLineInfo();
    }
}
