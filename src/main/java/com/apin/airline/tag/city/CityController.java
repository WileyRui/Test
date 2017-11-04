package com.apin.airline.tag.city;

import com.apin.util.JsonUtils;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhaowei on 2017/10/16.
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping(value = "/v1.0/listCitys")
    public Reply listCitys(@RequestHeader String Authorization, @RequestBody CityDTO cityDTO) {
        return cityService.listCitys(Authorization,cityDTO);
    }
    @PostMapping(value = "/v1.0/setCityTag")
    public Reply setCityTag(@RequestHeader String Authorization, @RequestBody CityDTO cityDTO){
        AccessToken atObject = JsonUtils.toAccessToken(Authorization);
        return cityService.setCityTag(atObject,cityDTO);
    }
    @PostMapping(value = "/v1.0/setCityImg")
    public Reply setCityImg(@RequestHeader String Authorization, @RequestBody CityDTO cityDTO){
        return cityService.setCityImg(Authorization,cityDTO);
    }
}
