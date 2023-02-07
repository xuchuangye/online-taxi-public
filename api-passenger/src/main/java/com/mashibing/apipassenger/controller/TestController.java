package com.mashibing.apipassenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/6 - 9:15
 */
@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "test api passenger";
	}
}
