package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/20 - 7:54
 */
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order/add")
	public ResponseResult addOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.addOrder(orderRequest);
	}
}
