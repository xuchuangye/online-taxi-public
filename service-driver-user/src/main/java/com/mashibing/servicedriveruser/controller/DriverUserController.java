package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/16 - 10:54
 */
@RestController
public class DriverUserController {

	@Autowired
	private DriverUserService driverUserService;


	@GetMapping("/test")
	public ResponseResult testGet() {
		return driverUserService.testGetDriverUser();
	}
}
