package com.apin.airline.common.entity;

import java.util.Date;

/**
 * Created by zhaowei on 2017/10/14.
 * 标签配置关联
 */
public class MbsResourceTag {
    private String id;
    //关联id 为城市时关联airline.msd_city ,为精品航线时，关联mbs_citypair
    private String refId;
    //msd_tag.id
    private String tagId;
    //创建用户ID
    private String creatorUserId;
    //创建时间
    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
