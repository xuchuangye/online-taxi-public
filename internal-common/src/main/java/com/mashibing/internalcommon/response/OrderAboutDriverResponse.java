package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/21 - 20:48
 */
@Data
public class OrderAboutDriverResponse {

	private Long carId;
	private Long driverId;
	private String driverPhone;
	/**
	 * 机动车驾驶证号
	 */
	private String licenseId;

	/**
	 * 车辆号牌
	 */
	private String vehicleNo;
}
