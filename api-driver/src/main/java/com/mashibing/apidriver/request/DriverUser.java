package com.mashibing.apidriver.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/16 - 10:29
 */
@Data
public class DriverUser {
	/**
	 * 司机id
	 */
	@Positive(message = "订单id必须是正数")
	private Long id;

	/**
	 * 注册地行政区划代码
	 */
	@NotBlank(message = "行政区划代码不能为空")
	@Pattern(regexp = "^\\d{6}$", message = "请输入6位数字行政区划代码")
	private String address;

	/**
	 * 司机名称
	 */
	@NotBlank(message = "司机名称不能为空")
	@Length(min = 2, message = "司机名称至少两位")
	private String driverName;

	/**
	 * 司机手机号
	 */
	//表示不能为空白
	@NotBlank(message = "手机号不能为空")
	//表示使用1开头，中间选择一位数为3,4,5,6,7,8,9，剩余9位数字，组成11位手机号
	@Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "请填写正确的司机手机号")
	private String driverPhone;

	/**
	 * 司机性别
	 */
	@NotNull(message = "性别不能为空")
	private Integer driverGender;
	/**
	 * 司机出生年月日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "出生年月日不能为空")
	private LocalDate driverBirthday;
	/**
	 * 司机民族
	 */
	@NotBlank(message = "民族不能为空")
	@Length(min = 2, message = "民族至少两位")
	private String driverNation;
	/**
	 * 司机通信地址
	 */
	@NotBlank(message = "通信地址不能为空")
	private String driverContactAddress;
	/**
	 * 司机驾驶证号
	 */
	@NotBlank(message = "驾驶证号不能为空")
	private String licenseId;

	/**
	 * 初次领取驾驶证日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "初次领取驾驶证日期不能为空")
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
	private LocalDate contractOn;
	/**
	 * 合同（或协议）有效日期止
	 */
	private LocalDate contractOff;
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
