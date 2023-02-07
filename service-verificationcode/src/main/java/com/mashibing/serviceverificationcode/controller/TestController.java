package com.mashibing.serviceverificationcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/7 - 9:20
 */
@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "test service verificationcode";
	}
}
