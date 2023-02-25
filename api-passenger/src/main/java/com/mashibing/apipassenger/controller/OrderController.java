package com.mashibing.apipassenger.controller;

/**
 * @author xcy
 * @date 2023/2/20 - 7:22
 */

import com.mashibing.apipassenger.service.OrderService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order/add")
	public ResponseResult add(@RequestBody OrderRequest orderRequest) {
		return orderService.add(orderRequest);
	}

	@PostMapping("/order/cancel")
	public ResponseResult cancel(@RequestParam Long orderId) {
		return orderService.cancel(orderId);
	}
}
