package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/verificationcode")
	public ResponseResult getVerificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String driverPhone = verificationcodeDTO.getDriverPhone();
		log.info("司机手机号：" + driverPhone);
		return verificationcodeService.checkAndSendVerificationcode(driverPhone);
	}
}
