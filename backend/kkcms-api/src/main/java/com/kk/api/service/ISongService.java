package com.kk.api.service;

import com.kk.api.entity.Song;
import com.kk.common.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/2/8.
 */
public interface ISongService extends BaseService<Song> {
    public Long count();
}
