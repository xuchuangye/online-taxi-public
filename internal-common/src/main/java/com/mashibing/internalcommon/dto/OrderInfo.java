package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchuangye
 * @since 2023-02-20
 */
@Data
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 乘客ID
     */
    private Long passengerId;

    /**
     * 乘客手机号
     */
    private String passengerPhone;

    /**
     * 司机ID
     */
    private Long driverId;

    /**
     * 司机手机号
     */
    private String driverPhone;

    /**
     * 车辆Id
     */
    private Long carId;

    /**
     * 车辆类型：经济型、舒适型、豪华型
     */
    private String vehicleType;

    /**
     * 发起地行政区划代码
     */
    private String address;

    /**
     * 订单发起时间
     */
    private LocalDateTime orderTime;

    /**
     * 预计用车时间
     */
    private LocalDateTime departTime;

    /**
     * 预计出发地点详细地址
     */
    private String departure;

    /**
     * 预计出发地点经度
     */
    private String depLongitude;

    /**
     * 预计出发地点纬度
     */
    private String depLatitude;

    /**
     * 预计目的地
     */
    private String destination;

    /**
     * 预计目的地经度
     */
    private String destLongitude;

    /**
     * 预计目的地纬度
     */
    private String destLatitude;

    /**
     * 坐标加密标识	1:GCJ-02测绘局标准	2:WGS84 GPS标准	3:BD-09 百度标准	4:CGCS2000 北斗标准	0:其他
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
     * 接单时车辆经度
     */
    private String receiveOrderCarLongitude;

    /**
     * 接单时车辆纬度
     */
    private String receiveOrderCarLatitude;

    /**
     * 接单时间，派单成功时间
     */
    private LocalDateTime receiveOrderTime;

    /**
     * 机动车驾驶证号
     */
    private String licenseId;

    /**
     * 车辆号牌
     */
    private String vehicleNo;

    /**
     * 司机去接乘客出发时间
     */
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
     * 司机到达 上车点 时间
     */
    private LocalDateTime driverArrivedDepartureTime;

    /**
     * 接到乘客，乘客上车的时间
     */
    private LocalDateTime pickUpPassengerTime;

    /**
     * 接到乘客，乘客上车的经度
     */
    private String pickUpPassengerLongitude;

    /**
     * 接到乘客，乘客上车的纬度
     */
    private String pickUpPassengerLatitude;

    /**
     * 乘客下车的时间
     */
    private LocalDateTime passengerGetoffTime;

    /**
     * 乘客下车的经度
     */
    private String passengerGetoffLongitude;

    /**
     * 乘客下车的纬度
     */
    private String passengerGetoffLatitude;

    /**
     * 订单撤销时间
     */
    private LocalDateTime cancelTime;

    /**
     * 撤销发起者：1:乘客	2:驾驶员	3:平台公司
     */
    private Integer cancelOperator;

    /**
     * 撤销类型代码	1:乘客提前撤销	2:驾驶员提前撤销	3:平台公司撤销	4;乘客违约撤销	5:驾驶员违约撤销
     */
    private Integer cancelTypeCode;

    /**
     * 载客里程（米）
     */
    private Long driveMile;

    /**
     * 载客时间(分)
     */
    private Long driveTime;

    /**
     * 订单状态1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9.订单取消'
     */
    private Integer orderStatus;

    private Double price;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
