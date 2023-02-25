package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JWTUtils;
import com.mashibing.servicedriveruser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

	/**
	 * 修改司机工作状态
	 *
	 * @param driverUserWorkStatus
	 * @return
	 */
	@PutMapping("/driver-user-work-status")
	public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus) {
		Long driverId = driverUserWorkStatus.getDriverId();
		Integer workStatus = driverUserWorkStatus.getWorkStatus();
		return driverUserWorkStatusService.changeWorkStatus(driverId, workStatus);
	}

	/**
	 * 首先根据司机手机号查询司机id，最后再根据司机id获取司机和车辆绑定关系的信息
	 *
	 * @param driverPhone 司机手机号
	 * @return
	 */
	@GetMapping("/driver-car-binding-relationship")
	public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(@RequestParam String driverPhone) {
		return driverUserWorkStatusService.getDriverCarBindingRelationshipByDriverPhone(driverPhone);
	}
}
