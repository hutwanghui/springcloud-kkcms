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

    @Transient
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Moment)) return false;

        Moment moment = (Moment) o;

        if (getContent() != null ? !getContent().equals(moment.getContent()) : moment.getContent() != null)
            return false;
        if (getPraiseuseridlist() != null ? !getPraiseuseridlist().equals(moment.getPraiseuseridlist()) : moment.getPraiseuseridlist() != null)
            return false;
        return getPicturelist() != null ? getPicturelist().equals(moment.getPicturelist()) : moment.getPicturelist() == null;
    }

    @Override
    public int hashCode() {
        int result = getContent() != null ? getContent().hashCode() : 0;
        result = 31 * result + (getPraiseuseridlist() != null ? getPraiseuseridlist().hashCode() : 0);
        result = 31 * result + (getPicturelist() != null ? getPicturelist().hashCode() : 0);
        return result;
    }
}