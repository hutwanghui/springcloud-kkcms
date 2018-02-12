package com.kk.api.service.impl;

import com.kk.api.entity.Song;
import com.kk.api.mapper.SongMapper;
import com.kk.api.service.ISongService;
import com.kk.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/2/8.
 */
@Service
public class SongServiceImpl extends BaseServiceImpl<SongMapper, Song> implements ISongService {
    @Override
    public Long count() {
        return mapper.count();
    }
}
