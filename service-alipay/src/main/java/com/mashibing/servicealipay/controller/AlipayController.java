package com.mashibing.servicealipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xcy
 * @date 2023/2/24 - 16:05
 */
@Controller
@ResponseBody
public class AlipayController {

	/**
	 * @param subject     支付的主题
	 * @param outTradeNo  贸易号
	 * @param totalAmount 总金额
	 * @return
	 */
	@GetMapping("/alipay/pay")
	public String pay(String subject, String outTradeNo, String totalAmount) {
		AlipayTradePagePayResponse alipayTradePagePayResponse = null;
		try {
			alipayTradePagePayResponse = Factory.Payment.Page().pay(subject, outTradeNo, totalAmount, "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		assert alipayTradePagePayResponse != null;
		return alipayTradePagePayResponse.getBody();
	}
}
