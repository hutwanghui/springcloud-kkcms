package com.kk.controllers;

import com.kk.models.MovieInfo;
import com.kk.services.LoadMovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by hutwanghui on 2018/7/14 13:37.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@Controller
@RequestMapping
public class LoadMovieFileController {

	@Autowired
	private LoadMovieInfoService loadMovieInfoService;

	@ResponseBody
	@RequestMapping(value = "/loadfile")
	public List<MovieInfo> loadMovieFile(){
		return loadMovieInfoService.loadInfo();
	}

	@ResponseBody
	@RequestMapping(value = "/loadpic")
	public List<String> loadPic(){
		return loadMovieInfoService.loadPics();
	}
}
