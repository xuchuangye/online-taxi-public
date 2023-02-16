package com.mashibing.apidriver.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/16 - 17:53
 */
@RestController
public class DriverUserController {

	@GetMapping("/test")
	public String test() {
		return "api driver";
	}
}
