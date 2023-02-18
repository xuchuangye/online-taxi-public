package com.mashibing.apidriver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/18 - 11:05
 */
@RestController
public class TestController {


	@GetMapping("/auth")
	public String auth() {
		return "auth";
	}

	@GetMapping("/noauth")
	public String noauth() {
		return "noauth";
	}
}
