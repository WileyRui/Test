package com.apin.airline.base;

import com.apin.airline.common.entity.Airport;
import com.apin.airline.common.entity.Airway;
import com.apin.airline.common.entity.City;
import com.apin.airline.common.entity.Country;
import com.apin.airline.flight.dto.CityStr;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/15
 * @remark 基础数据服务控制器
 */
@RestController
@RequestMapping("/baseapi")
public class BaseController {
    @Autowired
    private BaseService service;

    /**
     * 查询国家基础数据(分页,按国家代码排序)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/countries/list")
    public Reply getCountries(@RequestHeader("Authorization") String token, @RequestBody Country country) throws Exception {
        return service.getCountries(token, country);
    }

    /**
     * 新增国家基础数据
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/countries/create")
    public Reply addCountry(@RequestHeader("Authorization") String token, @RequestBody Country country) throws Exception {
        return service.addCountry(token, country);
    }

    /**
     * 删除国家基础数据(逻辑删除)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/countries/delete")
    public Reply deleteCountry(@RequestHeader("Authorization") String token, @RequestBody Country country) throws Exception {
        return service.deleteCountry(token, country);
    }

    /**
     * 编辑国家基础数据
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/countries/update")
    public Reply updateCountry(@RequestHeader("Authorization") String token, @RequestBody Country country) throws Exception {
        return service.updateCountry(token, country);
    }

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/cities/list")
    public Reply getCities(@RequestHeader("Authorization") String token, @RequestBody City city) throws Exception {
        return service.getCities(token, city);
    }

    /**
     * 查询城市基础数据(按拼音排序)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/cities/list/key")
    public Reply getCitiesByKey(@RequestHeader("Authorization") String token, @RequestBody CityStr city) throws Exception {
        return service.getCitiesByKey(token, city.getStr());
    }

    /**
     * 查询热门城市数据(按拼音排序)
     *
     * @param ids  单索引词
     * @param ids 单索引词
     * @return Reply
     */
    @PostMapping("/v1.0/cities/list/ids")
    public Reply getCities(@RequestBody List<String> ids) throws Exception {
        return service.getCitiesByIds(ids);
    }

    /**
     * 新增城市基础数据
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/cities/create")
    public Reply addCity(@RequestHeader("Authorization") String token, @RequestBody City city) throws Exception {
        return service.addCity(token, city);
    }

    /**
     * 删除城市基础数据(逻辑删除)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/cities/delete")
    public Reply deleteCity(@RequestHeader("Authorization") String token, @RequestBody City city) throws Exception {
        return service.deleteCity(token, city);
    }

    /**
     * 编辑城市基础数据
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/cities/update")
    public Reply updateCity(@RequestHeader("Authorization") String token, @RequestBody City city) throws Exception {
        return service.updateCity(token, city);
    }

    /**
     * 查询机场基础数据(分页,按三字码排序)
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airports/list")
    public Reply getAirports(@RequestHeader("Authorization") String token, @RequestBody Airport airport) throws Exception {
        return service.getAirports(token, airport);
    }

    /**
     * 新增机场基础数据
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airports/create")
    public Reply addAirport(@RequestHeader("Authorization") String token, @RequestBody Airport airport) throws Exception {
        return service.addAirport(token, airport);
    }

    /**
     * 删除机场基础数据(逻辑删除)
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airports/delete")
    public Reply deleteAirport(@RequestHeader("Authorization") String token, @RequestBody Airport airport) throws Exception {
        return service.deleteAirport(token, airport);
    }

    /**
     * 编辑机场基础数据
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airports/update")
    public Reply updateAirport(@RequestHeader("Authorization") String token, @RequestBody Airport airport) throws Exception {
        return service.updateAirport(token, airport);
    }

    /**
     * 查询航司基础数据(分页,按航司代码排序)
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airwaies/list")
    public Reply getAirwaies(@RequestHeader("Authorization") String token, @RequestBody Airway airway) throws Exception {
        return service.getAirwaies(token, airway);
    }

    /**
     * 新增航司基础数据
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airwaies/create")
    public Reply addAirway(@RequestHeader("Authorization") String token, @RequestBody Airway airway) throws Exception {
        return service.addAirway(token, airway);
    }

    /**
     * 删除航司基础数据(逻辑删除)
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airwaies/delete")
    public Reply deleteAirway(@RequestHeader("Authorization") String token, @RequestBody Airway airway) throws Exception {
        return service.deleteAirway(token, airway);
    }

    /**
     * 更新航司基础数据
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @PostMapping("/v1.0/airwaies/update")
    public Reply updateAirway(@RequestHeader("Authorization") String token, @RequestBody Airway airway) throws Exception {
        return service.updateAirway(token, airway);
    }



    @PostMapping("/v1.0/test")
    public Reply test() throws Exception {
        String name = java.nio.charset.Charset.defaultCharset().name();
        return ReplyHelper.success(name);
    }

}
