package com.kk.daos;

import com.kk.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

public interface ScoreDao extends JpaRepository<Score, Integer> {

	/**
	 * 根据用户id和电影id查找电影评分
	 * @param userId
	 * @param MovieId
	 * @return
	 */
	public Score findByUserIdAndMovieId(int userId, String MovieId);

	/**
	 * 根据用户id找出该用户所有的电影评分
	 * @param userId
	 * @return
	 */
	public List<Score> findByUserId(int userId);
}
