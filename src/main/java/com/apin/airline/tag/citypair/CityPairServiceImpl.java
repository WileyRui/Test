package com.apin.airline.tag.citypair;
import com.apin.airline.base.BaseService;
import com.apin.airline.common.entity.MbsResourceTag;
import com.apin.airline.common.entity.MsoCitypair;
import com.apin.airline.common.mapper.MbsResourceTagMapper;
import com.apin.airline.common.mapper.MsoCityPairMapper;
import com.apin.airline.common.mapper.MsoCityPairProvider;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaowei on 2017/10/14.
 */
@Service
public class CityPairServiceImpl implements CityPairService {
    @Autowired
    private MsoCityPairMapper msoCityPairMapper;
    @Autowired
    private MbsResourceTagMapper mbsResourceTagMapper;
    @Autowired
    private BaseService baseService;

    /**
     * 查询城市对列表
     * @param cityPairDTO
     * @return
     */
    @Override
    public Reply listCityPairs(CityPairDTO cityPairDTO) {
        PageHelper.startPage(cityPairDTO.getPage(), cityPairDTO.getSize());
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<CityPairVO> list = msoCityPairMapper.listCityPairs(cityPairDTO);
        PageInfo pageInfo = new PageInfo(list);
        map.put("totalRows", (int) pageInfo.getTotal());
        map.put("currentPage", pageInfo.getPageNum());
        return ReplyHelper.success(pageInfo.getList(),map);
    }

    /**
     * 新增城市对
     * @param Authorization
     * @param cityPairDTO
     * @return
     */
    @Override
    @Transactional
    public Reply insertCityPair(String Authorization, CityPairDTO cityPairDTO) {
        Reply reply = baseService.getCitiesByKey(Authorization,cityPairDTO.getDepCity());
        if(reply.getData()==null||((List<String>)reply.getData()).size()==0){
            return ReplyHelper.fail("出发城市不存在");
        }
         reply = baseService.getCitiesByKey(Authorization,cityPairDTO.getArrCity());
        if(reply.getData()==null||((List<String>)reply.getData()).size()==0){
            return ReplyHelper.fail("目的城市不存在");
        }
        if(existCityPair(cityPairDTO)) {
            return ReplyHelper.fail("城市对已存在");
        }
        AccessToken atObject = JsonUtils.toAccessToken(Authorization);
         MsoCitypair msoCityPair = new MsoCitypair();
        String id = Generator.uuid();
        msoCityPair.setId(id);
        msoCityPair.setDepCity(cityPairDTO.getDepCity());
        msoCityPair.setArrCity(cityPairDTO.getArrCity());
        msoCityPair.setCreatedTime(new Date());
        msoCityPair.setCreatorUserId(atObject.getUserId());
        msoCityPair.setFlightType(cityPairDTO.getFlightType());
        msoCityPairMapper.insert(msoCityPair);
        if(cityPairDTO.getIsExtract()!=null&&cityPairDTO.getIsExtract()==1){
            setExtract(atObject,id);
        }
       return ReplyHelper.success();
    }

    /**
     * 删除城市对
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Reply deleteCityPair(String id) {
        cancelExtract(id);
        msoCityPairMapper.deleteById(id);
        return ReplyHelper.success();
    }

    /**
     * 判断是否存在城市对
     * @param cityPairDTO
     * @return  true=存在，false=不存在
     */
    @Override
    public boolean existCityPair(CityPairDTO cityPairDTO) {
        Integer count = msoCityPairMapper.selectCount(new EntityWrapper<MsoCitypair>().eq("dep_city",cityPairDTO.getDepCity()).eq("arr_city",cityPairDTO.getArrCity()).eq("flight_type",cityPairDTO.getFlightType()));
        if(count>0) {
            return true;
        }
        return false;
    }

    /**
     * 设成精品
     * @param id
     * @return
     */
    @Override
    public Reply setExtract(AccessToken accessToken, String id) {
        MsoCitypair msoCityPair = new MsoCitypair();
        msoCityPair.setId(id);
        msoCityPair.setUpdateUserId(accessToken.getUserId());
        msoCityPair.setUpdateUserName(accessToken.getUserName());
        msoCityPair.setUpdateTime(new Date());
        msoCityPairMapper.updateCityPair(msoCityPair);
        MbsResourceTag mbsResourceTag = new MbsResourceTag();
        mbsResourceTag.setId(Generator.uuid());
        mbsResourceTag.setRefId(id);
        mbsResourceTag.setTagId(MsoCityPairProvider.EXTRACTTAGID);
        mbsResourceTag.setCreatedTime(new Date());
        mbsResourceTag.setCreatorUserId(accessToken.getUserId());
        mbsResourceTagMapper.insert(mbsResourceTag);
        return ReplyHelper.success();
    }

    /**
     * 取消精品
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Reply cancelExtract(String id) {
        MsoCitypair msoCityPair = new MsoCitypair();
        msoCityPair.setId(id);
        msoCityPair.setUpdateUserId("");
        msoCityPair.setUpdateUserName("");
        msoCityPair.setUpdateTime(null);
        msoCityPairMapper.updateCityPair(msoCityPair);
        mbsResourceTagMapper.delResourceTag(id,MsoCityPairProvider.EXTRACTTAGID);
        return ReplyHelper.success();
    }


}
