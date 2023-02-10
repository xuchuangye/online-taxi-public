package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.VerificationcodeDTO;
import com.mashibing.apipassenger.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
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
	 * @param verificationcodeDTO
	 * @return
	 */
	@GetMapping("/verification-code")
	public ResponseResult verificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		return verificationcodeService.generateVerificationcode(passengerPhone);
	}

	/**
	 * 乘客校验验证码
	 * @param verificationcodeDTO
	 * @return
	 */
	@PostMapping("/verification-code-check")
	public ResponseResult checkVerificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		String verificationcode = verificationcodeDTO.getVerificationcode();

		return verificationcodeService.checkVerificationcode(passengerPhone, verificationcode);
	}
}
