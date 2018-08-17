package com.kk.modules.article.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;
import javax.persistence.*;

@TableName("article")
public class BlArticle {
    @TableId
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 文章地址
     */
    private String url;

    /**
     * 文章ID
     */
    @Column(name = "url_object_id")
    private String urlObjectId;

    /**
     * 首页图片地址
     */
    @Column(name = "from_image_url")
    private String fromImageUrl;

    /**
     * 首页图片本地存储路径
     */
    @Column(name = "from_image_path")
    private String fromImagePath;

    /**
     * 点赞数
     */
    @Column(name = "praise_num")
    private Integer praiseNum;

    /**
     * 收藏数
     */
    @Column(name = "collect_num")
    private Integer collectNum;

    /**
     * 评论数
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 标签
     */
    private String tags;

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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取文章地址
     *
     * @return url - 文章地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置文章地址
     *
     * @param url 文章地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取文章ID
     *
     * @return url_object_id - 文章ID
     */
    public String getUrlObjectId() {
        return urlObjectId;
    }

    /**
     * 设置文章ID
     *
     * @param urlObjectId 文章ID
     */
    public void setUrlObjectId(String urlObjectId) {
        this.urlObjectId = urlObjectId;
    }

    /**
     * 获取首页图片地址
     *
     * @return from_image_url - 首页图片地址
     */
    public String getFromImageUrl() {
        return fromImageUrl;
    }

    /**
     * 设置首页图片地址
     *
     * @param fromImageUrl 首页图片地址
     */
    public void setFromImageUrl(String fromImageUrl) {
        this.fromImageUrl = fromImageUrl;
    }

    /**
     * 获取首页图片本地存储路径
     *
     * @return from_image_path - 首页图片本地存储路径
     */
    public String getFromImagePath() {
        return fromImagePath;
    }

    /**
     * 设置首页图片本地存储路径
     *
     * @param fromImagePath 首页图片本地存储路径
     */
    public void setFromImagePath(String fromImagePath) {
        this.fromImagePath = fromImagePath;
    }

    /**
     * 获取点赞数
     *
     * @return praise_num - 点赞数
     */
    public Integer getPraiseNum() {
        return praiseNum;
    }

    /**
     * 设置点赞数
     *
     * @param praiseNum 点赞数
     */
    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    /**
     * 获取收藏数
     *
     * @return collect_num - 收藏数
     */
    public Integer getCollectNum() {
        return collectNum;
    }

    /**
     * 设置收藏数
     *
     * @param collectNum 收藏数
     */
    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    /**
     * 获取评论数
     *
     * @return comment_num - 评论数
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 设置评论数
     *
     * @param commentNum 评论数
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 获取标签
     *
     * @return tags - 标签
     */
    public String getTags() {
        return tags;
    }

    /**
     * 设置标签
     *
     * @param tags 标签
     */
    public void setTags(String tags) {
        this.tags = tags;
    }
}