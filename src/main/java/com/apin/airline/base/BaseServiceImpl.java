package com.apin.airline.base;

import com.apin.airline.common.entity.Airport;
import com.apin.airline.common.entity.Airway;
import com.apin.airline.common.entity.City;
import com.apin.airline.common.entity.Country;
import com.apin.util.pojo.Reply;

/**
 * @author 宣炳刚
 * @date 2017/10/15
 * @remark
 */
public class BaseServiceImpl implements BaseService {
    /**
     * 查询国家基础数据(分页,按国家代码排序)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    @Override
    public Reply getCountries(String token, Country country) {
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
    }
}
