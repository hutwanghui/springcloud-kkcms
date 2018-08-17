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
@Table(name = "movie_score")
public class Score {
	@Id
	@GeneratedValue
	private Integer scoreId;
	private Integer userId;
	private String movieId;
	private Integer score;

	public Score() {
	}

	public Integer getScoreId() {
		return scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Score{" +
				"userId=" + userId +
				", movieId='" + movieId + '\'' +
				", score=" + score +
				'}';
	}

	public String toIdAndScore(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.movieId).append(":").append(this.score);
		return sb.toString();
	}
}
