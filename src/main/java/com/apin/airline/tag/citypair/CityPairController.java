package com.apin.airline.tag.citypair;

import com.apin.util.JsonUtils;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhaowei on 2017/10/14.
 */
@RestController
@RequestMapping("/citypair")
public class CityPairController {
    @Autowired
    private CityPairService cityPairService;

    /**
     * 查询城市对列表
     * @param cityPairDTO
     * @return
     */
    @PostMapping(value = "/v1.0/listCityPairs")
    public Reply listCityPairs(@RequestBody CityPairDTO cityPairDTO){
        return cityPairService.listCityPairs(cityPairDTO);
    }

    /**
     * 新增城市对
     * @param Authorization
     * @param cityPairDTO
     * @return
     */
    @PostMapping(value = "/v1.0/insertCityPair")
    public Reply insertCityPair(@RequestHeader String Authorization, @RequestBody CityPairDTO cityPairDTO){
        return cityPairService.insertCityPair(Authorization,cityPairDTO);
    }

    /**
     * 删除城市对
     * @param cityPairDTO
     * @return
     */
    @PostMapping(value = "/v1.0/deleteCityPair")
    public Reply deleteCityPair(@RequestBody CityPairDTO cityPairDTO){
        return cityPairService.deleteCityPair(cityPairDTO.getId());
    }

    /**
     * 设成精品
     * @param Authorization
     * @param cityPairDTO
     * @return
     */
    @PostMapping(value = "/v1.0/setExtract")
    public Reply setExtract(@RequestHeader String Authorization, @RequestBody CityPairDTO cityPairDTO){
        AccessToken atObject = JsonUtils.toAccessToken(Authorization);
        return cityPairService.setExtract(atObject,cityPairDTO.getId());
    }

    /**
     * 取消精品
     * @param cityPairDTO
     * @return
     */
    @PostMapping(value = "/v1.0/cancelExtract")
    public Reply cancelExtract(@RequestBody CityPairDTO cityPairDTO){
        return cityPairService.cancelExtract(cityPairDTO.getId());
    }

//    public static void main(String[] args) {
//        String tt = "";
//        AccessToken a = JsonUtils.toAccessToken(tt);
//        System.out.println(a.getUserId());
//    }
}
