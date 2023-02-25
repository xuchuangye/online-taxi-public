package com.mashibing.internalcommon.request;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/20 - 7:24
 */
@Data
public class OrderRequest {

	/**
	 * 订单id
	 */
	private Long orderId;

	/**
	 * 乘客id
	 */
	private Long passengerId;

	/**
	 * 乘客手机号
	 */
	private String passengerPhone;

	/**
	 * 发起地行政区划代码
	 */
	private String address;
	/**
	 * 预计用车时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime departTime;
	/**
	 * 订单发起时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderTime;
	/**
	 * 预计出发地点详细地址
	 */
	private String departure;
	/**
	 * 预计出发地点经度
	 */
	private String depLatitude;
	/**
	 * 预计出发地点维度
	 */
	private String depLongitude;
	/**
	 * 预计目的地
	 */
	private String destination;
	/**
	 * 预计目的地经度
	 */
	private String destLatitude;
	/**
	 * 预计目的地纬度
	 */
	private String destLongitude;
	/**
	 * 坐标加密标识：1：GCJ-02测绘局标准，2：WGS84 GPS标准，3：BD-09 百度标准，4：CGCS2000 北斗标准，0：其他
	 */
	private Integer encrypt;
	/**
	 * 运价类型编码
	 */
	private String fareType;

	/**
	 * 运价版本
	 */
	private Integer fareVersion;

	/**
	 * 车辆类型
	 */
	private String vehicleType;

	/**
	 * 设备唯一码
	 */
	private String deviceCode;

	/**
	 * 司机去接乘客出发时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime toPickUpPassengerTime;

	/**
	 * 去接乘客时，司机的经度
	 */
	private String toPickUpPassengerLongitude;

	/**
	 * 去接乘客时，司机的纬度
	 */
	private String toPickUpPassengerLatitude;

	/**
	 * 去接乘客时，司机的地点
	 */
	private String toPickUpPassengerAddress;

	/**
	 * 接到乘客，乘客上车的经度
	 */
	private String pickUpPassengerLongitude;

	/**
	 * 接到乘客，乘客上车的纬度
	 */
	private String pickUpPassengerLatitude;

	/**
	 * 乘客下车的经度
	 */
	private String passengerGetoffLongitude;

	/**
	 * 乘客下车的纬度
	 */
	private String passengerGetoffLatitude;

	private String price;
}
