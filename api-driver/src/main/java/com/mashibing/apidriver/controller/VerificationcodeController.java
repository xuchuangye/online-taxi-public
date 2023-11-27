package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.request.CheckVerificationcodeDTO;
import com.mashibing.apidriver.request.SendVerificationcodeDTO;
import com.mashibing.apidriver.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	 * @param sendVerificationcodeDTO
	 * @return
	 */
	@PostMapping("/verification-code")
	public ResponseResult getVerificationcode(@Validated
	                                              @RequestBody SendVerificationcodeDTO sendVerificationcodeDTO) {
		String driverPhone = sendVerificationcodeDTO.getDriverPhone();
		log.info("司机手机号：" + driverPhone);
		return verificationcodeService.checkAndSendVerificationcode(driverPhone);
	}

	/**
	 * 司机校验验证码
	 * @param checkVerificationcodeDTO
	 * @return
	 */
	@PostMapping("/verification-code-check")
	public ResponseResult checkVerificationcode(@Validated
	                                                @RequestBody CheckVerificationcodeDTO checkVerificationcodeDTO) {
		String driverPhone = checkVerificationcodeDTO.getDriverPhone();
		String verificationcode = checkVerificationcodeDTO.getVerificationcode();

		return verificationcodeService.checkVerificationcode(driverPhone, verificationcode);
	}
}
