package com.kk.api.service.datafetcher;

import com.kk.api.entity.Comment;
import com.kk.api.entity.Song;
import com.kk.api.service.ISongService;
import com.kk.api.service.IUserActionService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by msi- on 2018/2/8.
 */
@Component
public class FindOneDataFetcher<T> implements DataFetcher<T> {

    @Autowired
    private ISongService songService;
    @Autowired
    private IUserActionService userActionService;

    @Override
    public T get(DataFetchingEnvironment dataFetchingEnvironment) {
        //对应graphqls里面的findSongById (id : ID): Song的这个id

       /* String id = dataFetchingEnvironment.getArgument("id");
        return (T) songService.selectById(Integer.valueOf(id));*/
        String path = dataFetchingEnvironment.getFieldTypeInfo().getPath().toString();
        String chooseService = StringUtils.substringAfterLast(path, "findOne");
        if (chooseService.equals("PurchesMouth")) {
            return (T) userActionService.selectBehaviorMouth("4");
        } else if (chooseService.equals("BrowseMouth")) {

            return (T) userActionService.selectBehaviorMouth("1");
        } else if (chooseService.equals("CollectMouth")) {
            return (T) userActionService.selectBehaviorMouth("2");
        } else if (chooseService.equals("Behavior_1UpMouth")) {
            return (T) userActionService.selectBehaviorUpMouth("1");
        } else if (chooseService.equals("Behavior_2UpMouth")) {
            return (T) userActionService.selectBehaviorUpMouth("2");
        } else if (chooseService.equals("Behavior_3UpMouth")) {
            return (T) userActionService.selectBehaviorUpMouth("3");
        } else if (chooseService.equals("Behavior_4UpMouth")) {
            return (T) userActionService.selectBehaviorUpMouth("4");
        } else if (chooseService.equals("Behavior_1DownMouth")) {
            return (T) userActionService.selectBehaviorDownMouth("1");
        } else if (chooseService.equals("Behavior_2DownMouth")) {
            return (T) userActionService.selectBehaviorDownMouth("2");
        } else if (chooseService.equals("Behavior_3DownMouth")) {
            return (T) userActionService.selectBehaviorDownMouth("3");
        } else if (chooseService.equals("Behavior_4DownMouth")) {
            return (T) userActionService.selectBehaviorDownMouth("4");
        } else {
            return (T) userActionService.selectBehaviorMouth("3");
        }
    }
}
