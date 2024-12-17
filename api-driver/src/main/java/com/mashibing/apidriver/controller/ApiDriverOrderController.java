package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.ApiDriverOrderService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xcy
 * @date 2023/2/23 - 20:05
 */
@RestController
@RequestMapping("/order")
public class ApiDriverOrderController {

	@Autowired
	private ApiDriverOrderService apiDriverOrderService;

	/**
	 * 司机抢单
	 * @param orderRequest
	 * @param httpServletRequest 根据该对象获取token信息
	 * @return
	 */
	@PostMapping("/grab")
	public ResponseResult grabOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
		//获取token信息
		String token = httpServletRequest.getHeader("Authorization");
		TokenResult tokenResult = JWTUtils.parseToken(token);

		//获取司机的身份标识和手机号
		String identity = tokenResult.getIdentity();
		String phone = tokenResult.getPhone();

		System.out.println("司机的身份标识: " + identity);
		System.out.println("司机的手机号: " + phone);

		Long orderId = orderRequest.getOrderId();
		System.out.println("订单ID: " + orderId);

		return ResponseResult.success(orderRequest);
	}

	/**
	 * 司机去接乘客
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/to-pick-up-passenger")
	public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
		return apiDriverOrderService.toPickUpPassenger(orderRequest);
	}

	/**
	 * 司机到达乘客出发点
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/driver-arrived-departure")
	public ResponseResult driverArrivedDeparture(@RequestBody OrderRequest orderRequest) {
		return apiDriverOrderService.driverArrivedDeparture(orderRequest);
	}

	/**
	 * 司机接到乘客
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/pick-up-passenger")
	public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
		return apiDriverOrderService.pickUpPassenger(orderRequest);
	}

	/**
	 * 乘客下车
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/passenger-getoff")
	public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
		return apiDriverOrderService.passengerGetoff(orderRequest);
	}

	/**
	 * 司机取消订单
	 * @param orderId
	 * @return
	 */
	@PostMapping("/cancel")
	public ResponseResult cancel(@RequestParam Long orderId) {
		return apiDriverOrderService.cancel(orderId);
	}
}
