package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/16 - 17:53
 */
@RestController
public class DriverUserController {

	@Autowired
	private DriverUserService driverUserService;

	@PutMapping("/user")
	public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
		return driverUserService.updateUser(driverUser);
	}
}
