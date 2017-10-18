package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.Airport;
import com.apin.airline.common.entity.Airway;
import com.apin.airline.common.entity.City;
import com.apin.airline.common.entity.Country;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 宣炳刚
 * @date 2017/10/16
 * @remark 基础数据管理DAL
 */
@Mapper
public interface BaseMapper extends Mapper {

    /**
     * 查询国家基础数据(分页,按国家代码排序)
     *
     * @param country 国家基础数据
     * @return 国家基础数据集合
     */
    @Select("SELECT * FROM msd_country WHERE is_invalid=0 " +
            "AND ('NULL'=#{id} OR id=#{id}) " +
            "AND ('NULL'=#{zoneCode} OR zone_code=#{zoneCode}) " +
            "AND ('NULL'=#{countryCode} OR country_code=#{countryCode}) " +
            "AND ('NULL'=#{countryName} OR country_name=#{countryName}) " +
            "ORDER BY country_code LIMIT #{offset},#{count};")
    List<Country> getCountries(Country country);

    /**
     * 查询国家基础数据总行数
     *
     * @param country 国家基础数据
     * @return 总行数
     */
    @Select("SELECT COUNT(*) FROM msd_country WHERE is_invalid=0 " +
            "AND ('NULL'=#{id} OR id=#{id}) " +
            "AND ('NULL'=#{zoneCode} OR zone_code=#{zoneCode}) " +
            "AND ('NULL'=#{countryCode} OR country_code=#{countryCode}) " +
            "AND ('NULL'=#{countryName} OR country_name=#{countryName});")
    Integer getTotalCountries(Country country);

    /**
     * 新增国家基础数据
     *
     * @param country 国家基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_country(id,zone_code,country_code,country_name) " +
            "VALUES (#{id},#{zoneCode},#{countryCode},#{countryName});")
    Integer addCountry(Country country);

    /**
     * 删除国家基础数据(逻辑删除)
     *
     * @param id 国家基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_country SET is_invalid=1 WHERE id=#{id};")
    Integer deleteCountry(String id);

    /**
     * 编辑国家基础数据
     *
     * @param country 国家基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_country SET zone_code=#{zoneCode},country_code=#{countryCode},country_name=#{countryName} WHERE id=#{id};")
    Integer updateCountry(Country country);

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param key  单索引词
     * @return 城市基础数据集合
     */
    @Select("SELECT DISTINCT c.city_name " +
            "FROM msd_city c JOIN msd_airport p ON p.city_code=c.city_code " +
            "WHERE c.is_invalid=0 " +
            "AND (c.city_code like CONCAT('%',#{keyword},'%') " +
            "OR c.city_name like CONCAT('%',#{keyword},'%') " +
            "OR c.en_name like CONCAT('%',#{keyword},'%') " +
            "OR p.iata_code like CONCAT('%',#{keyword},'%'))")
    List<String> getCityNames(String keyword);

    /**
     * 查询城市基础数据(分页,按拼音排序)
     *
     * @param city 城市基础数据
     * @return 城市基础数据集合
     */
    @Select("SELECT g.country_name,c.*,GROUP_CONCAT(p.airport_name) AS airports " +
            "FROM msd_city c LEFT JOIN msd_country g ON g.id=c.country_id JOIN msd_airport p ON p.city_code=c.city_code " +
            "WHERE c.is_invalid=0 " +
            "AND ('NULL'=#{id} OR c.id=#{id}) " +
            "AND ('NULL'=#{countryName} OR g.country_name=#{countryName}) " +
            "AND ('NULL'=#{cityCode} OR c.city_code=#{cityCode}) " +
            "AND ('NULL'=#{cityName} OR c.city_name=#{cityName}) " +
            "AND ('NULL'=#{enName} OR c.en_name=#{enName}) " +
            "AND ('NULL'=#{pinyinFirst} OR c.pinyin_first=#{pinyinFirst}) " +
            "GROUP BY c.id ORDER BY c.en_name LIMIT #{offset},#{count};")
    List<City> getCities(City city);

    /**
     * 查询城市基础数据总行数
     *
     * @param city 城市基础数据
     * @return 总行数
     */
    @Select("SELECT COUNT(*) FROM msd_city c LEFT JOIN msd_country g ON g.id=c.country_id " +
            "JOIN msd_airport p ON p.city_code=c.city_code WHERE c.is_invalid=0 " +
            "AND ('NULL'=#{id} OR c.id=#{id}) " +
            "AND ('NULL'=#{countryName} OR g.country_name=#{countryName}) " +
            "AND ('NULL'=#{cityCode} OR c.city_code=#{cityCode}) " +
            "AND ('NULL'=#{cityName} OR c.city_name=#{cityName}) " +
            "AND ('NULL'=#{enName} OR c.en_name=#{enName}) " +
            "AND ('NULL'=#{pinyinFirst} OR c.pinyin_first=#{pinyinFirst}) ")
    Integer getTotalCities(City city);

