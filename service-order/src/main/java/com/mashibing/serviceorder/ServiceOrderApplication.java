package com.mashibing.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xcy
 * @date 2023/2/20 - 7:53
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mashibing.serviceorder.mapper")
public class ServiceOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceOrderApplication.class, args);
	}
}
