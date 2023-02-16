package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/16 - 10:29
 */
@Data
public class DriverUser {
	private Long id;
	/**
	 * 注册地行政区划代码
	 */
	private String address;
	/**
	 * 司机名称
	 */
	private String driverName;
	/**
	 * 司机手机号
	 */
	private String driverPhone;
	/**
	 * 司机性别
	 */
	private Integer driverGender;
	/**
	 * 司机出生年月日
	 */
	private LocalDate driverBirthday;
	/**
	 * 司机民族
	 */
	private String driverNation;
	/**
	 * 司机通信地址
	 */
	private String driverContactAddress;
	/**
	 * 司机驾驶证号
	 */
	private String licenseId;
	/**
	 * 初次领取驾驶证日期
	 */
	private LocalDate getDriverLicenseDate;
	/**
	 * 驾驶证有效期限起
	 */
	private LocalDate driverLicenseOn;
	/**
	 * 驾驶证有效期限止
	 */
	private LocalDate driverLicenseOff;
	/**
	 * 是否巡游出租汽车司机，1：是，0：否
	 */
	private Integer taxiDriver;
	/**
	 * 网络预约出租汽车司机资格证号
	 */
	private String certificateNo;
	/**
	 * 网络预约出租汽车司机发证机构
	 */
	private String networkCarIssueOrganization;
	/**
	 * 资格证发证日期
	 */
	private LocalDate networkCarIssueDate;
	/**
	 * 初次领取资格证日期
	 */
	private LocalDate getNetworkCarProofDate;
	/**
	 * 资格证有效起始日期
	 */
	private LocalDate networkCarProofOn;
	/**
	 * 资格证有效截止日期
	 */
	private LocalDate networkCarProofOff;
	/**
	 * 报备日期
	 */
	private LocalDate registerDate;
	/**
	 * 服务类型，1：网络预约出租汽车，2：巡游出租汽车，3：私人小客车合乘
	 */
	private Integer commercialType;
	/**
	 * 司机合同（或协议）签署公司
	 */
	private String contractCompany;
	/**
	 * 合同（或协议）有效日期起
	 */
	private LocalDate contract_on;
	/**
	 * 合同（或协议）有效日期止
	 */
	private LocalDate contract_off;
	/**
	 * 司机状态，1：无效，0：有效
	 */
	private Byte state;
	/**
	 * 创建时间
	 */
	private LocalDateTime gmtCreate;
	/**
	 * 更新时间
	 */
	private LocalDateTime gmtModified;

}
