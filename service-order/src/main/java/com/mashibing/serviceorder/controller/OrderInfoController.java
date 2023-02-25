package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xcy
 * @date 2023/2/20 - 7:54
 */
@RestController
@RequestMapping("/order")
public class OrderInfoController {

	@Autowired
	private OrderInfoService orderInfoService;

	/**
	 * 创建订单
	 *
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/add")
	public ResponseResult add(@RequestBody OrderRequest orderRequest/*, HttpServletRequest httpServletRequest*/) {
		//下面代码通过测试
		/*String deviceCode = httpServletRequest.getHeader("deviceCode");
		orderRequest.setDeviceCode(deviceCode);*/
		return orderInfoService.addOrder(orderRequest);
	}

	/**
	 * 司机去接乘客
	 *
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/to-pick-up-passenger")
	public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.toPickUpPassenger(orderRequest);
	}

	/**
	 * 司机到达乘客出发点
	 *
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/driver-arrived-departure")
	public ResponseResult driverArrivedDeparture(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.driverArrivedDeparture(orderRequest);
	}

	/**
	 * 司机接到乘客
	 *
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/pick-up-passenger")
	public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.pickUpPassenger(orderRequest);
	}

	/**
	 * 乘客下车
	 *
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/passenger-getoff")
	public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.passengerGetoff(orderRequest);
	}

	/**
	 * 司机发起收款
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/push-pay-info")
	public ResponseResult pushPayInfo(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.pushPayInfo(orderRequest);
	}

	/**
	 * 订单支付完成
	 *
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("/pay")
	public ResponseResult pay(@RequestBody OrderRequest orderRequest) {
		return orderInfoService.pay(orderRequest);
	}

	/**
	 * 订单取消
	 *
	 * @param orderId  订单id
	 * @param identity 发起订单取消的身份标识
	 * @return
	 */
	@PostMapping("/cancel")
	public ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity) {
		return  orderInfoService.cancel(orderId, identity);
	}
}
