package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CityDriverUserService;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/21 - 16:16
 */
@RestController
public class CityDriverUserController {

	@Autowired
	private CityDriverUserService cityDriverUserService;

	@GetMapping("/city-driver/is-available-driver")
	public ResponseResult isAvailableDriver(@RequestParam String cityCode) {
		return cityDriverUserService.isAvailableDriver(cityCode);
	}
}
