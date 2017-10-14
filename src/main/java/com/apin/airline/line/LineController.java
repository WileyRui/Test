package com.apin.airline.line;

import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.line.dto.LineBo;
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
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/create")
    public Reply addLine(@RequestHeader("accessToken") String token, @RequestBody LineBo lineBo) {
        return service.addLine(token, lineBo);
    }

    /**
     * 编辑航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/edit")
    public Reply editLine(@RequestBody LineBo lineBo) {
        return service.editLine(lineBo);
    }

    /**
     * 删除航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/delete")
    public Reply delLine(@RequestBody LineBo lineBo) {
        return service.delLine(lineBo);
    }

    /**
     * 航线列表/查询
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/list")
    public Reply lineList(@RequestBody LineBo lineBo) {
        return service.lineList(lineBo);
    }

    /**
     * 航线详情
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/detail/query")
    public Reply lineInfo(@RequestBody LineBo lineBo) {
        return service.lineInfo(lineBo);
    }

    /**
     * 航线上下架
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/status/update")
    public Reply upOrDown(@RequestBody LineBo lineBo) {
        return service.upOrDown(lineBo);
    }

    /**
     * 航班信息查询
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/flightInfo/query")
    public Reply queryFlightInfo(@RequestBody LineBo lineBo) throws InvocationTargetException, IllegalAccessException {
        return service.queryFlightInfo(lineBo);
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
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/flightInfo/update")
    public Reply updateFlightInfo(@RequestBody LineBo lineBo) throws InvocationTargetException, IllegalAccessException {
        return service.updateFlightInfo(lineBo);
    }
}
