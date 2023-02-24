package com.mashibing.servicealipay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/24 - 16:55
 */
@RestController
@RequestMapping("/alipay")
@Slf4j
public class CallbackController {


	@PostMapping("/test")
	public String testCallback() {
		log.info("回调方法执行");
		return "test callback";
	}
}
