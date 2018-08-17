package com.kk.daos;

import com.kk.models.MovieInfo;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hutwanghui on 2018/7/14 13:40.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */
@Repository
public interface MovieInfoDao extends JpaRepository<MovieInfo, String>, PagingAndSortingRepository<MovieInfo, String> {

    /**
     * 根据电影id们查找电影
     *
     * @param movieIds
     * @return
     */
    public List<MovieInfo> findMovieInfoByMovieIdIn(List<String> movieIds);

    /**
     * 根据电影名模糊查询
     *
     * @param keyword
     * @param pageable
     * @return
     */
    public Page<MovieInfo> findMovieInfoByNameLike(String keyword, Pageable pageable);

    /**
     * 根据电影id降序
     *
     * @return
     */
    @Query("select m from MovieInfo m order by m.movieId desc")
    public List<MovieInfo> findDesc();


    default boolean existsById(String id) {
        if (this.getOne(id) != null) {
            return true;
        }
        return false;
    }

    //List<MovieInfo> saveAll(List<MovieInfo> movieInfos);
}
