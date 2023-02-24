package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/24 - 11:08
 */
@Data
public class CalculatePriceRequest {

	private Integer distance;
	private Integer duration;
	private String cityCode;
	private String vehicleType;
}
