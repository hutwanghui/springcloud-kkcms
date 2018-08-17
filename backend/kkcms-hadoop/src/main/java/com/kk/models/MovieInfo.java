package com.kk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "movie_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MovieInfo {
	@Id
	@GeneratedValue
	private String movieId;
	private String name;
	private String date;
	private String infoUrl;
	private String imageUrl;

	public MovieInfo() {
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "MovieInfo{" +
				"movieId='" + movieId + '\'' +
				", name='" + name + '\'' +
				", date='" + date + '\'' +
				", infoUrl='" + infoUrl + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				'}';
	}
}
