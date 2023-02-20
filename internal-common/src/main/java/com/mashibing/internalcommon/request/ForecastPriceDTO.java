package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/13 - 17:31
 */
@Data
public class ForecastPriceDTO {
	/**
	 * 出发地经度
	 */
	private String depLongitude;
	/**
	 * 出发低纬度
	 */
	private String depLatitude;
	/**
	 * 目的地经度
	 */
	private String destLongitude;
	/**
	 * 目的地纬度
	 */
	private String destLatitude;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 车辆类型
	 */
	private String vehicleType;
}
