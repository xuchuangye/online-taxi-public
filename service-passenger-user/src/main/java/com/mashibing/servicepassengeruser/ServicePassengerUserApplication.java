package com.mashibing.servicepassengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mashibing.servicepassengeruser.mapper")
public class ServicePassengerUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePassengerUserApplication.class, args);
	}

}
