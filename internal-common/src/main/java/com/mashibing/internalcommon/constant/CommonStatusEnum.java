package com.mashibing.internalcommon.constant;

import lombok.Getter;

/**
 * @author xcy
 * @date 2023/2/8 - 15:59
 */
@Getter
public enum CommonStatusEnum {

	/**
	 * 验证码类的提示：1001 ~ 1099
	 * 验证码已过期
	 */
	VERIFICATIONCODE_OVERDUE(1001, "验证码已过期"),

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
	 * 计价规则的提示：1301 ~ 1399
	 */
	PRICE_RULE_NOT_EXISTS(1301, "计价规则不存在"),

	PRICE_RULE_EXISTS(1302, "计价规则已存在"),

	PRICE_RULE_NOT_CHANGE(1303, "计价规则没有变化"),

	PRICE_RULE_NOT_NEWEST(1304, "计价规则的版本不是最新的"),


	/**
	 * 拉取地图地区字典信息错误
	 */
	DISTRICT_URL_ERROR(1401, "拉取地图地区字典信息错误"),

	/**
	 * 司机和车辆关系的提示：1501 ~ 1599
	 */
	DRIVER_CAR_BIND_EXISTS(1501, "司机和车辆关系已经绑定"),

	DRIVER_CAR_BIND_NOT_EXISTS(1502, "司机和车辆关系没有绑定，无法进行解绑"),

	DRIVER_CAR_UNBIND_EXISTS(1503, "司机和车辆关系已经解绑，请勿重复解绑"),

	DRIVER_BIND_EXISTS(1504, "司机已经被绑定过了"),

	CAR_BIND_EXISTS(1505, "车辆已经被绑定过了"),

	/**
	 * 司机用户的提示：1601 ~ 1699
	 */
	DRIVER_NOT_EXISTS(1601, "司机用户不存在"),

	/**
	 * 订单类的提示：1701 ~ 1799
	 */
	ORDER_IN_PROGRESS(1701, "有订单正在进行中"),

	DEVICE_LOGIN_EXCEPTION(1702, "设备登录异常"),

	CITY_NOT_PROVIDE_SERVICE(1703, "当前城市不提供叫车服务"),

	CITY_NOT_IS_AVAILABLE_DRIVER(1704, "当前城市没有可用的司机"),

	NOT_AVAILABLE_DRIVER(1705, "没有可以派单的司机"),

	CANCEL_ORDER_FAIL(1706, "取消订单失败"),
	/**
	 * 请求成功
	 */
	SUCCESS(1, "请求响应成功"),

	/**
	 * 请求失败
	 */
	FAIL(0, "请求响应失败");;
	private final int code;
	private final String message;

	CommonStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
