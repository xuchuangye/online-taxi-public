package com.mashibing.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarBindingConstant;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/17 - 9:32
 */
@Service
public class DriverCarBindingRelationshipService {

	@Autowired
	private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

	/**
	 * 司机和车辆关系进行绑定
	 * @param driverCarBindingRelationship
	 * @return
	 */
	public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
		QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
		queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
		queryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);
		//司机和车辆关系已经进行过绑定
		//同样的司机id和车辆id无法进行第二次绑定
		if (driverCarBindingRelationshipMapper.selectCount(queryWrapper) > 0) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getMessage());
		}

		//司机已经被绑定了
		//同样的司机id和不同的车辆id无法进行第二次绑定
		queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
		queryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);
		if (driverCarBindingRelationshipMapper.selectCount(queryWrapper) > 0) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_BIND_EXISTS.getMessage());
		}

		//车辆已经被绑定了
		//不同的司机id和同样的车辆id无法进行第二次绑定
		queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
		queryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);
		if (driverCarBindingRelationshipMapper.selectCount(queryWrapper) > 0) {
			return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(),
					CommonStatusEnum.CAR_BIND_EXISTS.getMessage());
		}

		LocalDateTime now = LocalDateTime.now();
		//设置绑定时间
		driverCarBindingRelationship.setBindingTime(now);
		//设置绑定关系
		driverCarBindingRelationship.setBindState(DriverCarBindingConstant.DRIVER_CAR_BIND);
		//第一次进行绑定肯定没有司机和车辆关系绑定的数据，所以肯定是insert
		driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
		return ResponseResult.success("");
	}

	/**
	 * 司机和车辆关系进行解绑
	 * @param driverCarBindingRelationshipParam
	 * @return
	 */
	public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationshipParam) {
		QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("driver_id", driverCarBindingRelationshipParam.getDriverId());
		queryWrapper.eq("car_id", driverCarBindingRelationshipParam.getCarId());
		queryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);
		//司机和车辆没有进行过绑定
		if (driverCarBindingRelationshipMapper.selectCount(queryWrapper) == 0) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode()
			,CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
		}
		//司机和车辆已经解绑过了
		queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("driver_id", driverCarBindingRelationshipParam.getDriverId());
		queryWrapper.eq("car_id", driverCarBindingRelationshipParam.getCarId());
		queryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_UNBIND);
		if (driverCarBindingRelationshipMapper.selectCount(queryWrapper) == 1) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_UNBIND_EXISTS.getCode()
			,CommonStatusEnum.DRIVER_CAR_UNBIND_EXISTS.getMessage());
		}
		//查询数据库中的司机和车辆关系
		DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectById(driverCarBindingRelationshipParam.getId());

		LocalDateTime now = LocalDateTime.now();
		//设置解绑时间
		driverCarBindingRelationship.setUnBindingTime(now);
		//设置解绑关系
		driverCarBindingRelationship.setBindState(DriverCarBindingConstant.DRIVER_CAR_UNBIND);
		//此时进行解绑肯定已经有数据，有数据就是update
		driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationship);
		return ResponseResult.success("");
	}
}
