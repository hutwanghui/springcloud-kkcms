package com.kk.utils;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

public class JsonMessage {
	private String statu = "200";
	private String message;
	private Object data;

	public JsonMessage() {
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonMessage{" +
				"statu='" + statu + '\'' +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}
