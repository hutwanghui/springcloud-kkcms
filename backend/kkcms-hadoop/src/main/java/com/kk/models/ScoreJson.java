package com.kk.models;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

//todo 这里的json格式要修改一下
public class ScoreJson {
	private String userid;
	private String algo;
	private String movieid;

	public ScoreJson() {
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	public String getMovieid() {
		return movieid;
	}

	public void setMovieid(String movieid) {
		this.movieid = movieid;
	}

	@Override
	public String toString() {
		return "ScoreJson{" +
				"userid='" + userid + '\'' +
				", algo='" + algo + '\'' +
				", movieid=" + movieid +
				'}';
	}
}
