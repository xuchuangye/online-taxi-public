package com.mashibing.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarBindingConstant;
import com.mashibing.internalcommon.constant.DriverConstant;
import com.mashibing.internalcommon.constant.DriverUserWorkStatusConstant;
import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.internalcommon.response.OrderAboutDriverResponse;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/16 - 10:53
 */
@Service
public class DriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	@Autowired
	private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

	@Autowired
	private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

	@Autowired
	private CarMapper carMapper;

	/**
	 * 插入司机信息
	 *
	 * @param driverUser
	 * @return
	 */
	public ResponseResult addDriverUser(DriverUser driverUser) {
		LocalDateTime now = LocalDateTime.now();
		//创建时间
		driverUser.setGmtCreate(now);
		//更新时间
		driverUser.setGmtModified(now);
		driverUserMapper.insert(driverUser);

		//更新司机状态表
		DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
		driverUserWorkStatus.setDriverId(driverUser.getId());
		//目前是收车状态
		driverUserWorkStatus.setWorkStatus(DriverUserWorkStatusConstant.DRIVER_STATUS_STOP);
		driverUserWorkStatus.setGmtCreate(now);
		driverUserWorkStatus.setGmtModified(now);

		driverUserWorkStatusMapper.insert(driverUserWorkStatus);

		return ResponseResult.success("");
	}

	/**
	 * 修改司机信息
	 *
	 * @param driverUser
	 * @return
	 */
	public ResponseResult updateDriverUser(DriverUser driverUser) {
		LocalDateTime now = LocalDateTime.now();
		//更新时间
		driverUser.setGmtModified(now);
		driverUserMapper.updateById(driverUser);
		return ResponseResult.success("");
	}

	/**
	 * 查询司机信息
	 *
	 * @param driverPhone 司机手机号
	 * @return
	 */
	public ResponseResult<DriverUser> selectDriverUser(String driverPhone) {
		Map<String, Object> map = new HashMap<>();
		map.put("driver_phone", driverPhone);
		map.put("state", DriverConstant.DRIVER_STATE_VALID);
		List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
		if (driverUsers.isEmpty()) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
		}
		DriverUser driverUser = driverUsers.get(0);
		return ResponseResult.success(driverUser);
	}

	/**
	 * 根据车辆id查询可以派单的司机信息
	 *
	 * @param carId
	 * @return
	 */
	public ResponseResult<OrderAboutDriverResponse> getAvailableDriver(Long carId) {
		//查询司机和车辆绑定关系
		QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipQueryWrapper = new QueryWrapper<>();
		driverCarBindingRelationshipQueryWrapper.eq("car_id", carId);
		driverCarBindingRelationshipQueryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);

		DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(driverCarBindingRelationshipQueryWrapper);
		Long driverId = driverCarBindingRelationship.getDriverId();
		//查询司机工作状态
		QueryWrapper<DriverUserWorkStatus> driverUserWorkStatusQueryWrapper = new QueryWrapper<>();
		driverUserWorkStatusQueryWrapper.eq("driver_id", driverId);
		driverUserWorkStatusQueryWrapper.eq("work_status", DriverUserWorkStatusConstant.DRIVER_STATUS_STAR);

		int count = driverUserWorkStatusMapper.selectCount(driverUserWorkStatusQueryWrapper);
		if (count == 0) {
			return ResponseResult.fail(CommonStatusEnum.NOT_AVAILABLE_DRIVER.getCode(),
					CommonStatusEnum.NOT_AVAILABLE_DRIVER.getMessage());
		}else {
			//查询司机信息
			QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
			driverUserQueryWrapper.eq("id", driverId);
			DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);


			//查询车辆信息
			QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
			carQueryWrapper.eq("id", carId);
			Car car = carMapper.selectOne(carQueryWrapper);

			if (driverUser == null) {
				return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
						CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
			}

			String driverPhone = driverUser.getDriverPhone();

			OrderAboutDriverResponse orderAboutDriverResponse = new OrderAboutDriverResponse();
			orderAboutDriverResponse.setCarId(carId);
			orderAboutDriverResponse.setDriverId(driverId);
			orderAboutDriverResponse.setDriverPhone(driverPhone);
			orderAboutDriverResponse.setLicenseId(driverUser.getLicenseId());
			orderAboutDriverResponse.setVehicleType(car.getVehicleType());

			orderAboutDriverResponse.setVehicleNo(car.getVehicleNo());


			return ResponseResult.success(orderAboutDriverResponse);
		}


	}
}
