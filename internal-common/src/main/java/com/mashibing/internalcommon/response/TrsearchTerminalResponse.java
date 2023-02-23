package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/23 - 18:02
 */
@Data
public class TrsearchTerminalResponse {

	/**
	 * 载客里程
	 */
	private Long driverMile;
	/**
	 * 载客时间
	 */
	private Long driverTime;
}
