package com.kk.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hutwanghui on 2018/7/14 13:38.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Entity
@Table(name = "user_re")
public class UserRe {
	@Id
	@GeneratedValue()
	private Integer id;
	private Integer userId;
	private String movieId;

	public UserRe() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	@Override
	public String toString() {
		return "UserRe{" +
				"id=" + id +
				", userId=" + userId +
				", movieId='" + movieId + '\'' +
				'}';
	}
}
