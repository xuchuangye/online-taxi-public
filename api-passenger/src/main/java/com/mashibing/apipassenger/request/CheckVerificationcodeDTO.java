package com.mashibing.apipassenger.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author  xuchuangye
 * @date    2023/11/25-15:57
 * @version 1.0
 * @description  TODO
 */
@Data
public class CheckVerificationcodeDTO {
	/**
	 * 乘客手机号
	 */
	//表示不能为空白
	@NotBlank(message = "手机号不能为空")
	//表示使用1开头，中间选择一位数为3,4,5,6,7,8,9，剩余9位数字，组成11位手机号
	@Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "请填写正确的手机号")
	private String passengerPhone;

	/**
	 * 验证码
	 */
	//表示不能为空白
	@NotBlank(message = "验证码不能为空")
	//表示使用6位数字，组成6位验证码
	@Pattern(regexp = "^\\d{6}$", message = "请填写6位数字的验证码")
	private String verificationcode;
}
