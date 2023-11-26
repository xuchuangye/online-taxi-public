package com.mashibing.apipassenger.request;

import com.mashibing.apipassenger.constraints.VehicleTypeCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xcy
 * @date 2023/2/13 - 17:31
 */
@Data
public class ForecastPriceDTO {
	/**
	 * 起点经度
	 */
	@NotBlank(message = "起点经度不能为空")
	/*
	-180, +180
	主要分为三个范围:
	1. (-100, +100)
	2. (-180, +180)
	3. -180 和 +180
	 */
	@Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0,7]\\d{1}\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确的起点经度")
	private String depLongitude;
	/**
	 * 起点纬度
	 */
	@NotBlank(message = "起点纬度不能为空")
	/*
	-90, +90
	主要分为两个范围:
	1. (-90, +90)
	2. -90 和 +90
	 */
	@Pattern(regexp = "^[\\-\\+]?([0,8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确的起点纬度")
	private String depLatitude;
	/**
	 * 终点经度
	 */
	@NotBlank(message = "终点经度不能为空")
	/*
	-180, +180
	主要分为三个范围:
	1. (-100, +100)
	2. (-180, +180)
	3. -180 和 +180
	 */
	@Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0,7]\\d{1}\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确的终点经度")
	private String destLongitude;
	/**
	 * 终点纬度
	 */
	@NotBlank(message = "终点纬度不能为空")
	/*
	-90, +90
	主要分为两个范围:
	1. (-90, +90)
	2. -90 和 +90
	 */
	@Pattern(regexp = "^[\\-\\+]?([0,8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确的终点纬度")
	private String destLatitude;

	/**
	 * 城市编码
	 */
	@NotBlank(message = "城市编码不能为空")
	@Pattern(regexp = "^\\d{6}$", message = "请输入正确的城市编码")
	private String cityCode;

	/**
	 * 车辆类型
	 */
	@NotBlank(message = "车辆类型不能为空")
	//@Pattern(regexp = "^1$", message = "请输入正确的车辆类型")
	@VehicleTypeCheck(vehicleTypeValue = {"1,2"}, message = "请输入正确的车辆类型")
	private String vehicleType;
}
