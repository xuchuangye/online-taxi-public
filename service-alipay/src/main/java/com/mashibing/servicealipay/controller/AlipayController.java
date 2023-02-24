package com.mashibing.servicealipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/24 - 16:05
 */
@Controller
@RequestMapping("/alipay")
@ResponseBody
public class AlipayController {

	/**
	 * @param subject     支付的主题
	 * @param outTradeNo  贸易号
	 * @param totalAmount 总金额
	 * @return
	 */
	@GetMapping("/pay")
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

	@PostMapping("/notify")
	public String notify(HttpServletRequest request) throws Exception {
		System.out.println("支付宝回调notify");
		String tradeStatus = request.getParameter("trade_status");

		if (tradeStatus.trim().equals("TRADE_SUCCESS")) {
			Map<String, String> paramMap = new HashMap<>();

			Map<String, String[]> parameterMap = request.getParameterMap();

			for (String name : parameterMap.keySet()) {
				paramMap.put(name, request.getParameter(name));
			}

			if (Factory.Payment.Common().verifyNotify(paramMap)) {
				System.out.println("通过了支付宝的验证");

				for (String name : paramMap.keySet()) {
					System.out.println("收到的参数：");
					System.out.println(name + ": " + paramMap.get(name));
				}
			}else {
				System.out.println("支付宝验证没有通过");
			}

		}
		return "success";
	}
}
