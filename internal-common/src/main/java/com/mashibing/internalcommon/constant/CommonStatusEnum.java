package com.mashibing.internalcommon.constant;

import lombok.Getter;

/**
 * @author xcy
 * @date 2023/2/8 - 15:59
 */
@Getter
public enum CommonStatusEnum {

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
