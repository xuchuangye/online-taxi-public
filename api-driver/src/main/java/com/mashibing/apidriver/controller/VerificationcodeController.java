package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/18 - 7:15
 */
@RestController
@Slf4j
public class VerificationcodeController {

	@Autowired
	private VerificationcodeService verificationcodeService;

	/**
	 * 司机获取验证码
	 * @param verificationcodeDTO
	 * @return
	 */
	@PostMapping("/verification-code")
	public ResponseResult getVerificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String driverPhone = verificationcodeDTO.getDriverPhone();
		log.info("司机手机号：" + driverPhone);
		return verificationcodeService.checkAndSendVerificationcode(driverPhone);
	}

	/**
	 * 司机校验验证码
	 * @param verificationcodeDTO
	 * @return
	 */
	@PostMapping("/verification-code-check")
	public ResponseResult checkVerificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String driverPhone = verificationcodeDTO.getDriverPhone();
		String verificationcode = verificationcodeDTO.getVerificationcode();

		return verificationcodeService.checkVerificationcode(driverPhone, verificationcode);
	}
}
