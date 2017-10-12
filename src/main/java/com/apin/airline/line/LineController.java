package com.apin.airline.line;

import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 增加航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/create")
    public Reply addLine(LineBo lineBo) {
        return service.addLine(lineBo);
    }

    /**
     * 编辑航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/edit")
    public Reply editLine(LineBo lineBo) {
        return service.editLine(lineBo);
    }

    /**
     * 删除航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/delete")
    public Reply delLine(LineBo lineBo) {
        return service.delLine(lineBo);
    }

    /**
     * 航线列表/查询
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/list")
    public Reply lineList(LineBo lineBo) {
        return service.lineList(lineBo);
    }

    /**
     * 航线详情
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/detail/query")
    public Reply lineInfo(LineBo lineBo) {
        return service.lineInfo(lineBo);
    }

    /**
     * 航线上下架
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/lines/status/update")
    public Reply upOrDown(LineBo lineBo) {
        return service.upOrDown(lineBo);
    }

    /**
     * 航班信息查询
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/flightInfo/query")
    public Reply queryFlightInfo(LineBo lineBo) {
        return service.queryFlightInfo(lineBo);
    }

    /**
     * 航班信息维护
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/flightInfo/create")
    public Reply addFlightInfo(LineBo lineBo) {
        return service.addFlightInfo(lineBo);
    }

    /**
     * 航班信息更新
     *
     * @param lineBo
     * @return
     */
    @PostMapping("/v1.0/flightInfo/update")
    public Reply updateFlightInfo(LineBo lineBo) {
        return service.updateFlightInfo(lineBo);
    }
}
