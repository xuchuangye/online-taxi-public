package com.mashibing.servicemap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xcy
 * @date 2023/2/14 - 9:45
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mashibing.servicemap.mapper")
public class ServiceMapApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMapApplication.class, args);
	}
}
