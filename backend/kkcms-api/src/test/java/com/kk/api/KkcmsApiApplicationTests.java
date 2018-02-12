package com.kk.api;

import com.kk.api.entity.Comment;
import com.kk.api.entity.Moment;
import com.kk.api.entity.Song;
import com.kk.api.service.ICommentService;
import com.kk.api.service.IMomentService;
import com.kk.api.service.ISongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsApiApplicationTests {

    @Autowired
    private ISongService songService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IMomentService momentService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void testSelectOne() {
        Song song = songService.selectById(1);
        System.out.print("******" + song.toString());
    }

    @Test
    public void getMessage() {
        List<Comment> commentList = commentService.selectListAll();
        List<Moment> momentList = momentService.selectListAll();
    }

}
