package com.kk.api.service;

import com.kk.api.entity.Song;
import com.kk.common.service.BaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by msi- on 2018/2/8.
 */
public interface ISongService extends BaseService<Song> {
    public Long count();

    public void deleteCTQ(List<String> iditem);

    public void updateCTQ(List<Song> items);
}
