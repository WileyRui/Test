package com.apin.airline.line;

import com.apin.airline.common.entity.FlightInfo;
import com.apin.airline.common.entity.Line;
import com.apin.util.pojo.Reply;

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
     * @return
     */
    Reply lineList(Line line);

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
     * @param line
     * @return
     */
    Reply queryFlightInfo(FlightInfo info) throws InvocationTargetException, IllegalAccessException;


    /**
     * 维护航班
     *
     * @param flightInfo
     * @return
     */
    Reply addFlightInfo(FlightInfo flightInfo);

    /**
     * 更新航班
     *
     * @param info
     * @return
     */
    Reply updateFlightInfo(FlightInfo info) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取最新的航线信息
     * @return
     */
    Reply newLineInfo();
}
