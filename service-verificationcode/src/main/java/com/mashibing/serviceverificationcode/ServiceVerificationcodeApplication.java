package com.mashibing.serviceverificationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVerificationcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceVerificationcodeApplication.class, args);
	}

}
