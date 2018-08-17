package com.kk.controllers;

import com.kk.models.UserInfo;
import com.kk.services.UserInfoSevice;
import com.kk.utils.JsonMessage;
import com.kk.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by hutwanghui on 2018/7/14 13:37.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
	private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private UserInfoSevice userInfoSevice;

	@RequestMapping("/personal")
	public ModelAndView personal(Integer userId, HttpSession session){
		UserInfo loginedUser = (UserInfo) session.getAttribute("user");
		ModelAndView modelAndView = new ModelAndView();
		if (loginedUser != null) {
			logger.debug("获取{}的个人信息", userId);
			System.out.println("userId=" + userId);
			modelAndView.setViewName("personalcenter");
			UserInfo userInfo = null;
			if (userId != null) {
				userInfo = userInfoSevice.findById(userId);
			}
			if (userInfo != null) {
				if ("gin".equals(userInfo.getUserName())){
					logger.debug("当前为管理员用户");
					modelAndView.addObject("isAdmin", "true");
				}
				modelAndView.addObject("userinfo", userInfo);
			}
		}else {
			logger.debug("获取{}的个人信息", userId);
			modelAndView.setViewName("notfound");
		}
		return modelAndView;
	}

	@RequestMapping("/savedata")
	@ResponseBody
	public JsonMessage updateUser(UserInfo userInfo){
		JsonMessage jsonMessage = new JsonMessage();
		if (userInfo != null){
			UserInfo user = userInfoSevice.findById(userInfo.getUserId());
			userInfo.setPassword(user.getPassword());
			logger.debug("当前要保存的用户信息为: {}", userInfo);
			System.out.println("当前要保存的用户信息为: "+userInfo);
			UserInfo u = userInfoSevice.saveUser(userInfo);
			if (u != null){
				jsonMessage.setMessage("用户"+userInfo.getUserId()+"信息更新成功");
				jsonMessage.setData(u);
			}else {
				jsonMessage.setStatu("500");
				jsonMessage.setMessage("用户"+userInfo.getUserId()+"信息更新失败");
			}
		}else{
			jsonMessage.setStatu("500");
			jsonMessage.setMessage("保存的对象不能为空");
		}
		return jsonMessage;
	}

	@RequestMapping("/checkpwd")
	@ResponseBody
	public JsonMessage checkPwd(Integer userId, String pwd){
//		System.out.println("userId: "+userId);
//		System.out.println("pwd: "+pwd);
		JsonMessage jsonMessage = new JsonMessage();
		UserInfo userInfo = userInfoSevice.checkPwd(userId, Md5Utils.md5Password(pwd));
		if (userInfo != null){
			jsonMessage.setMessage("旧密码正确");
		}else {
			jsonMessage.setStatu("500");
			jsonMessage.setMessage("密码输入不正确");
		}
		return jsonMessage;
	}


	@RequestMapping("/changepwd")
	@ResponseBody
	public JsonMessage changePwd(Integer userId, String newPwd, HttpSession session){
//		System.out.println("userId: "+userId);
//		System.out.println("newPwd: "+newPwd);
		JsonMessage jsonMessage = new JsonMessage();
		UserInfo userInfo = userInfoSevice.changePwd(userId, Md5Utils.md5Password(newPwd));
		if (userInfo != null){
			jsonMessage.setMessage("修改密码成功");
			session.removeAttribute("user");
		}else {
			jsonMessage.setStatu("500");
			jsonMessage.setMessage("修改密码失败");
		}
		return jsonMessage;
	}
}
