package com.apin.airline.tag.airway;

import com.apin.airline.base.BaseService;
import com.apin.airline.common.entity.Airway;
import com.apin.airline.common.plugin.QiniuPic;
import com.apin.util.pojo.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by zhaowei on 2017/10/16.
 */
@Service
public class AirWayServiceImpl implements AirWayService {
    @Autowired
    private BaseService baseService;

    /**
     * 更新城市logo图片
     * @param Authorization
     * @param airWayDTO
     * @return
     */
    @Override
    public Reply updateAirWay(String Authorization, AirWayDTO airWayDTO) {
        QiniuPic qiniuPic = new QiniuPic();
        airWayDTO.setLogoIco(qiniuPic.downLoad(qiniuPic.toFile(airWayDTO.getImgBase64())));
        Airway airway = new Airway();
        BeanUtils.copyProperties(airWayDTO,airway);
        return baseService.updateAirway(Authorization,airway);
    }

    /**
     * 航司列表查询
     * @param Authorization
     * @param airWayDTO
     * @return
     */
    @Override
    public Reply listAirWays(String Authorization, AirWayDTO airWayDTO) {
        airWayDTO.setCount(airWayDTO.getSize());
        airWayDTO.setOffset((airWayDTO.getPage()-1)*airWayDTO.getSize());
        Airway airway = new Airway();
        BeanUtils.copyProperties(airWayDTO,airway);
        return baseService.getAirwaies(Authorization,airway);
    }
}
