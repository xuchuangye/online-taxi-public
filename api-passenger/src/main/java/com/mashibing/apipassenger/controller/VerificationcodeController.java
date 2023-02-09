package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.VerificationcodeDTO;
import com.mashibing.apipassenger.service.VerificationcodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/verification-code")
	public ResponseResult verificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		return verificationcodeService.generateVerificationcode(passengerPhone);
	}
}
