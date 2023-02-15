package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/15 - 10:45
 */
@Data
public class DicDistrict {
	/**
	 * 地区编码
	 */
	private String addressCode;
	/**
	 * 地区名称
	 */
	private String addressName;
	/**
	 * 上一级地区编码
	 */
	private String parentAddressCode;
	/**
	 * 级别：1：表示省，2：表示市，3：表示区/县
	 */
	private Integer level;
}
