package com.mashibing.internalcommon.request;

import com.mashibing.internalcommon.dto.PointDTO;
import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/19 - 11:24
 */
@Data
public class PointUploadRequest {

	/**
	 * 终端id
	 */
	private Integer tid;
	/**
	 * 轨迹id
	 */
	private String trid;
	/**
	 * 点信息：JSON数组
	 */
	private PointDTO[] points;
}
