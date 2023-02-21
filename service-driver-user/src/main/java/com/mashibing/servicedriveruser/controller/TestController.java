package com.mashibing.servicedriveruser.controller;

import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/21 - 15:20
 */
@RestController
public class TestController {

	@Autowired
	private DriverUserMapper driverUserMapper;

	@GetMapping("/test")
	public Integer selectDriverUserCount(String cityCode) {
		return driverUserMapper.selectDriverUserCountByCityCode(cityCode);
	}
}
