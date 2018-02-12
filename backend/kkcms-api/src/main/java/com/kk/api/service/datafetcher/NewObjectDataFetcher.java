package com.kk.api.service.datafetcher;

import com.kk.api.service.ICommentService;
import com.kk.api.service.IMomentService;
import com.kk.api.service.IPariseService;
import com.kk.api.service.IUserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by msi- on 2018/2/11.
 */
@Component
public class NewObjectDataFetcher<T> implements DataFetcher<T> {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMomentService momentService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IPariseService pariseService;


    @Override
    public T get(DataFetchingEnvironment dataFetchingEnvironment) {
        String path = dataFetchingEnvironment.getFieldTypeInfo().getPath().toString();
        String chooseService = StringUtils.substringAfterLast(path, "new");
        if (chooseService.equals("Comment")) {
            //System.out.print("看看有点什么" + dataFetchingEnvironment.getArgument("comment"));
            commentService.insert(dataFetchingEnvironment.getArgument("comment"));
        } else if (chooseService.equals("Moment")) {

        } else if (chooseService.equals("Parise")) {

        } else {
            return null;
        }
        return null;
    }
}
