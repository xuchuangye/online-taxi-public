package com.mashibing.controller;

import com.mashibing.request.VerificationcodeDTO;
import com.mashibing.service.VerificationcodeService;
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
	public String verificationcode(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		return verificationcodeService.generateVerificationcode();
	}
}
