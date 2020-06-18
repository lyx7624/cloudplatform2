package com.zcyk.service.impl;

import com.zcyk.dto.LoginUser;
import com.zcyk.dao.PermissionDao;
import com.zcyk.entity.Permission;
import com.zcyk.entity.User;
import com.zcyk.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
* 功能描述: 登录处理
* 版本信息: Copyright (c)2019
* 公司信息: 智辰云科
* 开发人员: lyx
* 版本日志: 1.0
* 创建日期: 2019/8/1 17:20
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Resource
	private PermissionDao permissionDao;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		//查询用户是否存在
		User sysUser = userService.getByAccount(account);
		if (sysUser == null) {
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
//
		}

		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(sysUser, loginUser);

		/*查询用户权限*/
		List<Permission> permissions = permissionDao.listByUserId(sysUser.getId());
		loginUser.setPermissions(permissions);

		return loginUser;
	}

}
