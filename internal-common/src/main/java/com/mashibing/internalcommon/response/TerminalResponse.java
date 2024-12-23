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
	private String tid;

	private Long desc;

	private String longitude;

	private String latitude;

	private Long carId;

}
