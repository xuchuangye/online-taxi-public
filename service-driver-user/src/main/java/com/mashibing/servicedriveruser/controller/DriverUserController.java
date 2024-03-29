package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.constant.DriverConstant;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.internalcommon.response.OrderAboutDriverResponse;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/16 - 10:54
 */
@RestController
public class DriverUserController {

	@Autowired
	private DriverUserService driverUserService;

	/**
	 * 添加司机信息
	 * @param driverUser
	 * @return
	 */
	@PostMapping("/user")
	public ResponseResult addDriverUser(@RequestBody DriverUser driverUser) {
		return driverUserService.addDriverUser(driverUser);
	}

	/**
	 * 修改司机信息
	 * @param driverUser
	 * @return
	 */
	@PutMapping("/user")
	public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
		return driverUserService.updateDriverUser(driverUser);
	}

	/**
	 * 查询司机信息
	 * @param driverPhone
	 * @return
	 */
	@GetMapping("/user/{driver-phone}")
	public ResponseResult<DriverUser> selectDriverUser(@PathVariable("driver-phone") String driverPhone) {
		ResponseResult<DriverUser> driverUserResponseResult = driverUserService.selectDriverUser(driverPhone);
		DriverUser driverUserDB = driverUserResponseResult.getData();

		DriverUserResponse driverUserResponse = new DriverUserResponse();
		int isExists = DriverConstant.DRIVER_EXISTS;
		if (driverUserDB == null) {
			isExists = DriverConstant.DRIVER_NOT_EXISTS;
			//如果司机不存在，返回响应传入的司机手机号
			driverUserResponse.setDriverPhone(driverPhone);
		} else {
			//如果司机存在，返回响应查询的司机手机号
			driverUserResponse.setDriverPhone(driverUserDB.getDriverPhone());
		}
		driverUserResponse.setIsExists(isExists);

		return ResponseResult.success(driverUserResponse);
	}

	/**
	 * 获取可用的司机
	 * @param carId
	 * @return
	 */
	@GetMapping("/get-available-driver/{carId}")
	public ResponseResult<OrderAboutDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId) {
		return driverUserService.getAvailableDriver(carId);
	}
}
