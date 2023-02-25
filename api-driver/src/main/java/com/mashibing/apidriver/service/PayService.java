package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceOrderClient;
import com.mashibing.apidriver.remote.ServiceSsePushClient;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/24 - 14:33
 */
@Service
public class PayService {

	@Autowired
	private ServiceSsePushClient serviceSsePushClient;

	@Autowired
	private ServiceOrderClient serviceOrderClient;

	/**
	 * 司机发起收款
	 *
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult pushPayInfo(OrderRequest orderRequest) {
		Long orderId = orderRequest.getOrderId();
		Long passengerId = orderRequest.getPassengerId();
		String price = orderRequest.getPrice();
		JSONObject result = new JSONObject();
		result.put("价格：" , price);
		result.put("订单id：", orderId);

		orderRequest.setOrderId(orderId);
		//修改订单状态
		serviceOrderClient.pushPayInfo(orderRequest);

		//将消息推送给乘客
		serviceSsePushClient.push(passengerId, IdentityConstant.PASSENGER_IDENTITY, result.toString());
		return ResponseResult.success("");
	}
}
