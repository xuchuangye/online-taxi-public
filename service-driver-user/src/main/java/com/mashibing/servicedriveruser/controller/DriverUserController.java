package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
