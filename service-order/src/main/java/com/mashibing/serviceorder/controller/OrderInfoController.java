package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/20 - 7:54
 */
@RestController
public class OrderInfoController {

	@Autowired
	private OrderInfoService orderInfoService;

	@PostMapping("/order/add")
	public ResponseResult addOrder(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.addOrder(orderRequest);
	}
}
