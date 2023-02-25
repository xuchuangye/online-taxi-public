package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/23 - 20:07
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

	/**
	 * 司机去接乘客
	 *
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/order/to-pick-up-passenger")
	public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest);

	/**
	 * 司机到达乘客出发点
	 *
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/order/driver-arrived-departure")
	public ResponseResult driverArrivedDeparture(@RequestBody OrderRequest orderRequest);

	/**
	 * 司机接到乘客，乘客上车
	 *
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/order/pick-up-passenger")
	public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest);

	/**
	 * 乘客下车到达目的地，行程结束
	 *
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/order/passenger-getoff")
	public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest);


	/**
	 * 司机取消订单
	 *
	 * @param orderId
	 * @param identity
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/order/cancel")
	public ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity);
}
