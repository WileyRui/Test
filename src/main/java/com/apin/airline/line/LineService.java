package com.apin.airline.line;

import com.apin.util.pojo.Reply;
import org.springframework.stereotype.Service;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线管理服务接口
 */
@Service
public interface LineService {

    /**
     * 新增航线
     *
     * @param lineBo
     * @return
     */
    Reply addLine(LineBo lineBo);

    /**
     * 编辑航线
     *
     * @param lineBo
     * @return
     */
    Reply editLine(LineBo lineBo);

    /**
     * 删除航线
     *
     * @param lineBo
     * @return
     */
    Reply delLine(LineBo lineBo);

    /**
     * 航线列表/查询
     *
     * @param lineBo
     * @return
     */
    Reply lineList(LineBo lineBo);

    /**
     * 航线详情
     *
     * @param lineBo
     * @return
     */
    Reply lineInfo(LineBo lineBo);

    /**
     * 航线上下架
     *
     * @param lineBo
     * @return
     */
    Reply upOrDown(LineBo lineBo);

    /**
     * 航班查询
     * @param lineBo
     * @return
     */
    Reply queryFlightInfo(LineBo lineBo);


    /**
     * 维护航班
     * @param lineBo
     * @return
     */
    Reply addFlightInfo(LineBo lineBo);

    /**
     * 更新航班
     * @param lineBo
     * @return
     */
    Reply updateFlightInfo(LineBo lineBo);
}
