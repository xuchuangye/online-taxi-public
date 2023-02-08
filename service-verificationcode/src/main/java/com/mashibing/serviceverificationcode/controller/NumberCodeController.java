package com.mashibing.serviceverificationcode.controller;

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
	public String numberCode(@PathVariable("size") int size) {

		System.out.println("size: " + size);
		JSONObject result = new JSONObject();
		result.put("code", 1);
		result.put("message", "success");

		JSONObject data = new JSONObject();
		data.put("numberCode", 123456);

		result.put("data", data);
		return result.toString();
	}
}
