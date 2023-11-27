package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.apipassenger.request.OrderRequest;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
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
	 * 乘客下订预约单
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult book(OrderRequest orderRequest) {
		return serviceOrderClient.bookOrder(orderRequest);
	}

	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	public ResponseResult cancel(Long orderId) {
		return serviceOrderClient.cancel(orderId, IdentityConstant.PASSENGER_IDENTITY);
	}

	/**
	 * 订单详情
	 * @param orderId
	 * @return
	 */
	public ResponseResult<OrderInfo> detail(Long orderId){
		return serviceOrderClient.detail(orderId);
	}

	/**
	 * 当前正在进行的订单
	 * @param phone
	 * @param identity
	 * @return
	 */
	public ResponseResult<OrderInfo> currentOrder(String phone , String identity){
		return serviceOrderClient.current(phone,identity);
	}
}
