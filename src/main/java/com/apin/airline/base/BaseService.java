package com.apin.airline.base;

import com.apin.airline.common.entity.Airport;
import com.apin.airline.common.entity.Airway;
import com.apin.airline.common.entity.City;
import com.apin.airline.common.entity.Country;
import com.apin.util.pojo.Reply;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/15
 * @remark 基础数据服务接口
 */
public interface BaseService {

    /**
     * 查询国家基础数据(分页,按国家代码排序)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    Reply getCountries(String token, Country country);

    /**
     * 新增国家基础数据
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    Reply addCountry(String token, Country country);

    /**
     * 删除国家基础数据(逻辑删除)
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    Reply deleteCountry(String token, Country country);

    /**
     * 编辑国家基础数据
     *
     * @param token   访问令牌
     * @param country 国家基础数据
     * @return Reply
     */
    Reply updateCountry(String token, Country country);

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    Reply getCities(String token, City city);

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param token 访问令牌
     * @return Reply
     */
    Reply getCitiesByKey(String token, String key);

    /**
     * 新增城市基础数据
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    Reply addCity(String token, City city);

    /**
     * 删除城市基础数据(逻辑删除)
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    Reply deleteCity(String token, City city);

    /**
     * 编辑城市基础数据
     *
     * @param token 访问令牌
     * @param city  城市基础数据
     * @return Reply
     */
    Reply updateCity(String token, City city);

    /**
     * 查询机场基础数据(分页,按三字码排序)
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    Reply getAirports(String token, Airport airport);

    /**
     * 新增机场基础数据
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    Reply addAirport(String token, Airport airport);

    /**
     * 删除机场基础数据(逻辑删除)
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    Reply deleteAirport(String token, Airport airport);

    /**
     * 编辑机场基础数据
     *
     * @param token   访问令牌
     * @param airport 机场基础数据
     * @return Reply
     */
    Reply updateAirport(String token, Airport airport);

    /**
     * 查询航司基础数据(分页,按航司代码排序)
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    Reply getAirwaies(String token, Airway airway);

    /**
     * 新增航司基础数据
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    Reply addAirway(String token, Airway airway);

    /**
     * 删除航司基础数据(逻辑删除)
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    Reply deleteAirway(String token, Airway airway);

    /**
     * 更新航司基础数据
     *
     * @param token  访问令牌
     * @param airway 航司基础数据
     * @return Reply
     */
    Reply updateAirway(String token, Airway airway);

    /**
     * 查询热门城市数据
     * @param token
     * @param ids
     * @return
     */
    Reply getCitiesByIds( List<String> ids);
}
