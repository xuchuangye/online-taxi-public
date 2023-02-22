package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/22 - 16:16
 */
@RestController
@Slf4j
public class TestController {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderInfoService orderInfoService;

	@Value("${server.port}")
	private String port;

	@GetMapping("/test-real-time-order/{orderId}")
	public String dispatchRealTimeOrder(@PathVariable("orderId") Long orderId) {
		log.info("service-order：端口：" + port + "：并发测试，orderId：" + orderId);
		OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
		orderInfoService.dispatchRealTimeOrder(orderInfo);
		return "test-real-time-order success";
	}
}
