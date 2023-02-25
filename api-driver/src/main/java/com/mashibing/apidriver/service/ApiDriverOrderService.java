package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceOrderClient;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/23 - 20:06
 */
@Service
public class ApiDriverOrderService {

	@Autowired
	private ServiceOrderClient serviceOrderClient;

	/**
	 * 司机去接乘客
	 *
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
		return serviceOrderClient.toPickUpPassenger(orderRequest);
	}

	/**
	 * 司机到达乘客出发点
	 *
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult driverArrivedDeparture(OrderRequest orderRequest) {
		return serviceOrderClient.driverArrivedDeparture(orderRequest);
	}

	/**
	 * 司机接到乘客
	 *
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
		return serviceOrderClient.pickUpPassenger(orderRequest);
	}

	/**
	 * 司机行程结束，乘客下车
	 *
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult passengerGetoff(OrderRequest orderRequest) {
		return serviceOrderClient.passengerGetoff(orderRequest);
	}

	/**
	 * 司机取消订单
	 * @param orderId
	 * @return
	 */
	public ResponseResult cancel(Long orderId) {
		return serviceOrderClient.cancel(orderId, IdentityConstant.DRIVER_IDENTITY);
	}
}
