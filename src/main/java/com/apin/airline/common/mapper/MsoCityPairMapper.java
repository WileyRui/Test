package com.apin.airline.common.mapper;

import com.apin.airline.tag.citypair.CityPairDTO;
import com.apin.airline.tag.citypair.CityPairVO;
import com.apin.airline.common.entity.MsoCitypair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

/**
 * Created by zhaowei on 2017/10/14.
 */
@Mapper
public interface MsoCityPairMapper extends BaseMapper<MsoCitypair> {
    @SelectProvider(type = MsoCityPairProvider.class, method = "listCityPairs")
    public List<CityPairVO> listCityPairs(CityPairDTO cityPairDTO);
    @Update("update mso_citypair set update_user_id = #{updateUserId},update_user_name = #{updateUserName},update_time=#{updateTime} where id=#{id}")
    public void updateCityPair(MsoCitypair msoCitypair);
}
