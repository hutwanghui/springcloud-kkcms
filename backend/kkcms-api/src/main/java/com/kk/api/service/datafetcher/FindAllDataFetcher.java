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
import java.util.List;

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
            List<Moment> momentList = momentService.selectListAll();
            List<MomentItem> momentItemList = new ArrayList<>();

            for (Moment moment : momentList) {
                MomentItem momentItem = new MomentItem();
                List<Comment> commentList = commentService.selectByMomentId(moment.getId());
                List<CommentItem> commentItem = new ArrayList<>();
                for (Comment comment : commentList) {
                    CommentItem commentItem1 = new CommentItem();
                    commentItem1.setComment(comment);
                    commentItem1.setUser(userService.selectById(comment.getUserId().intValue()));
                    commentItem1.setToUser(userService.selectById(comment.getToid().intValue()));
                    commentItem.add(commentItem1);
                }
                momentItem.setMoment(moment);
                momentItem.setUser(userService.selectById(moment.getUserId().intValue()));
                System.out.print(moment.getPraiseuseridlist());
                momentItem.setCommentItemList(commentItem);
                momentItemList.add(momentItem);
            }
            return (List<T>) momentItemList;
        } else if (chooseService.equals("MomentByUserId")) {
            Moment moment = new Moment();
            moment.setUserId(dataFetchingEnvironment.getArgument("userId"));
            return (List<T>) momentService.selectList(moment);
        } else {
            return null;
        }
    }
}
