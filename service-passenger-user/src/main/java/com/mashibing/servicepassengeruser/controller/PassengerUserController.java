package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/11 - 10:03
 */
@RestController
public class PassengerUserController {

	@Autowired
	private PassengerUserService passengerUserService;

	@PostMapping("/user")
	public ResponseResult loginOrRegister(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		System.out.println("手机号：" + passengerPhone);

		return passengerUserService.loginOrRegister(passengerPhone);
	}

	@GetMapping("/user")
	public ResponseResult getUsers(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		System.out.println("手机号：" + passengerPhone);

		return passengerUserService.getUserByPassengerPhone(passengerPhone);
	}
}
