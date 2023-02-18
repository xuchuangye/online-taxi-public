package com.mashibing.internalcommon.utils;

/**
 * @author xcy
 * @date 2023/2/12 - 11:52
 */
public class RedisKeyUtils {

	//验证码的前缀
	private static final String verificationcodePrefix = "verificationcode-";

	//token的前缀
	private static final String tokenPrefix = "token-";

	/**
	 * 拼接验证码的前缀 + 手机号作为Redis的key值
	 *
	 * @param phone 手机号
	 * @param identity 身份标识
	 * @return
	 */
	public static String generateVerificationcodeKey(String phone, String identity) {
		return verificationcodePrefix + identity + "-" + phone;
	}

	/**
	 * 拼接token的前缀 + 手机号 + "-" + 身份标识
	 *
	 * @param phone    手机号
	 * @param identity 身份标识
	 * @return
	 */
	public static String generateTokenKey(String phone, String identity, String tokenType) {
		return tokenPrefix + phone + "-" + identity + "-" + tokenType;
	}
}
