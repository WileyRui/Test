package com.apin.airline.common.mapper;

import com.apin.airline.common.entity.MbsResourceTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * Created by zhaowei on 2017/10/14.
 */
@Mapper
public interface MbsResourceTagMapper extends BaseMapper<MbsResourceTag> {
    @Update("delete from mbs_resource_tag where ref_id = #{refId} and tag_id = #{tagId}")
    public void delResourceTag(@Param("refId") String refId, @Param("tagId") String tagId);
}
