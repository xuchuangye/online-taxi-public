package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.internalcommon.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xcy
 * @date 2023/2/13 - 8:58
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseResult getUsers(HttpServletRequest request) {
		//从http请求中获取Header的Authorization属性，其实就是token
		String token = request.getHeader("Authorization");

		return userService.getUsersByAccessToken(token);
	}
}
