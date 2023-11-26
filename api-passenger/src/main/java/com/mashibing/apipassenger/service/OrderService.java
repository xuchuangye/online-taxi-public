package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/20 - 7:30
 */
@Service
public class OrderService {

	@Autowired
	private ServiceOrderClient serviceOrderClient;

	/**
	 * 创建订单
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult add(OrderRequest orderRequest) {
		return serviceOrderClient.addOrder(orderRequest);
	}

	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	public ResponseResult cancel(Long orderId) {
		return serviceOrderClient.cancel(orderId, IdentityConstant.PASSENGER_IDENTITY);
	}
}
