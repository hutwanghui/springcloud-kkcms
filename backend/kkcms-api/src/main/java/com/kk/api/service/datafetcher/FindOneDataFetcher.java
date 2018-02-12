package com.kk.api.service.datafetcher;

import com.kk.api.entity.Song;
import com.kk.api.service.ISongService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by msi- on 2018/2/8.
 */
@Component
public class FindOneDataFetcher implements DataFetcher<Song> {

    @Autowired
    private ISongService songService;

    @Override
    public Song get(DataFetchingEnvironment dataFetchingEnvironment) {
        //对应graphqls里面的findSongById (id : ID): Song的这个id
        String id = dataFetchingEnvironment.getArgument("id");
        return songService.selectById(Integer.valueOf(id));
    }
}
