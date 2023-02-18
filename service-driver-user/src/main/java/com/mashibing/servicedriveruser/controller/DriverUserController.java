package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/16 - 10:54
 */
@RestController
public class DriverUserController {

	@Autowired
	private DriverUserService driverUserService;


	@PostMapping("/user")
	public ResponseResult addDriverUser(@RequestBody DriverUser driverUser) {
		return driverUserService.addDriverUser(driverUser);
	}

	@PutMapping("/user")
	public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
		return driverUserService.updateDriverUser(driverUser);
	}

	@GetMapping("/user/{driver-phone}")
	public ResponseResult<DriverUser> selectDriverUser(@PathVariable("driver-phone") String driverPhone) {
		ResponseResult<DriverUser> driverUserResponseResult = driverUserService.selectDriverUser(driverPhone);
		DriverUser driverUserDB = driverUserResponseResult.getData();

		DriverUserResponse driverUserResponse = new DriverUserResponse();
		int isExists = 1;
		if (driverUserDB == null) {
			isExists = 0;
			//如果司机不存在，返回响应传入的司机手机号
			driverUserResponse.setDriverPhone(driverPhone);
		} else {
			//如果司机存在，返回响应查询的司机手机号
			driverUserResponse.setDriverPhone(driverUserDB.getDriverPhone());
		}
		driverUserResponse.setIsExists(isExists);

		return ResponseResult.success(driverUserResponse);
	}
}
