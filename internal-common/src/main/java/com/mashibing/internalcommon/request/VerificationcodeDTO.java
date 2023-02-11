package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/6 - 9:37
 */
@Data
public class VerificationcodeDTO {

	/**
	 * 乘客手机号
	 */
	private String passengerPhone;

	/**
	 * 验证码
	 */
	private String verificationcode;
}
