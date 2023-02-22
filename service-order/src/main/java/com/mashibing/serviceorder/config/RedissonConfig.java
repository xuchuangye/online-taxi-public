package com.mashibing.serviceorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xcy
 * @date 2023/2/22 - 19:49
 */
@Configuration
public class RedissonConfig {

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private String redisPort;
	@Value("${spring.redis.database}")
	private Integer redisDatabase;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		String redisPrefix = "redis://";
		config.useSingleServer().setAddress(redisPrefix + redisHost + ":" + redisPort).setDatabase(redisDatabase);

		return Redisson.create(config);
	}
}
