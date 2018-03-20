package com.kk.api.service.datafetcher;

import com.kk.api.entity.*;
import com.kk.api.service.ICommentService;
import com.kk.api.service.IMomentService;
import com.kk.api.service.ISongService;
import com.kk.api.service.IUserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by msi- on 2018/2/8.
 */
@Component
public class FindAllDataFetcher<T> implements DataFetcher<List<T>> {

    @Autowired
    private ISongService songService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IMomentService momentService;
    @Autowired
    private IUserService userService;

    @Override
    public List<T> get(DataFetchingEnvironment dataFetchingEnvironment) {
        String path = dataFetchingEnvironment.getFieldTypeInfo().getPath().toString();
        String chooseService = StringUtils.substringAfterLast(path, "findAll");
        if (chooseService.equals("Song")) {

            return (List<T>) songService.selectListAll();
        } else if (chooseService.equals("Comment")) {
            return (List<T>) commentService.selectListAll();
        } else if (chooseService.equals("CommentByUserId")) {
            Comment comment = new Comment();
            comment.setUserId(dataFetchingEnvironment.getArgument("userId"));
            return (List<T>) commentService.selectList(comment);
        } else if (chooseService.equals("Moment")) {
            List<Comment> momentItemList = commentService.getMomentItem();
            Map<Moment, List<CommentItem>> map = momentItemList.stream().collect(Collectors.groupingBy(comment -> comment.getMoment(), Collectors.mapping(comment -> comment.getBase(), Collectors.toList())));
            List<MomentItem> momentItems = new ArrayList<>();
            map.forEach((key, value) -> {
                        MomentItem momentItem = new MomentItem();
                        momentItem.setMoment(key);
                        momentItem.setUser(key.getUser());
                        momentItem.setCommentItemList(value);
                        List<User> pariseList = new ArrayList<>();
                        List<String> userIdList = Arrays.asList(key.getPraiseuseridlist().substring(1, key.getPraiseuseridlist().length() - 1).split(", "));
                        userIdList.parallelStream().forEach(userid -> pariseList.add(userService.findPraiseuserUserlist(Integer.valueOf(userid))));
                        momentItem.setUserList(pariseList);
                        momentItems.add(momentItem);
                    }
            );
            return (List<T>) momentItems;
        } else if (chooseService.equals("MomentByUserId")) {
            Moment moment = new Moment();
            moment.setUserId(dataFetchingEnvironment.getArgument("userId"));
            return (List<T>) momentService.selectList(moment);
        } else {
            return null;
        }
    }
}
