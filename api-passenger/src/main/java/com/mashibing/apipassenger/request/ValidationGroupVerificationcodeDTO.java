package com.mashibing.apipassenger.request;

import com.mashibing.apipassenger.validationgroup.CheckVerificationcodeValidationGroup;
import com.mashibing.apipassenger.validationgroup.SendVerificationcodeValidationGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Validation分组的VerificationcodeDTO
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/26-17:08
 * @description TODO
 */
@Data
public class ValidationGroupVerificationcodeDTO {

	/**
	 * 乘客手机号
	 */
	//表示不能为空白
	@NotBlank(message = "手机号不能为空", groups = {SendVerificationcodeValidationGroup.class})
	//表示使用1开头，中间选择一位数为3,4,5,6,7,8,9，剩余9位数字，组成11位手机号
	@Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "请填写正确的手机号", groups = {SendVerificationcodeValidationGroup.class})
	private String passengerPhone;

	/**
	 * 验证码
	 */
	//表示不能为空白
	@NotBlank(message = "验证码不能为空", groups = { CheckVerificationcodeValidationGroup.class})
	//表示使用6位数字，组成6位验证码
	@Pattern(regexp = "^\\d{6}$", message = "请填写6位数字的验证码", groups = { CheckVerificationcodeValidationGroup.class})
	private String verificationcode;
}
