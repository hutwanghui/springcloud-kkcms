package com.kk.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Entity
@Table(name = "movie_comment")
public class Comment {
	@Id
	@GeneratedValue
	private Integer commentId;
	private Integer userId;
	private String userName;
	private String movieId;
	private String commentText;
	private String createDate;

	public Comment() {
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String comment) {
		this.commentText = comment;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"commentId=" + commentId +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", movieId='" + movieId + '\'' +
				", commentText='" + commentText + '\'' +
				", createDate='" + createDate + '\'' +
				'}';
	}
}
