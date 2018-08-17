package com.kk.controllers;

import com.kk.models.MovieInfo;
import com.kk.models.Score;
import com.kk.models.UserInfo;
import com.kk.services.MovieInfoService;
import com.kk.services.ScoreService;
import com.kk.services.UserInfoSevice;
import com.kk.utils.JsonMessage;
import com.kk.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by hutwanghui on 2018/7/14 13:35.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Controller
@RequestMapping("/")
public class IndexController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private MovieInfoService movieInfoService;
	@Autowired
	private UserInfoSevice userInfoSevice;
	@Autowired
	private ScoreService scoreService;

	@RequestMapping("/")
	public String index(){
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public ModelAndView pageIndex(
						HttpSession session,
						@RequestParam(value = "keyword",required=false) String keyword,
						@RequestParam(value="pageNumberstr",required=false) String pageNumberstr){
		ModelAndView modelAndView = new ModelAndView();
		UserInfo logined = (UserInfo) session.getAttribute("user");
		if (logined != null) {
//			List<MovieInfo> reMovie = scoreService.findAndDoRecommend(logined.getUserId());
//			modelAndView.addObject("removies",reMovie);
			StringBuffer sb = new StringBuffer();
			List<Score> scores = scoreService.findScore(logined.getUserId());
			if (scores != null) {
				for (Score s :
						scores) {
					sb.append(s.toIdAndScore()).append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				modelAndView.addObject("scores", sb.toString());
			}
		}

		/*UserInfo loginUser = new UserInfo();
		loginUser.setUserName("Gintom");
		modelAndView.addObject("user", loginUser);*/
		if(pageNumberstr==null || "".equals(pageNumberstr)){
			pageNumberstr="0";
		}
		int pageNumber = Integer.parseInt(pageNumberstr);
		int pageSize = 8;
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		Page<MovieInfo> movieInfos;
		if (keyword != null && !"".equals(keyword)){
			System.out.println("findSearch");
			movieInfos = movieInfoService.findSearch(keyword, pageRequest);
		}else {
			movieInfos = movieInfoService.findAll(pageRequest);
		}
		System.out.println("m_size="+movieInfos.getTotalPages());
		if (movieInfos.getTotalPages() > 0){
			modelAndView.addObject("movies", movieInfos.getContent());
		}
		if (keyword != null && !"".equals(keyword)) {
			modelAndView.addObject("keyword", keyword);
		}
		modelAndView.addObject("totalPageNumber", movieInfos.getTotalElements());
		modelAndView.addObject("pageNumberstr", pageNumber);
		modelAndView.addObject("pagesize", pageSize);
		modelAndView.addObject("TotalPages",movieInfos.getTotalPages()-1);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/*@RequestMapping("/login")
	public String loginPage(UserInfo userInfo, Model model){

		if (!userInfo.isEmpty()){
			System.out.println("完成注册的用户"+userInfo);
			model.addAttribute("reuser", userInfo);
		}
		return "login";
	}*/
	@RequestMapping("/login")
	public String loginPage(){
		return "login";
	}

	@RequestMapping("/dologin")
	@ResponseBody
	public JsonMessage doLogin(UserInfo userInfo, HttpSession session){
		JsonMessage jsonMessage = new JsonMessage();
		logger.debug("当前进行登陆用户为："+userInfo);
		System.out.println("当前登陆用户为："+userInfo);
		userInfo.setPassword(Md5Utils.md5Password(userInfo.getPassword()));
		UserInfo loginedUser = userInfoSevice.findByNameAndPwd(userInfo);
		if (loginedUser != null){
			logger.debug("成功登陆用户为："+loginedUser);
			session.setAttribute("user", loginedUser);
			jsonMessage.setStatu("200");
			jsonMessage.setMessage("登陆成功");
		}else {
			logger.debug("登陆失败");
			jsonMessage.setStatu("500");
			jsonMessage.setMessage("登陆失败,用户名或密码错误");
		}
		return jsonMessage;
	}

	@RequestMapping("/register")
	public String registerPage(){
		return "register";
	}

	@RequestMapping("/doregister")
	@ResponseBody
	public JsonMessage doRegister(UserInfo registerUserInfo){
		JsonMessage jsonMessage = new JsonMessage();
		if (registerUserInfo != null) {
			logger.debug("注册的用户信息：{}", registerUserInfo);
			System.out.println("注册的用户信息：" + registerUserInfo);
			UserInfo u = userInfoSevice.findByName(registerUserInfo);
			if (u != null) {
				jsonMessage.setStatu("500");
				jsonMessage.setMessage("该用户名已存在!");
			} else {
				registerUserInfo.setPassword(
						Md5Utils.md5Password(registerUserInfo.getPassword()));
				UserInfo savedUser = userInfoSevice.saveUser(registerUserInfo);
				logger.debug("完成注册的用户：{}", savedUser);
				System.out.println("完成注册的用户：" + savedUser);
				jsonMessage.setMessage("注册成功");
				jsonMessage.setData(savedUser);
			}
		}else{
			jsonMessage.setStatu("500");
			jsonMessage.setMessage("信息不能为空");
		}
		return jsonMessage;
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:/login";
	}


}
