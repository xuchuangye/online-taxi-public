package com.mashibing.servicemap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 主配置类
 * @author xcy
 * @date 2023/2/14 - 11:44
 */
@Configuration
public class DirectionConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
