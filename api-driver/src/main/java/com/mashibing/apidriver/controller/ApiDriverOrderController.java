package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.ApiDriverOrderService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
