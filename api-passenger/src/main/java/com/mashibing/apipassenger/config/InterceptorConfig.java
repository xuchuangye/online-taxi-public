package com.mashibing.apipassenger.config;

import com.mashibing.apipassenger.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 配置类
 *
 * @author xcy
 * @date 2023/2/12 - 10:24
 */
@Configuration
//public class InterceptorConfig extends WebMvcConfigurationSupport {
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public JWTInterceptor jwtInterceptor() {
		return new JWTInterceptor();
	}

	@Override
	//protected void addInterceptors(InterceptorRegistry registry) {
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> list = Arrays.asList(
				"/noauthTest",
				"/verification-code",
				"/verification-code-check",
				"/token-refresh",
				"/error",
				"/order/book",
				"/test-real-time-order/**");
		registry.addInterceptor(jwtInterceptor())
				//拦截的路径
				.addPathPatterns("/**")
				//不拦截的路径
				.excludePathPatterns(list)
		;
	}
}
