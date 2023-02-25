package com.mashibing.servicealipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xcy
 * @date 2023/2/24 - 15:42
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceAlipayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAlipayApplication.class, args);
	}
}
