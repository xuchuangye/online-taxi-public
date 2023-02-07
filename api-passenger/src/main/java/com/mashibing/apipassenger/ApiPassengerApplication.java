package com.mashibing.apipassenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xcy
 * @date 2023/2/6 - 9:13
 */
@SpringBootApplication
//开启服务发现功能
@EnableDiscoveryClient
public class ApiPassengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPassengerApplication.class, args);
	}
}
