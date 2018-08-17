package com.kk.daos;

import com.kk.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hutwanghui on 2018/7/14 13:40.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {
	/**
	 * 根据用户名密码查找用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserInfo findByUserNameAndPassword(String userName, String password);

	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public UserInfo findByUserName(String userName);

	/**
	 * 根据用户id查找用户
	 * @param userId
	 * @return
	 */
	public UserInfo findByUserId(int userId);

	/**
	 * 根据用户id和用户密码查找用户
	 * @param userId
	 * @param pwd
	 * @return
	 */
	public UserInfo findByUserIdAndPassword(int userId, String pwd);

}
