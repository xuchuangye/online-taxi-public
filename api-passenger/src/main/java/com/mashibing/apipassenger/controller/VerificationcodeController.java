package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.CheckVerificationcodeDTO;
import com.mashibing.apipassenger.request.SendVerificationcodeDTO;
import com.mashibing.apipassenger.request.ValidationGroupVerificationcodeDTO;
import com.mashibing.apipassenger.service.VerificationcodeService;
import com.mashibing.apipassenger.validationgroup.CheckVerificationcodeValidationGroup;
import com.mashibing.apipassenger.validationgroup.SendVerificationcodeValidationGroup;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/6 - 9:34
 */
@RestController
public class VerificationcodeController {

	@Autowired
	private VerificationcodeService verificationcodeService;

	/**
	 * 乘客获取验证码
	 * @param sendVerificationcodeDTO 获取验证码
	 * @return
	 */
	@GetMapping("/verification-code")
	public ResponseResult verificationcode(@Validated @RequestBody SendVerificationcodeDTO sendVerificationcodeDTO) {
		String passengerPhone = sendVerificationcodeDTO.getPassengerPhone();
		return verificationcodeService.generateVerificationcode(passengerPhone);
	}

	/**
	 * 乘客校验验证码
	 * @param checkVerificationcodeDTO 检验验证码
	 * @return
	 */
	@PostMapping("/verification-code-check")
	public ResponseResult checkVerificationcode(@Validated @RequestBody CheckVerificationcodeDTO checkVerificationcodeDTO) {
		String passengerPhone = checkVerificationcodeDTO.getPassengerPhone();
		String verificationcode = checkVerificationcodeDTO.getVerificationcode();

		return verificationcodeService.checkVerificationcode(passengerPhone, verificationcode);
	}

	/**
	 * 乘客获取验证码 - Validation分组
	 * @param validationGroupVerificationcodeDTO
	 * @return
	 */
	/* @GetMapping("/verification-code")
	public ResponseResult verificationcode(@Validated(SendVerificationcodeValidationGroup.class) @RequestBody ValidationGroupVerificationcodeDTO validationGroupVerificationcodeDTO) {
		String passengerPhone = validationGroupVerificationcodeDTO.getPassengerPhone();
		return verificationcodeService.generateVerificationcode(passengerPhone);
	} */

	/**
	 * 乘客校验验证码 - Validation分组
	 * @param validationGroupVerificationcodeDTO
	 * @return
	 */
	/* @PostMapping("/verification-code-check")
	public ResponseResult checkVerificationcode(@Validated(CheckVerificationcodeValidationGroup.class) @RequestBody ValidationGroupVerificationcodeDTO validationGroupVerificationcodeDTO) {
		String passengerPhone = validationGroupVerificationcodeDTO.getPassengerPhone();
		String verificationcode = validationGroupVerificationcodeDTO.getVerificationcode();

		return verificationcodeService.checkVerificationcode(passengerPhone, verificationcode);
	} */
}
