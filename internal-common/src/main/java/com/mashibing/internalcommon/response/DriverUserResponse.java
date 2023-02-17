package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/17 - 18:17
 */
@Data
public class DriverUserResponse {

	/**
	 * 司机手机号
	 */
	private String driverPhone;
	/**
	 * 司机信息是否存在：1表示存在，0表示不存在
	 */
	private Integer isExists;
}
