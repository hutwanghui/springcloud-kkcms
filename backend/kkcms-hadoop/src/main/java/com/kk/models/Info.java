package com.kk.models;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

public class Info {
	private String id;
	private String directer;
	private String actor;
	private String type;
	private String Url;
	private String images;
	private String movieSummary;

	public Info() {
	}

	public String getDirecter() {
		return directer;
	}

	public void setDirecter(String directer) {
		this.directer = directer;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getMovieSummary() {
		return movieSummary;
	}

	public void setMovieSummary(String movieSummary) {
		this.movieSummary = movieSummary;
	}

	@Override
	public String toString() {
		return "Info{" +
				"id='" + id + '\'' +
				", directer='" + directer + '\'' +
				", actor='" + actor + '\'' +
				", type='" + type + '\'' +
				", Url='" + Url + '\'' +
				", images='" + images + '\'' +
				", movieSummary='" + movieSummary + '\'' +
				'}';
	}
}
