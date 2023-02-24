package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PayService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/24 - 14:32
 */
@RestController
public class PayController {

	@Autowired
	private PayService payService;

	@GetMapping("/pay/push-pay-info")
	public ResponseResult pushPayInfo(@RequestParam String orderId,
	                                  @RequestParam String passengerId,
	                                  @RequestParam String price) {
		return payService.pushPayInfo(orderId, passengerId, price);
	}
}
