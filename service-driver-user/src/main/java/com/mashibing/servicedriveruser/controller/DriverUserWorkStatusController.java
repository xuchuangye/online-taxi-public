package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuchuangye
 * @since 2023-02-18
 */
@RestController
public class DriverUserWorkStatusController {

	@Autowired
	private DriverUserWorkStatusService driverUserWorkStatusService;

	@PutMapping("/driver-user-work-status")
	public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus) {
		Long driverId = driverUserWorkStatus.getDriverId();
		Integer workStatus = driverUserWorkStatus.getWorkStatus();
		return driverUserWorkStatusService.changeWorkStatus(driverId, workStatus);
	}
}
