package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	/**
	 * Feign调用的Bug：以@RequestBody的方式传递对象的话，会将请求方式从GET方式自动转换成POST方式
	 * @param
	 * @return
	 */
	/*@GetMapping("/user")
	public ResponseResult getUser(@RequestBody VerificationcodeDTO verificationcodeDTO) {
		String passengerPhone = verificationcodeDTO.getPassengerPhone();
		System.out.println("手机号：" + passengerPhone);

		return passengerUserService.getUserByPassengerPhone(passengerPhone);
	}*/

	/**
	 * 以路径变量的方式传递参数
	 * @param phone
	 * @return
	 */
	@GetMapping("/user/{phone}")
	public ResponseResult getUser(@PathVariable("phone") String phone) {
		return passengerUserService.getUserByPassengerPhone(phone);
	}
}
