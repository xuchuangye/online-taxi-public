package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverConstant;
import com.mashibing.internalcommon.constant.DriverUserWorkStatusConstant;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
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
 * @date 2023/2/16 - 10:53
 */
@Service
public class DriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	@Autowired
	private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

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
}
