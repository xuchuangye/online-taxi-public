package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.request.DriverCarBindingRelationship;
import com.mashibing.apidriver.request.DriverUser;
import com.mashibing.apidriver.request.DriverUserWorkStatus;
import com.mashibing.apidriver.service.DriverUserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xcy
 * @date 2023/2/16 - 17:53
 */
@RestController
public class DriverUserController {

	@Autowired
	private DriverUserService driverUserService;

	@PutMapping("/user")
	public ResponseResult updateUser(@Validated @RequestBody DriverUser driverUser) {
		return driverUserService.updateUser(driverUser);
	}

	@PutMapping("/driver-user-work-status")
	public ResponseResult changeWorkStatus(@Validated @RequestBody DriverUserWorkStatus driverUserWorkStatus) {
		return driverUserService.changeWorkStatus(driverUserWorkStatus);
	}

	/**
	 * 因为token是根据手机号、身份标识生成的，所以解析token可以获取手机号。因此不需要参数
	 * 1.解析token获取司机手机号
	 * 2.根据司机手机号获取司机id
	 * 3.根据司机di获取司机和车辆的绑定关系
	 *
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/driver-car-binding-relationship")
	public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		TokenResult tokenResult = JWTUtils.checkToken(token);
		String driverPhone = tokenResult.getPhone();

		return driverUserService.getDriverCarBindingRelationship(driverPhone);
	}
}
