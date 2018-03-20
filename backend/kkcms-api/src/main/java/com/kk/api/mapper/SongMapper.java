package com.kk.api.mapper;

import com.kk.api.entity.Song;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.CrudRepository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SongMapper extends Mapper<Song> {

    public Long count();

    public void insertCTQ(@Param("list") List<Song> list);

    public void deleteCTQ(@Param("list") List<String> list);

    public void updateCTQ(@Param("list") List<Song> list);
}