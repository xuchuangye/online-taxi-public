package com.mashibing.servicealipay.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.servicealipay.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/25 - 8:11
 */
@Service
public class AlipayService {

	@Autowired
	private ServiceOrderClient serviceOrderClient;

	public ResponseResult pay(Long orderId) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderId(orderId);

		return serviceOrderClient.pay(orderRequest);
	}
}
