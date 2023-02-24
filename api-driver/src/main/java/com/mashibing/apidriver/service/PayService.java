package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceSsePushClient;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
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

	/**
	 * 司机发起收款
	 *
	 * @param orderId
	 * @param passengerId
	 * @param price
	 * @return
	 */
	public ResponseResult pushPayInfo(String orderId, String passengerId, String price) {

		JSONObject result = new JSONObject();
		result.put("价格：" , price);
		result.put("订单id：", orderId);

		serviceSsePushClient.push(Long.parseLong(passengerId), IdentityConstant.PASSENGER_IDENTITY, result.toString());
		return ResponseResult.success("");
	}
}
