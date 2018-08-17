package com.kk.modules.sys.controller;

import com.kk.common.annotation.SysLog;
import com.kk.common.utils.Constant;
import com.kk.common.utils.HttpContextUtils;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;
import com.kk.common.validator.Assert;
import com.kk.common.validator.ValidatorUtils;
import com.kk.common.validator.group.AddGroup;
import com.kk.common.validator.group.UpdateGroup;
import com.kk.modules.sys.entity.SysUserEntity;
import com.kk.modules.sys.form.PasswordForm;
import com.kk.modules.sys.service.SysUserRoleService;
import com.kk.modules.sys.service.SysUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) throws UnsupportedEncodingException {
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		PageUtils page = sysUserService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info() throws UnsupportedEncodingException {
		return R.ok().put("user", getUser(HttpContextUtils.getUser_name()));
	}

	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public R password(@RequestBody PasswordForm form) throws UnsupportedEncodingException {
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");

		//sha256加密
		String password =form.getPassword();
		//sha256加密
		String newPassword = form.getNewPassword();

		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}

		return R.ok();
	}

	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	public R info(@PathVariable("userId") Integer userId){
		SysUserEntity user = sysUserService.selectById(userId);

		//获取用户所属的角色列表
		List<Integer> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return R.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	public R save(@RequestBody SysUserEntity user) throws UnsupportedEncodingException {
		ValidatorUtils.validateEntity(user, AddGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.save(user);

		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	public R update(@RequestBody SysUserEntity user) throws UnsupportedEncodingException {
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);

		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	public R delete(@RequestBody Integer[] userIds) throws UnsupportedEncodingException {
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}

		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}

		sysUserService.deleteBatch(userIds);

		return R.ok();
	}
}
