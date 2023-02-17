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
	 * 计价规则不存在
	 */
	PRICE_RULE_NOT_EXISTS(1301, "计价规则不存在"),

	/**
	 * 拉取地图地区字典信息错误
	 */
	DISTRICT_URL_ERROR(1401, "拉取地图地区字典信息错误"),

	/**
	 * 司机和车辆关系的提示：1501 ~ 1599
	 */
	DRIVER_CAR_BIND_EXISTS(1501, "司机和车辆关系已经绑定，请勿重复绑定"),


	DRIVER_BIND_EXISTS(1502, "司机已经被绑定过了，请勿重复绑定"),

	CAR_BIND_EXISTS(1503, "车辆已经被绑定过了，请勿重复绑定"),

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
