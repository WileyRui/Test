package com.apin.airline.line;

import com.apin.airline.common.entity.Line;
import com.apin.airline.common.entity.LineDetail;
import com.apin.util.pojo.Reply;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 宣炳刚
 * @date 2017/10/10
 * @remark 航线管理服务接口
 */
public interface LineService {

    /**
     * 新增航线
     *
     * @param line
     * @return
     */
    Reply addLine(String token, Line line);

    /**
     * 编辑航线
     *
     * @param line
     * @return
     */
    Reply editLine(String token, Line line);

    /**
     * 删除航线
     *
     * @param line
     * @return
     */
    Reply delLine(String token, Line line);

    /**
     * 航线列表/查询
     *
     * @param line
     * @param token
     * @return
     */
    Reply lineList(Line line,String token);

    /**
     * 航线详情
     *
     * @param line
     * @return
     */
    Reply lineInfo(String token,Line line);

    /**
     * 航线上下架
     *
     * @param line
     * @return
     */
    Reply upOrDown(String token,Line line);

    /**
     * 航班查询
     *
     * @param info
     * @return
     */
    Reply queryFlightInfo(LineDetail info) throws InvocationTargetException, IllegalAccessException, IOException;


    /**
     * 维护航班
     *
     * @param lineDetail
     * @return
     */
    Reply addFlightInfo(LineDetail lineDetail);

    /**
     * 更新航班
     *
     * @param info
     * @return
     */
    Reply updateFlightInfo(LineDetail info) throws InvocationTargetException, IllegalAccessException, IOException;

    /**
     * 获取最新的航线信息
     * @return
     */
    Reply newLineInfo(Line line);

    /**
     * 查询航线操作日志
     * @param airlineId
     * @return
     */
    Reply listLogs(String airlineId);

    /**
     * 查询航线信息
     * @param line
     * @return
     */
    public Reply getAirlineInfo(Line line);
}
