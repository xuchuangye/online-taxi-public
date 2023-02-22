package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/19 - 7:51
 */
@Data
public class TerminalResponse {

	/**
	 * terminal_id
	 */
	private Integer tid;

	private Long desc;

	private Long longitude;

	private Long latitude;
}
