package com.mashibing.apiboss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xcy
 * @date 2023/2/16 - 15:30
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ApiBossApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBossApplication.class, args);
	}
}
