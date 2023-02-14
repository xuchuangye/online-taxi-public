package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/14 - 9:54
 */
@Data
public class DirectionResponse {
	/**
	 * 距离
	 */
	private Integer distance;
	/**
	 * 时长
	 */
	private Integer duration;
}
