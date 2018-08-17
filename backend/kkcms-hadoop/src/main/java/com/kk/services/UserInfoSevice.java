package com.kk.services;

import com.kk.daos.UserInfoDao;
import com.kk.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hutwanghui on 2018/7/14 13:37.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Service
public class UserInfoSevice {
	@Autowired
	private UserInfoDao userInfoDao;

	public UserInfo findByName(UserInfo userInfo) {
		return userInfoDao.findByUserName(userInfo.getUserName());
	}

	public UserInfo findByNameAndPwd(UserInfo userInfo) {
		return userInfoDao.findByUserNameAndPassword(userInfo.getUserName(),
				userInfo.getPassword());
	}

	public UserInfo saveUser(UserInfo userInfo) {
		return userInfoDao.save(userInfo);
	}

	public UserInfo findById(int userId) {
		return userInfoDao.findByUserId(userId);
	}

	public UserInfo checkPwd(int userId, String pwd) {
		return userInfoDao.findByUserIdAndPassword(userId, pwd);
	}

	public UserInfo changePwd(int userId, String pwd) {
		UserInfo u = userInfoDao.findByUserId(userId);
		u.setPassword(pwd);
		return userInfoDao.save(u);
	}
}
