package com.kk.api.entity;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
public class MomentItem extends BaseModel {

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_PUBLISHING = 1;
    public static final int STATUS_DELETING = 2;
    public static final int STATUS_DELETED = 3;
    private int status;
    private User user;
    private Moment moment;
    private List<User> userList;//点赞的用户列表
    private List<CommentItem> commentItemList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<CommentItem> getCommentItemList() {
        return commentItemList;
    }

    public void setCommentItemList(List<CommentItem> commentItemList) {
        this.commentItemList = commentItemList;
    }
}
