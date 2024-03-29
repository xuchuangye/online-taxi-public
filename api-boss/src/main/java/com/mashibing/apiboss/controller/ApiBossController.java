package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.ApiBossService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author xcy
 * @date 2023/2/16 - 15:34
 */
@RestController
public class ApiBossController {

	@Autowired
	private ApiBossService apiBossService;

	@PostMapping("/driver-user")
	public ResponseResult addDriverUser(@RequestBody DriverUser driverUser) {
		return apiBossService.addDriverUser(driverUser);
	}

	@PutMapping("/driver-user")
	public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
		return apiBossService.updateDriverUser(driverUser);
	}
}
