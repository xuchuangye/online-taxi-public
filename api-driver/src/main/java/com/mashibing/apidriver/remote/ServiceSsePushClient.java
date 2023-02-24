package com.mashibing.apidriver.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xcy
 * @date 2023/2/24 - 14:46
 */
@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

	@GetMapping("/push")
	public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content);
}
