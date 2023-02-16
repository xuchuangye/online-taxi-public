package com.mashibing.servicedriveruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xcy
 * @date 2023/2/16 - 9:58
 */
@SpringBootApplication
@MapperScan("com.mashibing.servicedriveruser.mapper")
public class ServiceDriverUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceDriverUserApplication.class, args);
	}
}
