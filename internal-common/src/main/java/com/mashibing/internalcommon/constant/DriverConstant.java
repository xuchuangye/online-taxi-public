package com.mashibing.internalcommon.constant;

import lombok.Data;

/**
 * 司机信息相关的常量类
 * @author xcy
 * @date 2023/2/18 - 7:50
 */
@Data
public class DriverConstant {

	/**
	 * 司机状态有效
	 */
	public static final String DRIVER_STATE_VALID = "0";

	/**
	 * 司机状态无效
	 */
	public static final String DRIVER_STATE_INVALID = "1";

	/**
	 * 司机存在
	 */
	public static final int DRIVER_EXISTS = 1;

	/**
	 * 司机不存在
	 */
	public static final int DRIVER_NOT_EXISTS = 0;
}
