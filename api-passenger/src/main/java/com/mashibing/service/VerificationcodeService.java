package com.mashibing.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/6 - 9:39
 */
@Service
public class VerificationcodeService {

	public String generateVerificationcode() {

		System.out.println("乘客获取验证码，调用验证码服务");

		//存入Redis
		System.out.println("存入Redis");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 1);
		jsonObject.put("message", "success");

		return jsonObject.toString();
	}
}
