package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/11 - 10:03
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseResult loginOrRegister(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		System.out.println("手机号：" + passengerPhone);

		return userService.loginOrRegister(passengerPhone);
	}
}
