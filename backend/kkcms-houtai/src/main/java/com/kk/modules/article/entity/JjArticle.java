package com.kk.modules.article.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.persistence.*;

@TableName("juejin")
public class JjArticle {
    @TableId
    private Integer id;

    private String title;

    @Column(name = "summaryInfo")
    private String summaryinfo;

    @Column(name = "updatedAt")
    private String updatedat;

    private String tags;

    @Column(name = "collectionCount")
    private Integer collectioncount;

    @Column(name = "commentsCount")
    private Integer commentscount;

    @Column(name = "viewsCount")
    private Integer viewscount;

    @Column(name = "originalUrl")
    private String originalurl;

    private String screenshot;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return summaryInfo
     */
    public String getSummaryinfo() {
        return summaryinfo;
    }

    /**
     * @param summaryinfo
     */
    public void setSummaryinfo(String summaryinfo) {
        this.summaryinfo = summaryinfo;
    }

    /**
     * @return updatedAt
     */
    public String getUpdatedat() {
        return updatedat;
    }

    /**
     * @param updatedat
     */
    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    /**
     * @return tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return collectionCount
     */
    public Integer getCollectioncount() {
        return collectioncount;
    }

    /**
     * @param collectioncount
     */
    public void setCollectioncount(Integer collectioncount) {
        this.collectioncount = collectioncount;
    }

    /**
     * @return commentsCount
     */
    public Integer getCommentscount() {
        return commentscount;
    }

    /**
     * @param commentscount
     */
    public void setCommentscount(Integer commentscount) {
        this.commentscount = commentscount;
    }

    /**
     * @return viewsCount
     */
    public Integer getViewscount() {
        return viewscount;
    }

    /**
     * @param viewscount
     */
    public void setViewscount(Integer viewscount) {
        this.viewscount = viewscount;
    }

    /**
     * @return originalUrl
     */
    public String getOriginalurl() {
        return originalurl;
    }

    /**
     * @param originalurl
     */
    public void setOriginalurl(String originalurl) {
        this.originalurl = originalurl;
    }

    /**
     * @return screenshot
     */
    public String getScreenshot() {
        return screenshot;
    }

    /**
     * @param screenshot
     */
    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }
}