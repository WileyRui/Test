package com.apin.airline.tag.airway;

import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhaowei on 2017/10/16.
 */
@RestController
@RequestMapping("/airway")
public class AirWayController {
    @Autowired
    private AirWayService airWayService;

    /**
     * 更新城市logo图片
     * @param Authorization
     * @param airWayDTO
     * @return
     */
    @PostMapping(value = "/v1.0/updateAirWay")
    public Reply updateAirWay(@RequestHeader String Authorization, @RequestBody AirWayDTO airWayDTO){
       return airWayService.updateAirWay(Authorization,airWayDTO);
    }

    /**
     *  航司列表
     * @param Authorization
     * @param airWayDTO
     * @return
     */
    @PostMapping(value = "/v1.0/listAirWays")
    public Reply listAirWays(@RequestHeader String Authorization, @RequestBody AirWayDTO airWayDTO){
        return airWayService.listAirWays(Authorization,airWayDTO);
    }
}