    /**
     * 新增城市基础数据
     *
     * @param city 城市基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_city(id,country_id,city_code,city_name,en_name,pinyin_first,img_url,description,longitude,latitude) " +
            "VALUES (#{id},#{countryId},#{cityCode},#{cityName},#{enName},#{pinyinFirst},#{imgUrl},#{description},#{longitude},#{latitude});")
    Integer addCity(City city);

    /**
     * 删除城市基础数据(逻辑删除)
     *
     * @param id 城市基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_city SET is_invalid=1 WHERE id=#{id};")
    Integer deleteCity(String id);

    /**
     * 编辑城市基础数据
     *
     * @param city 城市基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_city SET country_id=#{countryId},city_code=#{cityCode},city_name=#{cityName},en_name=#{enName},pinyin_first=#{pinyinFirst}," +
            "img_url=#{imgUrl},description=#{description},longitude=#{longitude},latitude=#{latitude} WHERE id=#{id};")
    Integer updateCity(City city);

    /**
     * 查询机场基础数据(分页,按三字码排序)
     *
     * @param airport 机场基础数据
     * @return 机场基础数据集合
     */
    @Select("SELECT * FROM msd_airport WHERE is_invalid=0 " +
            "AND ('NULL'=#{id} OR id=#{id}) " +
            "AND ('NULL'=#{cityCode} OR city_code=#{cityCode}) " +
            "AND ('NULL'=#{iataCode} OR iata_code=#{iataCode}) " +
            "AND ('NULL'=#{airportName} OR airport_name=#{airportName}) " +
            "ORDER BY iata_code LIMIT #{offset},#{count};")
    List<Airport> getAirports(Airport airport);

    /**
     * 查询机场基础数据总行数
     *
     * @param airport 机场基础数据
     * @return 总行数
     */
    @Select("SELECT COUNT(*) FROM msd_airport WHERE is_invalid=0 " +
            "AND ('NULL'=#{id} OR id=#{id}) " +
            "AND ('NULL'=#{cityCode} OR city_code=#{cityCode}) " +
            "AND ('NULL'=#{iataCode} OR iata_code=#{iataCode}) " +
            "AND ('NULL'=#{airportName} OR airport_name=#{airportName});")
    Integer getTotalAirports(Airport airport);

    /**
     * 新增机场基础数据
     *
     * @param airport 机场基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_airport(id,city_code,iata_code,icao_code,airport_name,longitude,latitude,time_zone) " +
            "VALUES (#{id},#{cityCode},#{iataCode},#{icaoCode},#{airportName},#{longitude},#{latitude},#{timeZone});")
    Integer addAirport(Airport airport);

    /**
     * 删除机场基础数据(逻辑删除)
     *
     * @param id 机场基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_airport SET is_invalid=1 WHERE id=#{id};")
    Integer deleteAirport(String id);

    /**
     * 编辑机场基础数据
     *
     * @param airport 机场基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_airport SET city_code=#{cityCode},iata_code=#{iataCode},icao_code=#{icaoCode},airport_name=#{airportName}," +
            "longitude=#{longitude},latitude=#{latitude},time_zone=#{timeZone} WHEREid=#{id};")
    Integer updateAirport(Airport airport);

    /**
     * 查询航司基础数据(分页,按航司代码排序)
     *
     * @param airway 航司基础数据
     * @return 航司基础数据集合
     */
    @Select("SELECT * FROM msd_airway WHERE is_invalid=0 " +
            "AND ('NULL'=#{id} OR id=#{id}) " +
            "AND ('NULL'=#{nationCode} OR nation_code=#{nationCode}) " +
            "AND ('NULL'=#{iataCode} OR iata_code=#{iataCode}) " +
            "AND ('NULL'=#{companyName} OR company_name LIKE CONCAT('%',#{companyName},'%')) " +
            "ORDER BY iata_code LIMIT #{offset},#{count};")
    List<Airway> getAirwaies(Airway airway);

    /**
     * 查询航司基础数据总行数
     *
     * @param airway 航司基础数据
     * @return 总行数
     */
    @Select("SELECT COUNT(*) FROM msd_airway WHERE is_invalid=0 " +
            "AND ('NULL'=#{id} OR id=#{id}) " +
            "AND ('NULL'=#{nationCode} OR nation_code=#{nationCode}) " +
            "AND ('NULL'=#{iataCode} OR iata_code=#{iataCode}) " +
            "AND ('NULL'=#{companyName} OR company_name LIKE CONCAT('%',#{companyName},'%'));")
    Integer getTotalAirwaies(Airway airway);

    /**
     * 新增航司基础数据
     *
     * @param airway 航司基础数据
     * @return 受影响行数
     */
    @Insert("INSERT msd_airway(id,iata_code,company_name,nation_code,logo_ico) " +
            "VALUES (#{id},#{iataCode},#{companyName},#{nationCode},#{logoIco});")
    Integer addAirway(Airway airway);

    /**
     * 删除航司基础数据(逻辑删除)
     *
     * @param id 航司基础数据ID
     * @return 受影响行数
     */
    @Update("UPDATE msd_airway SET is_invalid=1 WHERE id=#{id};")
    Integer deleteAirway(String id);

    /**
     * 更新航司基础数据
     *
     * @param airway 航司基础数据
     * @return 受影响行数
     */
    @Update("UPDATE msd_airway SET " +
            "iata_code=#{iataCode},company_name=#{companyName},nation_code=#{nationCode},logo_ico=#{logoIco} " +
            "WHERE id=#{id};")
    Integer updateAirway(Airway airway);
}
