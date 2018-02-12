package com.kk.api.entity;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
public class CommentItem extends BaseModel {
    private Comment comment;
    private User user;
    private User toUser;
    private List<CommentItem> childList;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public List<CommentItem> getChildList() {
        return childList;
    }

    public void setChildList(List<CommentItem> childList) {
        this.childList = childList;
    }
}
