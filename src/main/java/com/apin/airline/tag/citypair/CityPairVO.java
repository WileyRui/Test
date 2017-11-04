package com.apin.airline.tag.citypair;

import com.apin.airline.common.entity.MsoCitypair;

/**
 * Created by zhaowei on 2017/10/14.
 */
public class CityPairVO extends MsoCitypair {
    //null 为全部，0=非精品，1=精品
    private Integer isExtract;

    public Integer getIsExtract() {
        return isExtract;
    }

    public void setIsExtract(Integer isExtract) {
        this.isExtract = isExtract;
    }
}
