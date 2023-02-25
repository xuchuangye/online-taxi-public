package com.mashibing.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.DriverCarBindingConstant;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/18 - 14:45
 */
@Service
public class DriverUserWorkStatusService {

	@Autowired
	private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

	@Autowired
	private DriverUserMapper driverUserMapper;

	@Autowired
	private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

	/**
	 * 修改司机状态
	 *
	 * @param driverId
	 * @param workStatus
	 * @return
	 */
	public ResponseResult changeWorkStatus(Long driverId, Integer workStatus) {
		Map<String, Object> map = new HashMap<>();
		map.put("driver_id", driverId);

		//查询司机状态
		List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(map);
		DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
		driverUserWorkStatus.setWorkStatus(workStatus);
		LocalDateTime now = LocalDateTime.now();
		driverUserWorkStatus.setGmtModified(now);

		//修改司机状态
		driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
		return ResponseResult.success("");
	}

	/**
	 * 根据司机手机号获取司机和车辆的绑定关系
	 *
	 * @param driverPhone
	 * @return 返回车司机和车辆绑定关系的信息
	 */
	public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationshipByDriverPhone(String driverPhone) {
		QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
		driverUserQueryWrapper.eq("driver_phone", driverPhone);
		DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);
		Long driverId = driverUser.getId();

		QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipQueryWrapper = new QueryWrapper<>();
		driverCarBindingRelationshipQueryWrapper.eq("driver_id", driverId);
		driverCarBindingRelationshipQueryWrapper.eq("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);

		DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(driverCarBindingRelationshipQueryWrapper);
		return ResponseResult.success(driverCarBindingRelationship);
	}
}
