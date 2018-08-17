package com.kk.daos;

import com.kk.models.UserRe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

public interface UserReDao extends JpaRepository<UserRe, Integer> {
	/**
	 * 根据用户id查找该用户的所有预测
	 * @param userId
	 * @return
	 */
	public List<UserRe> findUserReByUserId(Integer userId);

	/**
	 * 根据用户id和电影id查找记录
	 * @param userId
	 * @param movieId
	 * @return
	 */
	public UserRe findUserReByUserIdAndAndMovieId(Integer userId, String movieId);

	/**
	 * 根据用户id进行删除
	 * @param userId
	 * @return
	 */
	public int deleteByUserId(Integer userId);
}
