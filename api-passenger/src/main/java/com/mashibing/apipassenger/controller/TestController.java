package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/6 - 9:15
 */
@RestController
@Slf4j
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



	@Autowired
	private ServiceOrderClient serviceOrderClient;

	@GetMapping("/test-real-time-order/{orderId}")
	public String dispatchRealTimeOrder(@PathVariable("orderId") Long orderId) {
		log.info("api-passenger并发测试，orderId：" + orderId);

		serviceOrderClient.dispatchRealTimeOrder(orderId);
		return "test-real-time-order success";
	}
}
