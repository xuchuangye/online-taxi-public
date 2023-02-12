package com.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
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

	/**
	 * 测试拦截的请求
	 * @return
	 */
	@GetMapping("/authTest")
	public ResponseResult authTest() {
		return ResponseResult.success("auth test");
	}

	/**
	 * 测试不拦截的请求
	 * @return
	 */
	@GetMapping("/noauthTest")
	public ResponseResult noauthTest() {
		return ResponseResult.success("noauth test");
	}
}
