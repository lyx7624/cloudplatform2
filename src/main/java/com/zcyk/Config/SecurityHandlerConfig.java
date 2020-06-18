package com.zcyk.Config;

import com.alibaba.fastjson.JSONObject;
import com.zcyk.dto.LoginUser;
import com.zcyk.dto.Token;
import com.zcyk.filter.TokenFilter;
import com.zcyk.service.TokenService;
import com.zcyk.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security处理器
 *
 */
@Configuration
public class SecurityHandlerConfig {

	@Autowired
	private TokenService tokenService;


/**
	 * 功能描述：登录成功
	 * 开发人员： lyx
	 * 创建时间： 2019/8/1 17:47
	 */

	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
				LoginUser loginUser = (LoginUser) authentication.getPrincipal();

				Token token = tokenService.saveToken(loginUser);
				System.out.println(JSONObject.toJSONString(token));
				ResponseUtil.responseJson(request,response, HttpStatus.OK.value(), token);
			}
		};
	}


/**
	 * 功能描述：登录失败
	 * 开发人员： lyx
	 * 创建时间： 2019/8/1 17:47
	 */

	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
				String msg = null;
				if (exception instanceof BadCredentialsException) {
					msg = "密码错误";
				} else {
					msg = exception.getMessage();
				}
//				ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", msg);
				ResponseUtil.responseJson(request,response, HttpStatus.UNAUTHORIZED.value(), msg);
			}
		};

	}


/*
*
	 * 功能描述：未登录
	 * 开发人员： lyx
	 * 创建时间： 2019/8/1 17:47

*/

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
				ResponseUtil.responseJson(request,response, HttpStatus.UNAUTHORIZED.value(), " 请先登录");
			}
		};
	}


/**
	 * 功能描述：退出处理
	 * 开发人员： lyx
	 * 创建时间： 2019/8/1 17:47
	 */

	@Bean
	public LogoutSuccessHandler logoutSussHandler() {
		return new LogoutSuccessHandler() {

			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

				String token = TokenFilter.getToken(request);
				tokenService.deleteToken(token);

				ResponseUtil.responseJson(request,response, HttpStatus.OK.value(), "退出成功");
			}
		};

	}

}
