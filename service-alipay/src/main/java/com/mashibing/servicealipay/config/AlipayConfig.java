package com.mashibing.servicealipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author xcy
 * @date 2023/2/24 - 15:47
 */
@Configuration
@ConfigurationProperties("alipay")
@Data
@Slf4j
public class AlipayConfig {

	/**
	 *
	 */
	private String appId;

	/**
	 * 应用私钥
	 */
	private String appPrivateKey;

	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;

	private String notifyUrl;

	@PostConstruct
	public void init() {
		Config config = new Config();
		//配置基础信息
		config.protocol = "https";
		config.gatewayHost = "openapi.alipaydev.com";
		config.signType = "RSA2";

		//配置业务信息
		config.appId = this.appId;
		config.merchantPrivateKey = this.appPrivateKey;
		config.alipayPublicKey = this.alipayPublicKey;
		config.notifyUrl = this.notifyUrl;

		Factory.setOptions(config);

		log.info("支付宝配置初始化完成");
	}
}
