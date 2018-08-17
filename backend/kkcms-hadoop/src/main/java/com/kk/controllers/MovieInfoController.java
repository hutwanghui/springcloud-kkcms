package com.kk.controllers;

import com.kk.models.Comment;
import com.kk.models.Info;
import com.kk.models.MovieInfo;
import com.kk.services.CommentService;
import com.kk.services.MovieInfoService;
import com.kk.services.UserInfoSevice;
import com.kk.utils.JsonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hutwanghui on 2018/7/14 13:35.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@Controller
@RequestMapping("/movieinfo")
public class MovieInfoController {
    private static Logger logger = LoggerFactory.getLogger(MovieInfoController.class);

    @Autowired
    private MovieInfoService movieInfoService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserInfoSevice userInfoSevice;

    @RequestMapping("/details")
    public String details(String movieid, Model model) {
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setMovieId(movieid);
        MovieInfo m = movieInfoService.findOne(movieInfo);
        model.addAttribute("movie", m);
        return "details";
    }

    @RequestMapping("/info")
    @ResponseBody
    public Info info(String movieName) throws IOException {
        return movieInfoService.getInfo(movieName);
    }

    @RequestMapping("/get")
    @ResponseBody
    public MovieInfo get(String movieId) {
        return movieInfoService.findById(movieId);
    }

    @RequestMapping("/savecom")
    @ResponseBody
    public JsonMessage saveCom(Comment comment) {
        JsonMessage result = new JsonMessage();
        if (comment != null) {
            logger.debug("要保存的评论为: {}", comment);
            System.out.println("要保存的评论为: " + comment);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            comment.setCreateDate(format.format(date));
            Comment savedCom = commentService.save(comment);
            result.setMessage("保存评论成功");
            result.setData(savedCom);
        } else {
            result.setStatu("500");
            result.setMessage("保存的不能为空");
        }
        return result;
    }

    @RequestMapping("/savemovie")
    @ResponseBody
    public JsonMessage saveMovie(MovieInfo movieInfo) {
        JsonMessage result = new JsonMessage();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(movieInfo.getDate(), pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        String date = k[2] + "-" + k[1].toUpperCase() + "-" + k[5].substring(0, 4);
        movieInfo.setDate(date);
        logger.debug("正在上传电影: {}", movieInfo);
        MovieInfo saved = movieInfoService.uploadMovie(movieInfo);
        if (saved != null) {
            result.setMessage("保存电影成功");
        } else {
            result.setStatu("500");
            result.setMessage("保存电影失败");
        }
        return result;
    }

    @RequestMapping("/getcom")
    @ResponseBody
    public JsonMessage findPage(
            @RequestParam(value = "movieId", required = true) String movieId,
            @RequestParam(value = "currentPage", required = false) String currentPage) {
        JsonMessage result = new JsonMessage();
        if (movieId != null) {
            logger.debug("要获取{}的评论信息", movieId);
            System.out.println("要获取" + movieId + "的评论信息");
            if (currentPage == null || "".equals(currentPage)) {
                currentPage = "0";
            }
            //当前页
            int pageNumber = Integer.parseInt(currentPage);
            //页大小
            int pageSize = 10;
            Sort sort = new Sort(Sort.Direction.DESC, "createDate");
            PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sort);
            Page<Comment> commentPage = commentService.findPage(pageRequest, movieId);
            for (Comment c : commentPage.getContent()) {
                c.setUserName(userInfoSevice.findById(c.getUserId()).getUserName());
            }
            Map<String, Object> comInfo = new HashMap<>();
            comInfo.put("data", commentPage.getContent());
            comInfo.put("totalCount", commentPage.getTotalElements());
            comInfo.put("pageSize", pageSize);
            comInfo.put("currentPage", pageNumber);
            result.setMessage("获取电影" + movieId + "的评论列表成功");
            result.setData(comInfo);
        } else {
            result.setStatu("500");
            result.setMessage("当前电影id为空，请重试");
        }
        return result;
    }


}
