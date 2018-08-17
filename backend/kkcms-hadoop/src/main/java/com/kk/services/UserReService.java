package com.kk.services;

import com.kk.daos.UserReDao;
import com.kk.models.UserRe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hutwanghui on 2018/7/14 13:37.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Service
@Transactional
public class UserReService {
	@Autowired
	private UserReDao userReDao;

	public UserRe save(UserRe userRe){
		return userReDao.save(userRe);
	}

	public List<UserRe> findAll(Integer userId){
		return userReDao.findUserReByUserId(userId);
	}

	public UserRe findOneByUserAndMovie(UserRe userRe){
		return userReDao.findUserReByUserIdAndAndMovieId(userRe.getUserId(), userRe.getMovieId());
	}

	public int deleteByUserId(UserRe userRe){
		return userReDao.deleteByUserId(userRe.getUserId());
	}

}
