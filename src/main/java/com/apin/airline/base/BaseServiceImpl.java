package com.apin.airline.base;

import com.apin.airline.common.entity.Airport;
import com.apin.airline.common.entity.Airway;
import com.apin.airline.common.entity.City;
import com.apin.airline.common.entity.Country;
import com.apin.airline.common.mapper.BaseMapper;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/15
 * @remark 基础数据服务实现
 */
@Service
public class BaseServiceImpl implements BaseService {
    private final BaseMapper mapper;

    /**
     * 构造方法,自动注入AirlineMapper
     *
     * @param mapper AirlineMapper
     */
    @Autowired
    public BaseServiceImpl(BaseMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 查询国家基础数据(分页,按国家代码排序)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @Override
    public Reply getCountries(String token, Country country) {
        
        // 处理空值
        country.setId(country.getId() == null ? "NULL" : country.getId());
        country.setZoneCode(country.getZoneCode() == null ? "NULL" : country.getZoneCode());
        country.setCountryCode(country.getCountryCode() == null ? "NULL" : country.getCountryCode());
        country.setCountryName(country.getCountryName() == null ? "NULL" : country.getCountryName());

        // 查询数据
        List<Country> countries = mapper.getCountries(country);
        return ReplyHelper.success(countries);
    }

    /**
     * 新增国家基础数据
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @Override
    public Reply addCountry(String token, Country country) {
        Integer count = mapper.addCountry(country);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 删除国家基础数据(逻辑删除)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @Override
    public Reply deleteCountry(String token, Country country) {
        Integer count = mapper.deleteCountry(country.getId());
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 编辑国家基础数据
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @Override
    public Reply updateCountry(String token, Country country) {
        Integer count = mapper.updateCountry(country);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @Override
    public Reply getCities(String token, City city) {
        return null;
    }

    /**
     * 新增城市基础数据
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @Override
    public Reply addCity(String token, City city) {
        Integer count = mapper.addCity(city);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 删除城市基础数据(逻辑删除)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @Override
    public Reply deleteCity(String token, City city) {
        Integer count = mapper.deleteCity(city.getId());
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 编辑城市基础数据
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    @Override
    public Reply updateCity(String token, City city) {
        Integer count = mapper.updateCity(city);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 查询机场基础数据(分页,按三字码排序)
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @Override
    public Reply getAirports(String token, Airport airport) {
        return null;
    }

    /**
     * 新增机场基础数据
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @Override
    public Reply addAirport(String token, Airport airport) {
        Integer count = mapper.addAirport(airport);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 删除机场基础数据(逻辑删除)
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @Override
    public Reply deleteAirport(String token, Airport airport) {
        Integer count = mapper.deleteAirport(airport.getId());
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 编辑机场基础数据
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    @Override
    public Reply updateAirport(String token, Airport airport) {
        Integer count = mapper.updateAirport(airport);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 查询航司基础数据(分页,按航司代码排序)
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @Override
    public Reply getAirwaies(String token, Airway airway) {
        return null;
    }

    /**
     * 新增航司基础数据
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @Override
    public Reply addAirway(String token, Airway airway) {
        Integer count = mapper.addAirway(airway);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 删除航司基础数据(逻辑删除)
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @Override
    public Reply deleteAirway(String token, Airway airway) {
        Integer count = mapper.deleteAirway(airway.getId());
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }

    /**
     * 更新航司基础数据
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    @Override
    public Reply updateAirway(String token, Airway airway) {
        Integer count = mapper.updateAirway(airway);
        return count > 0 ? ReplyHelper.success() : ReplyHelper.error();
    }
}
