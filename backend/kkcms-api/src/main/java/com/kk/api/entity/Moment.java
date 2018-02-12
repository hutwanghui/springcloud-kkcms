package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Moment extends BaseModel {
    /**
     * 内容
     */
    private String content;

    /**
     * 点赞的用户id列表
     */
    @Column(name = "praiseUserIdList")
    private String praiseuseridlist;

    /**
     * 图片列表
     */
    @Column(name = "pictureList")
    private String picturelist;

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取点赞的用户id列表
     *
     * @return praiseUserIdList - 点赞的用户id列表
     */
    public String getPraiseuseridlist() {
        return praiseuseridlist;
    }

    /**
     * 设置点赞的用户id列表
     *
     * @param praiseuseridlist 点赞的用户id列表
     */
    public void setPraiseuseridlist(String praiseuseridlist) {
        this.praiseuseridlist = praiseuseridlist;
    }

    /**
     * 获取图片列表
     *
     * @return pictureList - 图片列表
     */
    public String getPicturelist() {
        return picturelist;
    }

    /**
     * 设置图片列表
     *
     * @param picturelist 图片列表
     */
    public void setPicturelist(String picturelist) {
        this.picturelist = picturelist;
    }
}