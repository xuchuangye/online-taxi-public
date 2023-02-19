package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/19 - 9:34
 */
@Data
public class TraceResponse {
	/**
	 * 轨迹id
	 */
	private Integer trid;
	/**
	 * 轨迹名称
	 */
	private String trname;
}
