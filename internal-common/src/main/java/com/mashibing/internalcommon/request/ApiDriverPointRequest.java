package com.mashibing.internalcommon.request;

import com.mashibing.internalcommon.dto.PointDTO;
import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/19 - 15:25
 */
@Data
public class ApiDriverPointRequest {

	private Long carId;

	/**
	 * 点信息：JSON数组
	 */
	private PointDTO[] points;
}
