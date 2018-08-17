package com.kk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by hutwanghui on 2018/7/14 13:38.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Entity
@Table(name = "user_info")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="mySeqGenerator")
	@SequenceGenerator(name = "mySeqGenerator", sequenceName = "user_sequence", initialValue = 1000)
	private Integer userId;
	private String userName;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private Integer age = 0;
	@JsonIgnore
	private String gender = "男";
	@JsonIgnore
	private String occupation = "无";
	@JsonIgnore
	private String setUpAlgo = "als";

	public UserInfo() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSetUpAlgo() {
		return setUpAlgo;
	}

	public void setSetUpAlgo(String setUpAlgo) {
		this.setUpAlgo = setUpAlgo;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", age=" + age +
				", gender='" + gender + '\'' +
				", occupation='" + occupation + '\'' +
				", setUpAlgo='" + setUpAlgo + '\'' +
				'}';
	}

	public boolean isEmpty(){
		return this.getUserId() == null;
	}
}