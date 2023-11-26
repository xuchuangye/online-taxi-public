package com.mashibing.apipassenger.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mashibing.apipassenger.constraints.DateTimeRange;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
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
	@Positive(message = "订单id格式不正确")
	private Long orderId;

	/**
	 * 乘客id
	 */
	@NotNull(message = "乘客id不能为空")
	@Positive(message = "乘客id格式不正确")
	private Long passengerId;

	/**
	 * 乘客手机号
	 */
	//表示不能为空白
	@NotBlank(message = "手机号不能为空")
	//表示使用1开头，中间选择一位数为3,4,5,6,7,8,9，剩余9位数字，组成11位手机号
	@Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "请填写正确的手机号")
	private String passengerPhone;

	/**
	 * 发起地行政区划代码
	 */
	@NotNull(message = "行政区划代码不能为空")
	@Pattern(regexp = "^\\d{6}$", message = "请输入6位数字行政区划代码")
	private String address;
	/**
	 * 出发时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeRange(message = "出发时间不正确")
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
