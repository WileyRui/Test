package com.apin.airline.tag.city;

import com.apin.airline.base.BaseService;
import com.apin.airline.common.entity.City;
import com.apin.airline.common.entity.MbsResourceTag;
import com.apin.airline.common.mapper.MbsResourceTagMapper;
import com.apin.airline.common.plugin.QiniuPic;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.AccessToken;
import com.apin.util.pojo.Reply;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Created by zhaowei on 2017/10/16.
 */
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private MbsResourceTagMapper mbsResourceTagMapper;
    @Autowired
    private BaseService baseService;
    private static final String DEPHOTTAGID="ad9e321e8eac46a28e6032a3cfce2f75";
    private static final String ARRHOTTAGID="c39ac446830d40069eddc64a5a49d3aa";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Reply listCitys(String Authorization, CityDTO cityDTO) {
        cityDTO.setOffset(0);
        cityDTO.setCount(10000);
        City city = new City();
        BeanUtils.copyProperties(cityDTO,city);
        Reply reply = baseService.getCities(Authorization,city);
        if(!reply.getSuccess()) {
            return reply;
        }
        List<City> cityVOList = (List<City>) reply.getData();
        //设置城市标签
        City cityBean = null;
        List<String> depHotCityIds = getDepHotCityIds();
        List<String> arrHotCityIds = getArrHotCityIds();
        for (int i = 0; i < cityVOList.size(); i++) {
            cityBean = cityVOList.get(i);
             List<Integer> tagList = new ArrayList<>();
             if(depHotCityIds.contains(cityBean.getId())) {
                 tagList.add(0);
             }
             if(arrHotCityIds.contains(cityBean.getId())) {
                 tagList.add(1);
             }
            cityBean.setTag(tagList);
        }
        List<City> cityVOS = new ArrayList<>();

        if(cityDTO.getTag()!=null){
            for (int i = 0; i < cityVOList.size(); i++) {
                cityBean = cityVOList.get(i);
               if(((List<Integer>)cityBean.getTag()).contains(cityDTO.getTag())) {
                   cityVOS.add(cityBean);
               }
            }
        }else{
            cityVOS = cityVOList;
        }

        PageHelper.startPage(cityDTO.getPage(), cityDTO.getSize());
        Map<String, Integer> map = new HashMap<String, Integer>();
        int startIndex = (cityDTO.getPage()-1)*cityDTO.getSize();
        int endIndex = startIndex+cityDTO.getSize();
        if(endIndex>cityVOS.size()) {
            endIndex = cityVOS.size();
        }
        PageInfo pageInfo = new PageInfo(cityVOS);
        map.put("totalRows", (int) pageInfo.getTotal());
        map.put("currentPage", pageInfo.getPageNum());
        return ReplyHelper.success(cityVOS.subList(startIndex,endIndex),map);
    }

    @Override
    @Transactional
    public Reply setCityTag(AccessToken accessToken, CityDTO cityDTO) {
        mbsResourceTagMapper.delete(new EntityWrapper<MbsResourceTag>().eq("ref_id",cityDTO.getId()));
        if(cityDTO.getDepHot()!=null){
            MbsResourceTag mbsResourceTag = new MbsResourceTag();
            mbsResourceTag.setId(Generator.uuid());
            mbsResourceTag.setRefId(cityDTO.getId());
            mbsResourceTag.setTagId(DEPHOTTAGID);
            mbsResourceTag.setCreatedTime(new Date());
            mbsResourceTag.setCreatorUserId(accessToken.getUserId());
            mbsResourceTagMapper.insert(mbsResourceTag);
        }
        if(cityDTO.getArrHot()!=null){
            MbsResourceTag mbsResourceTag = new MbsResourceTag();
            mbsResourceTag.setId(Generator.uuid());
            mbsResourceTag.setRefId(cityDTO.getId());
            mbsResourceTag.setTagId(ARRHOTTAGID);
            mbsResourceTag.setCreatedTime(new Date());
            mbsResourceTag.setCreatorUserId(accessToken.getUserId());
            mbsResourceTagMapper.insert(mbsResourceTag);
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply setCityImg(String Authorization, CityDTO cityDTO) {
        QiniuPic qiniuPic = new QiniuPic();
        cityDTO.setImgUrl(qiniuPic.downLoad(qiniuPic.toFile(cityDTO.getImgBase64())));
        City city = new City();
        BeanUtils.copyProperties(cityDTO,city);
         return  baseService.updateCity(Authorization,city);
    }

    private List<String> getDepHotCityIds(){
       List<MbsResourceTag> mbsResourceTags = mbsResourceTagMapper.selectList(new EntityWrapper<MbsResourceTag>().eq("tag_id",DEPHOTTAGID));
       List<String> depHotCityIds = new ArrayList<>();
       if(mbsResourceTags!=null&&mbsResourceTags.size()>0){
           for (int i = 0; i < mbsResourceTags.size(); i++) {
               depHotCityIds.add(mbsResourceTags.get(i).getRefId());
           }
       }
       return depHotCityIds;
    }
    private List<String> getArrHotCityIds(){
        List<MbsResourceTag> mbsResourceTags = mbsResourceTagMapper.selectList(new EntityWrapper<MbsResourceTag>().eq("tag_id",ARRHOTTAGID));
        List<String> arrHotCityIds = new ArrayList<>();
        if(mbsResourceTags!=null&&mbsResourceTags.size()>0){
            for (int i = 0; i < mbsResourceTags.size(); i++) {
                arrHotCityIds.add(mbsResourceTags.get(i).getRefId());
            }
        }
        return arrHotCityIds;
    }
}
