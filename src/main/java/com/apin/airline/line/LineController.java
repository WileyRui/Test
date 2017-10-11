package com.apin.airline.line;

import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线管理服务控制器
 */
@RestController
@RequestMapping("/lineapi/")
public class LineController {
    @Autowired
    private LineService service;

    /**
     * 增加航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("addLine")
    public Reply addLine(LineBo lineBo) {
        return service.addLine(lineBo);
    }

    /**
     * 编辑航线
     *
     * @param lineBo
     * @return
     */
    @PostMapping("editLine")
    public Reply editLine(LineBo lineBo) {
        return service.editLine(lineBo);
    }

    /**
     * 删除航线
     *
     * @param lineId
     * @param accountId
     * @return
     */
    @GetMapping("delLine")
    public Reply delLine(String lineId, String accountId) {
        if (StringUtils.isBlank(lineId) || StringUtils.isBlank(accountId)) {
            return ReplyHelper.fail("参数为空");
        }
        return service.delLine(lineId, accountId);
    }

    /**
     * 航线列表/查询
     *
     * @param lineBo
     * @return
     */
    @PostMapping("lineList")
    public Reply lineList(LineBo lineBo) {
        return service.lineList(lineBo);
    }

    /**
     * 航线详情
     *
     * @param lineId
     * @param accountId
     * @return
     */
    @GetMapping("lineInfo")
    public Reply lineInfo(String lineId, String accountId) {
        if (StringUtils.isBlank(lineId) || StringUtils.isBlank(accountId)) {
            return ReplyHelper.fail("参数为空");
        }
        return service.lineInfo(lineId, accountId);
    }

    /**
     * 航线上下架
     *
     * @param lineId
     * @param accountId
     * @return
     */
    @GetMapping("upOrDown")
    public Reply upOrDown(String lineId, String accountId) {
        if (StringUtils.isBlank(lineId) || StringUtils.isBlank(accountId)) {
            return ReplyHelper.fail("参数为空");
        }
        return service.upOrDown(lineId, accountId);
    }
}
