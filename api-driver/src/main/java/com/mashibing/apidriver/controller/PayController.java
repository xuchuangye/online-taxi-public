package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PayService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/24 - 14:32
 */
@RestController
public class PayController {

	@Autowired
	private PayService payService;

	@PostMapping("/pay/push-pay-info")
	public ResponseResult pushPayInfo(@RequestBody OrderRequest orderRequest) {
		return payService.pushPayInfo(orderRequest);
	}
}
