package com.kk.api.mapper;

import com.kk.api.entity.Song;
import org.springframework.data.repository.CrudRepository;
import tk.mybatis.mapper.common.Mapper;

public interface SongMapper extends Mapper<Song>{

    public Long count();
}