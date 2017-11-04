package com.apin.airline.common.entity;

import java.util.Date;

/**
 * Created by zhaowei on 2017/10/14.
 * 标签表
 */
public class MsdTag {
    private String id;
    //标签类别：A：城市 B：城市对
    private String type;
    //标签名称
    private String tagName;
    //标签值，越大越热
    private Integer point;
    //图标
    private String icon;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
