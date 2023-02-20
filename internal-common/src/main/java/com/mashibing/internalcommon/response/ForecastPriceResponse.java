package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * 预估价格返回响应
 * @author xcy
 * @date 2023/2/13 - 17:35
 */
@Data
public class ForecastPriceResponse {
	private double price;

	private String cityCode;

	private String vehicleType;
}
