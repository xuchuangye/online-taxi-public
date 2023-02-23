package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xcy
 * @date 2023/2/20 - 7:54
 */
@RestController
public class OrderInfoController {

	@Autowired
	private OrderInfoService orderInfoService;

	@PostMapping("/order/add")
	public ResponseResult add(@RequestBody OrderRequest orderRequest/*, HttpServletRequest httpServletRequest*/) {
		//下面代码通过测试
		/*String deviceCode = httpServletRequest.getHeader("deviceCode");
		orderRequest.setDeviceCode(deviceCode);*/
		return orderInfoService.addOrder(orderRequest);
	}

	@PostMapping("/to-pick-up-passenger")
	public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.toPickUpPassenger(orderRequest);
	}
}
