package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/8 - 9:30
 */
@RestController
public class NumberCodeController {

	@GetMapping("/numberCode/{size}")
	public ResponseResult numberCode(@PathVariable("size") int size) {

		System.out.println("size: " + size);

		//生成验证码
		double random = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
		System.out.println(random);
		int randomVerificationcode = (int) random;
		System.out.println(randomVerificationcode);

		/*JSONObject result = new JSONObject();
		result.put("code", 1);
		result.put("message", "success");

		JSONObject data = new JSONObject();
		data.put("numberCode", code);

		result.put("data", data);
		return result.toString();*/

		NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
		numberCodeResponse.setNumberCode(randomVerificationcode);
		return ResponseResult.success(numberCodeResponse);
	}
}
