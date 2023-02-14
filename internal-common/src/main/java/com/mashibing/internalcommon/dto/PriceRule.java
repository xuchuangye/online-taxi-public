package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/14 - 18:50
 */
@Data
public class PriceRule {
	/**
	 * 城市代码(编码)
	 */
	private String cityCode;
	/**
	 * 车辆类型(舒适、豪华)
	 */
	private String vehicleType;
	/**
	 * 起步价
	 */
	private Double startFare;
	/**
	 * 起步里程
	 */
	private Integer startMile;
	/**
	 * 计程单价
	 */
	private Double unitPricePreMile;
	/**
	 * 计时单价
	 */
	private Double unitPricePreMinute;

}
