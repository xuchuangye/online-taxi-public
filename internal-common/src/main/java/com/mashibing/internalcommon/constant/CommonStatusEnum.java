package com.mashibing.internalcommon.constant;

import lombok.Getter;

/**
 * @author xcy
 * @date 2023/2/8 - 15:59
 */
@Getter
public enum CommonStatusEnum {

	/**
	 * 验证码已过期
	 */
	VERIFICATIONCODE_OVERDUE(1001, "验证码已过期"),
	/**
	 * 验证码类的提示：1001 ~ 1099
	 * 验证码不正确
	 */
	VERIFICATIONCODE_ERROR(1099, "验证码不正确"),
	/**
	 * token类的提示：1101 ~ 1199
	 */
	TOKEN_ERROR(1101, "token不正确"),

	/**
	 * 用户类的提示：1201 ~ 1299
	 */
	USER_IS_NOT_EXISTS(1201, "用户不存在"),

	/**
	 * 请求成功
	 */
	SUCCESS(1,"请求响应成功"),

	/**
	 * 请求失败
	 */
	FAIL(0, "请求响应失败");
	;
	private int code;
	private String message;

	CommonStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
